package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class PinParamTest {

    @Test
    public void testBinaryString() throws Exception {
        String[] validPins = {"abcd", "333333", "123456789abc"};
        String[] expectedOut = {
                "01100001" + // a
                        "01100010" + // b
                        "01100011" + // c
                        "01100100" + // d
                        "00000100",  // 4 /
                "00110011" + // 3
                        "00110011" + // 3
                        "00110011" + // 3
                        "00110011" + // 3
                        "00110011" + // 3
                        "00110011" + // 3
                        "00000110", //6 /
                "00110001" + //1
                        "00110010" + //2
                        "00110011" + //3
                        "00110100" + //4
                        "00110101" + //5
                        "00110110" + //6
                        "00110111" + //7
                        "00111000" + //8
                        "00111001" + //9
                        "01100001" + //a
                        "01100010" + //b
                        "01100011" + //c
                        "00001100" //12 /
        };
        for (int i = 0; i < validPins.length; i++) {
            String pin = validPins[i];
            String expected = expectedOut[i];

            assertEquals(expected, new PinParam(pin).toBinaryString());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPinMinimumIsFourDigits() throws Exception {
        new PinParam("123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPinMaximumIsFourDigits() throws Exception {
        new PinParam("123456789abcd");
    }
}