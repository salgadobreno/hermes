package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringWrapperParamTest {

    @Test
    public void testToBinaryString() throws Exception {
        String msg = "Here be secret messages!!1";
        String expectedBinaryString = "0100100001100101011100100110010100100000011000100110010100100000011100110110010101100011011100100110010101110100001000000110110101100101011100110111001101100001011001110110010101110011001000010010000100110001";
        assertEquals(expectedBinaryString, new StringWrapperParam(msg).toBinaryString());
    }
}