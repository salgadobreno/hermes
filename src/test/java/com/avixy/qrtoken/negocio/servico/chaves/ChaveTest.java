package com.avixy.qrtoken.negocio.servico.chaves;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChaveTest {
    Chave notHex = new Chave("teste", KeyType.AES, "nothexz", 64);
    Chave old = new Chave("teste", KeyType.AES, "1234567812345678123456781234567812345678123456781234567812345678", 64);
    Chave hexButDiffLength = new Chave("teste", KeyType.AES, "00112233", 64);
    Chave rightHex = new Chave("teste", KeyType.AES, "0011223344556677", 64);
    Chave rightHex2 = new Chave("teste", KeyType.AES, "00112233445566778899aabbccddeeff", 128);

    @Test
    public void testIsValid() throws Exception {
        assertFalse(notHex.isValid());
        assertFalse(old.isValid());
        assertFalse(hexButDiffLength.isValid());
        assertTrue(rightHex.isValid());
        assertTrue(rightHex2.isValid());
    }
}