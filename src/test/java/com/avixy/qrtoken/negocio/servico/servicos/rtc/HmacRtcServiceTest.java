package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HmacRtcServiceTest {
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    HmacRtcService service = new HmacRtcService(hmacKeyPolicy);

    byte[] expectedByteArray;

    @Before
    public void setUp() throws Exception {
        expectedByteArray = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000,     // expected_epoch gmt
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

        assertEquals(expectedEpochParam, calendar.getTimeInMillis()/1000);
    }

    @Test
    public void testGetServiceCode() throws Exception {
        // Protocolo de serviços: Atualizar RTC - Avixy com HMAC - código 50
        assertEquals(50, service.getServiceCode());
    }

    @Test
    public void testRtcMessage() throws Exception {
        assertArrayEquals(expectedByteArray, service.getMessage());
    }

    @Test
    public void testRtcMessageComTimezoneNegativo() throws Exception {
        expectedByteArray = new byte[]{
                0b01010100,
                0b00000000,
                (byte)0b10101000,
                0b00110000,     // expected_epoch gmt
                0b00001011      // -11
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
        service.setTimezone(TimeZone.getTimeZone("GMT-11"));

        assertEquals(expectedEpochParam, calendar.getTimeInMillis()/1000);
        assertArrayEquals(expectedByteArray, service.getMessage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTimezoneMaximoEhDoze() throws Exception {
        service.setTimezone(TimeZone.getTimeZone("GMT+16"));
        service.setTimezone(TimeZone.getTimeZone("GMT-13"));
    }

    @Test
    public void testCrypto() throws Exception {
        service.setHmacKey("key");
        service.getData();
        verify(hmacKeyPolicy).apply(Matchers.<byte[]>anyObject());
    }
}