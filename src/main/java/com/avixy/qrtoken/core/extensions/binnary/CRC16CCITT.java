package com.avixy.qrtoken.core.extensions.binnary;

/**
 * Implementação do CRC 16 bit CCITT.
 * Tirado de: http://introcs.cs.princeton.edu/java/51data/CRC16CCITT.java.html
 */
public class CRC16CCITT {

    private static final int POLYNOMIAL = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)

    public static int calc(byte[] bytes){
        int crc = 0xFFFF;          // initial value
        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= POLYNOMIAL;
            }
        }

        crc &= 0xffff;
        return crc;
    }
}
