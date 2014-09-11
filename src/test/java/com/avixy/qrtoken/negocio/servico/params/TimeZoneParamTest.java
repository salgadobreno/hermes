package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import java.util.TimeZone;

import static org.junit.Assert.*;

public class TimeZoneParamTest {

    @Test
    public void testToBinaryString() throws Exception {
        String expectedBinaryStringPositive = "00010111"; //+7
        String expectedBinaryStringNegative = "00001011"; //-11

        TimeZone timeZonePositive = TimeZone.getTimeZone("GMT+7");
        TimeZone timeZoneNegative = TimeZone.getTimeZone("GMT-11");

        assertEquals(expectedBinaryStringPositive, new TimeZoneParam(timeZonePositive).toBinaryString());
        assertEquals(expectedBinaryStringNegative, new TimeZoneParam(timeZoneNegative).toBinaryString());
    }
}