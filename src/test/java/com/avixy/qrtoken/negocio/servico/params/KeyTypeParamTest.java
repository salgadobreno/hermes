package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeyTypeParamTest {
    KeyTypeParam param;

    @Test
    public void testBinaryString() throws Exception {
        param = new KeyTypeParam(KeyTypeParam.KeyType.RNG);
        assertEquals("0000", param.toBinaryString());

        param = new KeyTypeParam(KeyTypeParam.KeyType.RSA_SIGNATURE);
        assertEquals("0100", param.toBinaryString());

        param = new KeyTypeParam(KeyTypeParam.KeyType.SYMMETRIC_ENCRYPTION);
        assertEquals("0010", param.toBinaryString());

        param = new KeyTypeParam(KeyTypeParam.KeyType.TDES);
        assertEquals("0001", param.toBinaryString());
    }
}