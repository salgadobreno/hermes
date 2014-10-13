package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HmacKeyPolicyTest {

    @Test
    public void testApply() throws Exception {
        /* http://en.wikipedia.org/wiki/Hash-based_message_authentication_code#Examples_of_HMAC_.28MD5.2C_SHA1.2C_SHA256.29 */
        String key = "key";
        String msg = "The quick brown fox jumps over the lazy dog";
        String correct = "f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997479dbc2d1a3cd8";

        HmacKeyPolicy keyPolicy = new HmacKeyPolicy();
        keyPolicy.setKey(key.getBytes());
        String result = Hex.encodeHexString(keyPolicy.apply(msg.getBytes()));
        assertTrue(result.contains(correct));
    }

    @Test
    public void testApplyWithLargeKey() throws Exception {
        String key = "Computes a Hash-based message authentication code (HMAC) using a secret key. A HMAC is a small set of data that helps authenticate the nature of message; it protects the integrity and the authenticity of the message.  The secret key is a unique piece of information that is used to compute the HMAC and is known both by the sender and the receiver of the message. This key will vary in length depending on the algorithm that you use.";
        String msg = "x";
        String correct = "f1045648260e28edc2a5c472672aab9b976efd4542c8458c558998987b6d1750";

        HmacKeyPolicy keyPolicy = new HmacKeyPolicy();
        keyPolicy.setKey(key.getBytes());
        String result = Hex.encodeHexString(keyPolicy.apply(msg.getBytes()));
        assertTrue(result.contains(correct));
    }
}