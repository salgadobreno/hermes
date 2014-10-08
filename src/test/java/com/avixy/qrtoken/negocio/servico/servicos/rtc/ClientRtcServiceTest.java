package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ClientRtcServiceTest {
    HmacKeyPolicy hmacKeyPolicy = Mockito.mock(HmacKeyPolicy.class);
    ClientRtcService service = new ClientRtcService(hmacKeyPolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000,     // expected_epoch gmt / timestamp
                0b00010111,     // +7
                0b0011_0010, // template
                (byte) 0b1000_0011, // a param
                (byte) 0b00100000, // another param
        };
        service.setTemplate((byte) 3);
        service.setTimestamp(new Date(epoch));
        service.setTimezone(TimeZone.getTimeZone("GMT+7"));
        service.setParams(new ByteWrapperParam((byte) 40), new ByteWrapperParam((byte) 50));
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(52, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testCrypto() throws Exception {
        service.setHmacKey("key");
        service.getData();
        Mockito.verify(hmacKeyPolicy).apply(Mockito.<byte[]>anyObject());
    }
}