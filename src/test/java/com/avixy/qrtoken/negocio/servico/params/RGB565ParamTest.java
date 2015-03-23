package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class RGB565ParamTest {

    @Test
    public void testBinaryString() throws Exception {
        RGB565Param rgb565Param = new RGB565Param(255, 0, 255);
        String expectedBinary = "1111100000011111";

        assertEquals(expectedBinary, rgb565Param.toBinaryString());

        RGB565Param rgb565Param1 = new RGB565Param(255, 0, 0);
        String expectedBinary1 = "1111100000000000";

        assertEquals(expectedBinary1, rgb565Param1.toBinaryString());
    }
}