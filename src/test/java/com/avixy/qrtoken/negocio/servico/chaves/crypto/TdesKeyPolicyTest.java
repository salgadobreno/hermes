package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;
import org.junit.Test;

import java.security.GeneralSecurityException;

import static org.junit.Assert.*;

/**
 * Created on 18/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TdesKeyPolicyTest {
    TdesKeyPolicy tdesKeyPolicy;
    byte[] key1;
    byte[] key2;
    byte[] key3;
    byte[] iv;
    byte[] plaintext;
    byte[] ciphertext;
    byte[] key;
    byte[] res;

    @Test
    public void testApply() throws Exception {

        //1key
        key1 = Hex.decodeHex("a4e319510bef76ea".toCharArray());
        iv = Hex.decodeHex("679fdbee166c2e0a".toCharArray());
        plaintext = Hex.decodeHex("36926e3a2720ea9f".toCharArray());
        ciphertext = Hex.decodeHex("c108c0e25ee81ea3".toCharArray());

        testResult(key1, iv, plaintext, ciphertext);

        //2keys
        key1 = Hex.decodeHex("34a41a8c293176c1".toCharArray());
        key2 = Hex.decodeHex("b30732ecfe38ae8a".toCharArray());
        iv = Hex.decodeHex("f55b4855228bd0b4".toCharArray());
        plaintext = Hex.decodeHex("7dd880d2a9ab411c".toCharArray());
        ciphertext = Hex.decodeHex("c91892948b6cadb4".toCharArray());

        key = ArrayUtils.addAll(key1, key2);

        testResult(key, iv, plaintext, ciphertext);

        //3 keys
        //vector1
        key1 = Hex.decodeHex("b5cb1504802326c7".toCharArray());
        key2 = Hex.decodeHex("3df186e3e352a20d".toCharArray());
        key3 = Hex.decodeHex("e643b0d63ee30e37".toCharArray());
        iv = Hex.decodeHex("43f791134c5647ba".toCharArray());
        plaintext =  Hex.decodeHex("dcc153cef81d6f24".toCharArray());
        ciphertext = Hex.decodeHex("92538bd8af18d3ba".toCharArray());

        key = ArrayUtils.addAll(key1, key2);
        key = ArrayUtils.addAll(key, key3);

        testResult(key, iv, plaintext, ciphertext);

        //vector2
        key1 = Hex.decodeHex("a49d7564199e97cb".toCharArray());
        key2 = Hex.decodeHex("529d2c9d97bf2f98".toCharArray());
        key3 = Hex.decodeHex("d35edf57ba1f7358".toCharArray());
        iv = Hex.decodeHex("c2e999cb6249023c".toCharArray());
        plaintext =  Hex.decodeHex("c689aee38a301bb316da75db36f110b5".toCharArray());
        ciphertext = Hex.decodeHex("e9afaba5ec75ea1bbe65506655bb4ecb".toCharArray());

        key = ArrayUtils.addAll(key1, key2);
        key = ArrayUtils.addAll(key, key3);

        testResult(key, iv, plaintext, ciphertext);

        //vector3
        key1 = Hex.decodeHex("1a5d4c0825072a15".toCharArray());
        key2 = Hex.decodeHex("a8ad9dfdaeda8c04".toCharArray());
        key3 = Hex.decodeHex("8adffb85bc4fced0".toCharArray());
        iv = Hex.decodeHex("7fcfa736f7548b6f".toCharArray());
        plaintext =  Hex.decodeHex("983c3edacd939406010e1bc6ff9e12320ac5008117fa8f84".toCharArray());
        ciphertext = Hex.decodeHex("d84fa24f38cf451ca2c9adc960120bd8ff9871584fe31cee".toCharArray());

        key = ArrayUtils.addAll(key1, key2);
        key = ArrayUtils.addAll(key, key3);

        testResult(key, iv, plaintext, ciphertext);

    }

    @Test
    public void testAlternateKeyLengths() throws Exception {

    }

    private void testResult(byte[] key, byte[] iv, byte[] plainText, byte[] expectedResult) throws CryptoException, GeneralSecurityException {
        tdesKeyPolicy = new TdesKeyPolicy(iv);
        tdesKeyPolicy.setKey(key);

        res = tdesKeyPolicy.apply(plainText);
        assertArrayEquals(res, ciphertext);
    }
}
