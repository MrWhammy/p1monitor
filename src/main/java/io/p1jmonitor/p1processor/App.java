package io.p1jmonitor.p1processor;

import io.p1jmonitor.p1processor.publish.*;
import io.p1jmonitor.p1processor.read.SerialPortByteReader;
import io.p1jmonitor.p1processor.read.TelegramInputStreamReader;
import jssc.SerialPortList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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

    @Override
    public Integer call() {
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
            publisherList.add(RedisTelegramPublisher.create(topic));
        }

        if (publisherList.isEmpty()) {
            System.err.println("Specify at least -f, -r or -s");
            return TelegramException.Error.CONFIG_ERROR.getValue();
        }

        try (TelegramInputStreamReader telegramReader = new TelegramInputStreamReader(SerialPortByteReader.create(portName));
             CompositeTelegramPublisher publisher = new CompositeTelegramPublisher(publisherList)) {
            SingleTelegramProcessor processor = new SingleTelegramProcessor(telegramReader, publisher);

            if (continuous) {
                ContinuousRunner runner = new ContinuousRunner(processor);
                return runner.call() ? 0 : -1;
            } else {
                return processor.call() ? 0 : -1;
            }
        } catch (TelegramException e) {
            System.err.println(e.getMessage());
            return e.getError().getValue();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return TelegramException.Error.IO_ERROR.getValue();
        }
    }

    public static void main(String... args) {
        new CommandLine(new App()).execute(args);
    }
}
