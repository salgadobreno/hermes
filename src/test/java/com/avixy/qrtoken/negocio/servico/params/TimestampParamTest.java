package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class TimestampParamTest {

    @Test
    public void testToBinaryString() throws Exception {
        Long epoch = 1409329200000L;
        String epochBinaryString = "01010100000000001010100000110000";
        Date date = new Date(epoch);

        assertEquals(epochBinaryString, new TimestampParam(date).toBinaryString());
    }

    @Test
    public void testItUsesGmtValue() throws Exception {
        String gmtPochBinaryString = "01010011110010101001101010110000"; // Epoch 1405786800 19/07/14 16:20
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.set(2014, Calendar.JULY, 19, 16, 20, 0);

        TimestampParam param = new TimestampParam(calendar.getTime());
        assertEquals(gmtPochBinaryString, param.toBinaryString());
    }
}