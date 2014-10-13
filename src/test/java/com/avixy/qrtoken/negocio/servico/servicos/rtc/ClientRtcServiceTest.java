package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.negocio.TestHelper;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ClientRtcServiceTest {
    HmacKeyPolicy hmacKeyPolicy = Mockito.mock(HmacKeyPolicy.class);
    ClientRtcService service = new ClientRtcService(TestHelper.getHeaderPolicy(), hmacKeyPolicy);

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
        };
        service.setTimestamp(new Date(epoch));
        service.setTimezone(TimeZone.getTimeZone("GMT+7"));
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
        service.setHmacKey("key".getBytes());
        service.getData();
        Mockito.verify(hmacKeyPolicy).apply(Mockito.<byte[]>anyObject());
    }
}