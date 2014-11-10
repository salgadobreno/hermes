package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class AvixyRtcServiceTest {
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    AvixyRtcService service = new AvixyRtcService(headerPolicy, hmacKeyPolicy, timestampPolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        //NOTE: redundante com HmacRtcService..
        expectedMsg = new byte[]{
//                0b01010100,
//                0b00000000,
//                (byte) 0b10101000,
//                0b00110000,     // expected_epoch gmt
                0b00010111     // +7
        };

        int expectedEpochParam = 1409329200;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("utc"));
        calendar.set(2014, Calendar.AUGUST, 29);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        service.setTimestamp(calendar.getTime());
        service.setTimezone(TimeZone.getTimeZone("GMT+7"));
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(50, service.getServiceCode());
    }

    @Test
    public void testMsg() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testCrypto() throws Exception {
//        service.setHmacKey("key".getBytes());
        service.run();
        verify(headerPolicy).getHeader(Matchers.<Service>anyObject());
        verify(hmacKeyPolicy).apply(Mockito.<byte[]>any());
        verify(timestampPolicy).get();
    }
}