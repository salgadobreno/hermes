package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class ClientRtcServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    ClientRtcService service = new ClientRtcService(headerPolicy, hmacKeyPolicy, timestampPolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b00010111     // +7
        };
        service.setTimestamp(new Date(epoch));
        service.setTimezone(TimeZone.getTimeZone("GMT+7"));
        service.setHmacKey("key".getBytes());

        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        verify(headerPolicy).getHeader(service);
        verify(hmacKeyPolicy).apply(Mockito.<byte[]>anyObject());
        verify(timestampPolicy).get();
    }
}