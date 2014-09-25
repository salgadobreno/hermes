package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeyComponentParamTest {
    KeyLengthParam lengthParam;
    KeyTypeParam typeParam;
    KeyComponentParam param;

    @Test
    public void testBinaryString() throws Exception {
        lengthParam = new KeyLengthParam(128);
        typeParam = new KeyTypeParam(KeyTypeParam.KeyType.TDES);

        param = new KeyComponentParam(typeParam, lengthParam, "123456789");

        String expectedKeyType = "0001";
        String expectedKeyLength = "00001";
        String expectedKey = "001100010011001000110011001101000011010100110110001101110011100000111001"; //123456789
        String expectedCrc = "0010100110110001"; //http://www.lammertbies.nl/comm/info/crc-calculation.html -> "123456789" -> CRC-CCITT (0xFFFF) -> 0x29B1 == 10673
        String expectedBinaryString = expectedKeyType + expectedKeyLength + expectedKey + expectedCrc;

        assertEquals(expectedBinaryString, param.toBinaryString());
    }
}