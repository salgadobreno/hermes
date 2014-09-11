package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class PinParamTest {

    @Test
    public void testToBinaryString() throws Exception {
        String expectedOut = "01100001" + // a
                "01100010" + // b
                "01100011" + // c
                "01100100" + // d
                "00100100";  // $
        PinParam pinParam = new PinParam("abcd");

        assertEquals(expectedOut, pinParam.toBinaryString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPinIsFourInLength() throws Exception {
        new PinParam("12345");
    }
}