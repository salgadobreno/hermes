package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.negocio.servico.TokenHuffman;
import org.junit.Test;

import static org.junit.Assert.*;

public class HuffmanEncodedParamTest {
    HuffmanEncodedParam param;

    @Test
    public void testParam() throws Exception {
        String toEncode = "23/10/2014 Transferência x";
        String encoded = "00011010"; // length 26
        encoded += new TokenHuffman().encode(toEncode);

        param = new HuffmanEncodedParam(toEncode);

        assertEquals(encoded, param.toBinaryString());
    }
}
