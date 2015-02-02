package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import static org.junit.Assert.*;

//TODO: defasated
public class KeyComponentParamTest {
//    KeyComponentParam param;
//
//    @Test
//    public void testBinaryString() throws Exception {
//        param = new KeyComponentParam(KeyType.TDES, Hex.decode("098123baf0879dcb890bcef14289adeb")); //128 key
//
//        String expectedKeyType = "0000";
//        String expectedKeyLength = "00001";
//        String expectedKey = "00001001100000010010001110111010111100001000011110011101110010111000100100001011110011101111000101000010100010011010110111101011"; //http://binary.online-toolz.com/tools/hex-binary-convertor.php
//        String expectedCrc = "1110000111101101"; //http://www.lammertbies.nl/comm/info/crc-calculation.html -> CRC-CCITT (0xFFFF)
//        String expectedBinaryString = expectedKeyType + expectedKeyLength + expectedKey + expectedCrc;
//
//        assertEquals(expectedBinaryString, param.toBinaryString());
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testOnlyAcceptsValidLengths() throws Exception {
//        new KeyComponentParam(KeyType.AES, Hex.decode("098123baf0879dcb890bcef14289ad")); //14
//        new KeyComponentParam(KeyType.AES, Hex.decode("098123baf0879dcb890bcef14289ad098123baf0879dcb890bcef14289ad"));
//        new KeyComponentParam(KeyType.AES, Hex.decode("098123baf087"));
//    }
//
}