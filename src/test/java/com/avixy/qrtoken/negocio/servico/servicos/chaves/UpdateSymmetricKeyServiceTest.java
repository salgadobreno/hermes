package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.*;

public class UpdateSymmetricKeyServiceTest {
    AesKeyPolicy aesKeyPolicy = Mockito.mock(AesKeyPolicy.class);
    UpdateSymmetricKeyService service = new UpdateSymmetricKeyService(aesKeyPolicy);
    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000,     // timestamp
                0b00011_000, //template3_
                (byte) 0b1_0111101, //keytype1_key: "zxcv"
                (byte) 0b00111100,
                (byte) 0b00110001,
                (byte) 0b10111011,
                0b00000000
        };

        service.setTimestamp(new Date(epoch));
        service.setTemplate((byte) 3);
        service.setKeyType(KeyTypeParam.KeyType.TDES);
        service.setKey("zxcv");
        aesKeyPolicy.setKey("bla".getBytes());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(37, service.getServiceCode());
    }

    @Test
    public void testMsg() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testCrypto() throws Exception {
        service.getData();
        Mockito.verify(aesKeyPolicy).setKey(Matchers.<byte[]>anyObject());
        Mockito.verify(aesKeyPolicy).apply(Mockito.<byte[]>any());
    }
}