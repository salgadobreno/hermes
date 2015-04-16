package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChallengeParamTest {
    ChallengeParam param = new ChallengeParam("1234");

    @Test
    public void testBinaryString() throws Exception {
        String expectedBinaryString = "00000100" + //primeiro byte informa o tamanho
                "00110001001100100011001100110100";//1234

        assertEquals(expectedBinaryString, param.toBinaryString());
    }
}