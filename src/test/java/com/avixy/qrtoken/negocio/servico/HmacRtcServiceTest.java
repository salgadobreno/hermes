package com.avixy.qrtoken.negocio.servico;

import org.junit.Before;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HmacRtcServiceTest {
    static final Charset CHARSET = Charset.forName("ISO-8859-1");
    HmacRtcService service = new HmacRtcService();
    int timeStamp = 50;
    String key = "chave";
    String data;
    String msg;

    @Before
    public void setUp() throws Exception {
        service.setData(new Date(timeStamp));
        service.setTimeZone(TimeZone.getDefault());
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
    public void testParams() throws Exception {
        // timestamp, fuso horário
        assertTrue(msg.contains(String.valueOf(timeStamp)));
        assertTrue(msg.contains(String.valueOf(TimeZone.getDefault().getRawOffset())));
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
}