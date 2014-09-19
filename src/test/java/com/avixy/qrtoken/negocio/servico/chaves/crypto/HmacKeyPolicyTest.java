package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import static org.junit.Assert.*;

public class HmacKeyPolicyTest {

    @Test
    public void testApply() throws Exception {
        /* http://en.wikipedia.org/wiki/Hash-based_message_authentication_code#Examples_of_HMAC_.28MD5.2C_SHA1.2C_SHA256.29 */
        String key = "key";
        String msg = "The quick brown fox jumps over the lazy dog";
        String correct = "de7c9b85b8b78aa6bc8a7a36f70a90701c9db4d9";

        HmacKeyPolicy keyPolicy = new HmacKeyPolicy();
        keyPolicy.setKey(key.getBytes());
        String result = Hex.encodeHexString(keyPolicy.apply(msg.getBytes()));
        assertTrue(result.contains(correct));
    }

    @Test
    public void testApplyWithLargeKey() throws Exception {
        String key = "Computes a Hash-based message authentication code (HMAC) using a secret key. A HMAC is a small set of data that helps authenticate the nature of message; it protects the integrity and the authenticity of the message.  The secret key is a unique piece of information that is used to compute the HMAC and is known both by the sender and the receiver of the message. This key will vary in length depending on the algorithm that you use.";
        String msg = "x";
        String correct = "a7153a474c2a359b4e0904aef02c86aa9934330f";

        HmacKeyPolicy keyPolicy = new HmacKeyPolicy();
        keyPolicy.setKey(key.getBytes());
        String result = Hex.encodeHexString(keyPolicy.apply(msg.getBytes()));
        assertTrue(result.contains(correct));
    }
}