package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class DeleteSymKeyServiceTest {
    AesKeyPolicy aesKeyPolicy = Mockito.mock(AesKeyPolicy.class);
    DeleteSymKeyService service = new DeleteSymKeyService(aesKeyPolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000,     //timestamp
                0b0011_0001, //template_
        };

        service.setTimestamp(new Date(epoch));
        service.setTemplate((byte) 3);
        service.setKeyType(KeyTypeParam.KeyType.TDES);

        aesKeyPolicy.setKey("bla".getBytes());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(36, service.getServiceCode());
    }

    @Test
    public void testServiceMsg() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testCrytpo() throws Exception {
        service.getData();
        verify(aesKeyPolicy).setKey(Matchers.<byte[]>anyObject());
        verify(aesKeyPolicy).apply(Matchers.<byte[]>anyObject());
    }
}