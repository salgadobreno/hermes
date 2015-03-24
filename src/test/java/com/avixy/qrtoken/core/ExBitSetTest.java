package com.avixy.qrtoken.core;

import com.avixy.qrtoken.core.extensions.binary.ExBitSet;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExBitSetTest {

    @Test
    public void testBytesFromString() throws Exception {
        byte[] testArray = new byte[]{1,2,3};
        String testString = "00000001" + // 1
                "00000010" + // 2
                "00000011"; // 3
        assertArrayEquals(testArray, ExBitSet.bytesFromString(testString));
    }
}