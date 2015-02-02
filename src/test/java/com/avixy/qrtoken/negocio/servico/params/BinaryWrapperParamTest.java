package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryWrapperParamTest {

    @Test
    public void testToBinaryString() throws Exception {
        byte[] bytes = {1, 2, 3};
        BinaryWrapperParam param = new BinaryWrapperParam(bytes);
        String expectedBinnaryString = "00000001" + //1
                "00000010" + //2
                "00000011";  //3

        assertEquals(expectedBinnaryString, param.toBinaryString());
    }
}