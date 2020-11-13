package io.p1jmonitor.p1processor;

import io.p1jmonitor.telegram.publish.CompositeTelegramPublisher;
import io.p1jmonitor.telegram.publish.FileTelegramPublisher;
import io.p1jmonitor.telegram.publish.StandardOutTelegramPublisher;
import io.p1jmonitor.telegram.publish.TelegramPublisher;
import io.p1jmonitor.telegram.io.TelegramInputStreamReader;
import jssc.SerialPortList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "p1processor", mixinStandardHelpOptions = true, version = "p1processor 1.0-SNAPSHOT",
        description = "Reads telegrams from P1 port and publishes them.")
public class App implements Callable<Integer> {
    static class PortCompletionCandidates implements Iterable<String> {
        @Override
        public Iterator<String> iterator() {
            return Arrays.stream(SerialPortList.getPortNames()).iterator();
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @CommandLine.Parameters(index = "0", description = "Serial port name: ${COMPLETION-CANDIDATES}", completionCandidates = PortCompletionCandidates.class)
    private String portName = null;

    @CommandLine.Option(names = {"-c", "--continuous"}, description = "Keep reading")
    private boolean continuous = false;

    @CommandLine.Option(names = {"-f", "--file"}, description = "Output to file")
    private boolean file = false;

    @CommandLine.Option(names = {"-s", "--stdout"}, description = "Output to standard out")
    private boolean stdout = false;

    @CommandLine.Option(names = {"-t", "--topic"}, description = "Output to Redis topic")
    private String topic = null;

    @CommandLine.Option(names = {"-r", "--redis"}, description = "Redis URL")
    private URI redis = null;

    @Override
    public Integer call() {
        try {
            List<TelegramPublisher> publisherList = new ArrayList<>();
            if (stdout) {
                LOGGER.debug("Adding stdout publisher");
                publisherList.add(StandardOutTelegramPublisher.create());
            }
            if (file) {
                LOGGER.debug("Adding file publisher");
                publisherList.add(FileTelegramPublisher.create());
            }
            if (topic != null) {
                LOGGER.debug("Adding Redis publisher to {}", topic);
                publisherList.add(RedisTelegramPublisher.create(redis, topic));
            }

            if (publisherList.isEmpty()) {
                System.err.println("Specify at least -f, -t or -s");
                return TelegramException.Error.CONFIG_ERROR.getValue();
            }

            LOGGER.info("Opening connection to {}", portName);
            try (TelegramInputStreamReader telegramReader = new TelegramInputStreamReader(SerialPortByteReader.create(portName, loadSerialConfiguration()));
                 CompositeTelegramPublisher publisher = new CompositeTelegramPublisher(publisherList)) {
                SingleTelegramProcessor processor = new SingleTelegramProcessor(telegramReader, publisher);

                LOGGER.debug("Starting {} run", continuous ? "continuous" : "single");
                if (continuous) {
                    ContinuousRunner runner = new ContinuousRunner(processor);
                    return runner.call() ? 0 : -1;
                } else {
                    return processor.call() ? 0 : -1;
                }
            }
        } catch (TelegramException e) {
            LOGGER.error("Could not interpret telegram", e);
            System.err.println(e.getMessage());
            return e.getError().getValue();
        } catch (SerialPortException e) {
            LOGGER.error("Problem reading from serial port", e);
            System.err.println(e.getMessage());
            return TelegramException.Error.SERIAL_ERROR.getValue();
        } catch (IOException e) {
            LOGGER.error("Generic IO Exception", e);
            System.err.println(e.getMessage());
            return TelegramException.Error.IO_ERROR.getValue();
        }
    }

    private SerialConfiguration loadSerialConfiguration() throws IOException {
        Path serialConfigurationPath = Paths.get("serial.properties");
        if (Files.exists(serialConfigurationPath)) {
            Properties properties = new Properties();
            try (InputStream inputStream = Files.newInputStream(serialConfigurationPath)) {
                properties.load(inputStream);
            }
            return SerialConfiguration.load(properties);
        } else {
            return new SerialConfiguration();
        }
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
