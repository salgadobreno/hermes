package com.avixy.qrtoken.negocio.servico.params;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyParamTest {
    private KeyParam keyParam;

    @Test
    public void testToBinaryString() throws Exception {
        String expectedMessage = "00001" + //keylength 1 -> 16 bits
                "00001001100000010010001110111010111100001000011110011101110010111000100100001011110011101111000101000010100010011010110111101011" + //123456789
                "1110000111101101"; //crc
        keyParam = new KeyParam(Hex.decode("098123baf0879dcb890bcef14289adeb"));

        assertEquals(expectedMessage, keyParam.toBinaryString());
    }
}