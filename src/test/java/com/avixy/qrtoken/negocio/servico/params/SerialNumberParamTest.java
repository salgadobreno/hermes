package com.avixy.qrtoken.negocio.servico.params;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Breno on 15/05/2015.
 */
public class SerialNumberParamTest {

    public void testBinaryString() throws Exception {
        String expectedBinary = "00001010" + "00110000001100010011001000110011001101000011010100110110001101110011100000111001";
        String msg = "0123456789";

        assertEquals(expectedBinary, new SerialNumberParam(msg).toBinaryString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueSize() throws Exception {
        new SerialNumberParam("1234");
    }
}