package io.p1jmonitor;

import java.io.IOException;
import java.util.concurrent.Callable;

public class ContinuousRunner implements Callable<Boolean> {
    private final SingleTelegramProcessor singleRunner;

    public ContinuousRunner(SingleTelegramProcessor singleRunner) {
        this.singleRunner = singleRunner;
    }

    @Override
    public Boolean call() throws IOException {
        boolean running = true;
        while (running) {
            running = singleRunner.call();
        }
        return true;
    }
}
