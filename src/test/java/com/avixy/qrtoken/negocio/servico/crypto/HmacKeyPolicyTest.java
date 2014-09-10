package com.avixy.qrtoken.negocio.servico.crypto;

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
}