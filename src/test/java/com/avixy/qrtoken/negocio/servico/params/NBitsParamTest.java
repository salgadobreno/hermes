package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class NBitsParamTest {

    @Test
    public void testToBinaryString() throws Exception {
        String expectedBinaryString = "0110";
        byte value = 6;
        assertEquals(expectedBinaryString, new NBitsParam((byte) expectedBinaryString.length(), (byte) value).toBinaryString());

        //max size
        expectedBinaryString = "1111111";
        value = 127;
        assertEquals(expectedBinaryString, new NBitsParam((byte) expectedBinaryString.length(), (byte) value).toBinaryString());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testOverflow() throws Exception {
        //overflows
        String expectedBinaryString = "11111111";
        byte value = (byte)128;
        new NBitsParam((byte) expectedBinaryString.length(), (byte) value);
    }
}