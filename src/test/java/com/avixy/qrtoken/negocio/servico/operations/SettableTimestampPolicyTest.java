package com.avixy.qrtoken.negocio.servico.operations;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class SettableTimestampPolicyTest {
    SettableTimestampPolicy timestampPolicy = new SettableTimestampPolicy();

    @Test
    public void testGet() throws Exception {
        long time = 1409329200000L;
        Date date = new Date(time);
        byte[] expectedRes = new byte[]{
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000,     // expected_epoch gmt
        };

        timestampPolicy.setDate(date);

        assertArrayEquals(expectedRes, timestampPolicy.get());
    }
}