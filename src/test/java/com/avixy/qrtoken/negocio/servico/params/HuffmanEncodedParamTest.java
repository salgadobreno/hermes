package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.negocio.lib.TokenHuffman;
import org.junit.Test;

import static org.junit.Assert.*;

public class HuffmanEncodedParamTest {

    @Test
    public void testParam() throws Exception {
        String toEncode = "23/10/2014 Transferencia x";
        String encoded = "00011010"; // length 26
        encoded += new TokenHuffman().encode(toEncode);

        String argEncode = "{arg}";
        String argEncoded = "11111111";
        argEncoded += new TokenHuffman().encode(argEncode);

        HuffmanEncodedParam param = new HuffmanEncodedParam(toEncode);
        HuffmanEncodedParam argParam = new HuffmanEncodedParam(argEncode);

        assertEquals(encoded, param.toBinaryString());
        assertEquals(argEncoded, argParam.toBinaryString());
    }
}
