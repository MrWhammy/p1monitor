package io.p1jmonitor.p1processor;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ContinuousRunnerTest {
    @Test
    void call() throws IOException {
        SingleTelegramProcessor processor = mock(SingleTelegramProcessor.class);
        when(processor.call()).thenReturn(true, true, false);
        ContinuousRunner runner = new ContinuousRunner(processor);
        Boolean result = runner.call();
        assertThat(result).isTrue();
        verify(processor, times(3)).call();
    }
}