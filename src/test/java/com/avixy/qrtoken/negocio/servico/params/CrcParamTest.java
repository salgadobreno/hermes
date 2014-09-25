package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CrcParamTest {
    CrcParam param;

    @Test
    public void testBinaryString() throws Exception {
        /*
        referência: http://www.lammertbies.nl/comm/info/crc-calculation.html
        CRC-CCITT(0xFFFF)
        */
        String vector1 = "123456789";
        String binaryString1 = "0010100110110001"; //10673
        String vector2 = "987654321";
        String binaryString2 = "1000010011011111"; //34015

        param = new CrcParam(vector1.getBytes());
        assertEquals(binaryString1, param.toBinaryString());

        param = new CrcParam(vector2.getBytes());
        assertEquals(binaryString2, param.toBinaryString());

    }
}