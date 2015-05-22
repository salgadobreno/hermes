package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class DesafioParamTest {
    DesafioParam param;
    
    String desafio1 = "1234";
    String binaryString1 = "00110001001100100011001100110100";
    String desafio2 = "12345678";
    String binaryString2 = "0011000100110010001100110011010000110101001101100011011100111000";
    
    String desafio3 = "12345678910"; // fail
    String desafio4 = "12"; // fail

    @Test
    public void testBinaryString() throws Exception {
        param = new DesafioParam(desafio1);
        assertEquals(binaryString1, param.toBinaryString());
        
        param = new DesafioParam(desafio2);
        assertEquals(binaryString2, binaryString2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSizeHigher() throws Exception {
        param = new DesafioParam(desafio3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSizeLower() throws Exception {
        param = new DesafioParam(desafio4);
    }
}