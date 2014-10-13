package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringWithLengthParamTest {
    StringWithLengthParam param = new StringWithLengthParam("italo");

    @Test
    public void testBinaryString() throws Exception {
        String expectedBinaryString = "00000101" // length 5
                                    + "0110100101110100011000010110110001101111"; // italo

        assertEquals(expectedBinaryString, param.toBinaryString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSizeLimit() throws Exception {
        char[] string = new char[256];
        for (int i = 0; i < string.length; i++) {
            string[i] = 'u';
        }
        new StringWithLengthParam(new String(string));
    }
}