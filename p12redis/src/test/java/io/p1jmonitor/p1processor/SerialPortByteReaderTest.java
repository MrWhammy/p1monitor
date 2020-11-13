package io.p1jmonitor.p1processor;

import jssc.SerialPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SerialPortByteReaderTest {

    @Mock
    private SerialPort port;

    private SerialPortByteReader byteReader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        byteReader = new SerialPortByteReader(port);
    }

    @Test
    void read() throws IOException, jssc.SerialPortException {
        byte[] bytes = new byte[] {7};
        byte[] secondBytes = new byte[] {};
        byte[] thirdBytes = new byte[] {8,9};
        when(port.readBytes()).thenReturn(bytes, secondBytes, thirdBytes);

        int firstByte = byteReader.read();
        int secondByte = byteReader.read();
        int thirdByte = byteReader.read();

        assertThat(firstByte).isEqualTo(bytes[0]);
        assertThat(secondByte).isEqualTo(thirdBytes[0]);
        assertThat(thirdByte).isEqualTo(thirdBytes[1]);
    }

    @Test
    void readExceptionally() throws jssc.SerialPortException {
        when(port.readBytes()).thenThrow(jssc.SerialPortException.class);

        assertThatThrownBy(() -> byteReader.read())
                .isInstanceOf(SerialPortException.class)
                .hasCauseInstanceOf(jssc.SerialPortException.class);
    }

    @Test
    void close() throws IOException, jssc.SerialPortException {
        when(port.isOpened()).thenReturn(true);

        byteReader.close();

        verify(port).closePort();
    }

    @Test
    void closeExceptionally() throws jssc.SerialPortException {
        when(port.isOpened()).thenReturn(true);
        when(port.closePort()).thenThrow(jssc.SerialPortException.class);

        assertThatThrownBy(() -> byteReader.close())
                .isInstanceOf(SerialPortException.class)
                .hasCauseInstanceOf(jssc.SerialPortException.class);
    }
}