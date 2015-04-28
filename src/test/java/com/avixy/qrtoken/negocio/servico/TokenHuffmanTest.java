package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.lib.TokenHuffman;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenHuffmanTest {

    @Test
    public void testEncode() throws Exception {
        String texto = "Hoje é Quarta-feira, faça uma Transferência";
        String encoded = "1000111" + //H
                "0111110" + //o
                "1000010" + //j
                "1000000" + //e
                "1110001011" + //espaco
                "01001000" + //é
                "1110001011" + //espaco
                "11001111" + //quarta-feira
                "1110110001" +//,
                "1110001011" + //espaco
                "0011101" +  //f
                "1000011" +  //a
                "01101000" +  //ç
                "1000011" +  //a
                "1110001011" + //espaco
                "0011110" +  //u
                "0111001" +  //m
                "1000011" +  //a
                "1110001011" + //espaco
                "11111101";  //transferência

        assertEquals(encoded, new TokenHuffman().encode(texto));
    }

    @Test
    public void testArg() throws Exception {
        String texto = TokenHuffman.ARG;
        String encoded = "0011010010";

        assertEquals(encoded, new TokenHuffman().encode(texto));

        assertEquals(texto, new TokenHuffman().decode(encoded, TokenHuffman.ARG_LENGTH));
    }

    @Test
    public void testDecode() throws Exception {
        String encoded = "1000111" + //H
                "0111110" + //o
                "1000010" + //j
                "1000000" + //e
                "1110001011" + //espaco
                "01001000" + //é
                "1110001011" + //espaco
                "11001111" + //quarta-feira
                "1110110001" +//,
                "1110001011" + //espaco
                "0011101" +  //f
                "1000011" +  //a
                "01101000" +  //ç
                "1000011" +  //a
                "1110001011" + //espaco
                "0011110" +  //u
                "0111001" +  //m
                "1000011" +  //a
                "1110001011" + //espaco
                "11111101";  //transferência
        String decoded = "Hoje é Quarta-feira, faça uma Transferência";

        assertEquals(decoded, new TokenHuffman().decode(encoded, decoded.length()));
    }

}