package com.avixy.qrtoken.negocio.servico.operations;

import org.junit.Test;

import static org.junit.Assert.*;

public class PinPolicyTest {
    PinPolicy pinPolicy = new PinPolicy();
    @Test
    public void testGet() throws Exception {
        byte[] expectedRes = new byte[]{
                0b01100001, // a
                0b01100010, // b
                0b01100011, // c
                0b01100100, // d
                0b00000100  // 4
        };

        pinPolicy.setPin("abcd");
        assertArrayEquals(expectedRes, pinPolicy.get());
    }
}