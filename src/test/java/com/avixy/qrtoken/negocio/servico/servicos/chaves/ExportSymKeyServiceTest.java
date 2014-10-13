package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.TestHelper;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ExportSymKeyServiceTest {
    HmacKeyPolicy hmacKeyPolicy = Mockito.mock(HmacKeyPolicy.class);
    ExportSymKeyService service = new ExportSymKeyService(TestHelper.getHeaderPolicy(), hmacKeyPolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000,     // timestamp
                0b00110001, // PIN:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00100100, // '$'
        };

        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(39, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testCrypto() throws Exception {
        hmacKeyPolicy.setKey("key".getBytes());
        service.getData();
        Mockito.verify(hmacKeyPolicy).apply(Mockito.<byte[]>any());
    }
}