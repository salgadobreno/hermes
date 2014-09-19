package com.avixy.qrtoken.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExBitSetTest {

    @Test
    public void testBytesFromString() throws Exception {
        byte[] testArray = new byte[]{1,2,3};
        String testString = "0000_0001" + // 1
                "0000_0010" + // 2
                "0000_0011"; // 3
        assertArrayEquals(testArray, ExBitSet.bytesFromString(testString));
    }
}