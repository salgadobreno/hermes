package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TimestampParamTest {

    @Test
    public void testToBinaryString() throws Exception {
        Long epoch = 1409329200000L;
        String epochBinaryString = "01010100000000001010100000110000";
        Date date = new Date(epoch);

        assertEquals(epochBinaryString, new TimestampParam(date).toBinaryString());
    }
}