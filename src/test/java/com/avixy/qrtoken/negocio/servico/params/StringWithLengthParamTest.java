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
}