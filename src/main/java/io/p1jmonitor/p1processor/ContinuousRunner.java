package io.p1jmonitor.p1processor;

public class ContinuousRunner implements Runnable {
    private final SingleTelegramProcessor singleRunner;

    public ContinuousRunner(SingleTelegramProcessor singleRunner) {
        this.singleRunner = singleRunner;
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            running = singleRunner.call();
        }
    }
}
