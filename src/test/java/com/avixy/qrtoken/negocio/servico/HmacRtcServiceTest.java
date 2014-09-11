package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.header.QrtHeaderPolicy;
import com.avixy.qrtoken.negocio.servico.params.TimeZoneParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class HmacRtcServiceTest {
    HmacRtcService service = new HmacRtcService(new HmacKeyPolicy());

    static final Charset CHARSET = Charset.forName("ISO-8859-1");
    int timeStamp = 50;
    String key = "chave";
    String data;
    String msg;

    @Before
    public void setUp() throws Exception {
        service.setDate(new TimestampParam(50));
        service.setTimeZone(new TimeZoneParam(TimeZone.getDefault()));
        service.setKey(key);
        data = new String(service.getData(), CHARSET);
        msg = new String(service.getMessage(), CHARSET);
    }

    @Test
    public void testGetServiceCode() throws Exception {
        // Protocolo de serviços: Atualizar RTC - Avixy com HMAC - código 50
        assertEquals(50, service.getServiceCode());
    }

    @Test
    public void testHmac() throws Exception {
        // Testa que os getData() inclui o HMAC de getMessage()
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac sha1Mac = Mac.getInstance("HmacSHA1");
        sha1Mac.init(secretKeySpec);
        byte[] result = sha1Mac.doFinal(service.getMessage());
        assertTrue(new String(service.getData(), CHARSET).contains(new String(result, CHARSET)));
        // Asserções contra falso-negativos
        // wrong key
        SecretKeySpec wrongKey = new SecretKeySpec("wrongkey".getBytes(), "HmacSHA1");
        sha1Mac = Mac.getInstance("HmacSHA1");
        sha1Mac.init(wrongKey);
        result = sha1Mac.doFinal(service.getMessage());
        assertFalse(new String(service.getData(), CHARSET).contains(new String(result, CHARSET)));
        // wrong message
        sha1Mac = Mac.getInstance("HmacSHA1");
        sha1Mac.init(secretKeySpec);
        result = sha1Mac.doFinal("wrong message".getBytes());
        assertFalse(new String(service.getData(), CHARSET).contains(new String(result, CHARSET)));
    }

    @Test
    public void testRtcMessage() throws Exception {
        byte[] expectedByteArray = {
                0b01010100,
                0b00000000,
                (byte)0b10101000,
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

        service.setDate(new TimestampParam(calendar.getTime()));
        service.setTimeZone(new TimeZoneParam(TimeZone.getTimeZone("GMT+7")));

        assertEquals(expectedEpochParam, calendar.getTimeInMillis()/1000);
        assertArrayEquals(expectedByteArray, service.getMessage());
    }

    @Test
    public void testRtcMessageComTimezoneNegativo() throws Exception {
        byte[] expectedByteArray = {
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

        service.setDate(new TimestampParam(calendar.getTime()));
        service.setTimeZone(new TimeZoneParam(TimeZone.getTimeZone("GMT-11")));

        assertEquals(expectedEpochParam, calendar.getTimeInMillis()/1000);
        assertArrayEquals(expectedByteArray, service.getMessage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTimezoneMaximoEhDoze() throws Exception {
        service.setTimeZone(new TimeZoneParam(TimeZone.getTimeZone("GMT+16")));
        service.setTimeZone(new TimeZoneParam(TimeZone.getTimeZone("GMT-13")));
    }
}