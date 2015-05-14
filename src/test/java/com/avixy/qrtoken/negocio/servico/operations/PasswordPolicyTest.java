package com.avixy.qrtoken.negocio.servico.operations;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordPolicyTest {
    PasswordPolicy passwordPolicy = new PasswordPolicy();
    @Test
    public void testGet() throws Exception {
        byte[] expectedRes = new byte[]{
                0b01100001, // a
                0b01100010, // b
                0b01100011, // c
                0b01100100, // d
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
                0b00000000, // padding
        };

        passwordPolicy.setPassword("abcd");
        assertArrayEquals(expectedRes, passwordPolicy.get());
    }
}