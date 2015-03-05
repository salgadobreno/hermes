package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class NBitsParamTest {

    @Test
    public void testToBinaryString() throws Exception {
        String expectedBinaryString = "0110";
        byte value = 6;

        assertEquals(new NBitsParam((byte)4, (byte)6).toBinaryString(), expectedBinaryString);
    }
}