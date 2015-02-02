package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeyLengthParamTest {
    KeyLengthParam param;

    @Test
    public void testBinaryString() throws Exception {
        param = new KeyLengthParam(8);
        assertEquals("00000", param.toBinaryString());

        param = new KeyLengthParam(16);
        assertEquals("00001", param.toBinaryString());

        param = new KeyLengthParam(128);
        assertEquals("01001", param.toBinaryString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidKeyLength() throws Exception {
        param = new KeyLengthParam(66);
    }
}