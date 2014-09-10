package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimestampParamTest {

    @Test
    public void testToBinaryString() throws Exception {
        String expectedBinaryString = "01010100000000001010100000110000";
        Integer timestamp = 1409329200;

        assertEquals(expectedBinaryString, new TimestampParam(timestamp).toBinaryString());
    }
}