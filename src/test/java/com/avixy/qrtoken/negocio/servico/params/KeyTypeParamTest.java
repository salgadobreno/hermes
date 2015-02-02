package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeyTypeParamTest {
    KeyTypeParam param;

    @Test
    public void testBinaryString() throws Exception {
        param = new KeyTypeParam(KeyType.TDES);
        assertEquals("0000", param.toBinaryString());

        param = new KeyTypeParam(KeyType.AES);
        assertEquals("0001", param.toBinaryString());

        param = new KeyTypeParam(KeyType.HMAC);
        assertEquals("0010", param.toBinaryString());
    }
}