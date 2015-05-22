package com.avixy.qrtoken.negocio.lib;

import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created on 23/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class KeyDerivatorTest {
    KeyDerivator keyDerivator = new KeyDerivator();
    byte[] kAes1, kAes2, kHmac1, kHmac2, keyComponent1, keyComponent2, keyComponent3;
    byte[] key;
    String serialNumber;

    @Before
    public void setUp() throws Exception {
        serialNumber = "0000000000";
        kAes1 = new byte[32];
        kAes2 = new byte[32];
        kHmac1 = new byte[32];
        kHmac2 = new byte[32];
        for (int i = 0; i < 32; i++) {
            kAes1[i] = kAes2[i] = kHmac1[i] = kHmac2[i] = 0;
        }

        keyComponent1 = new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        keyComponent2 = Hex.decodeHex("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".toCharArray());
        keyComponent3 = new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        assertEquals(32, keyComponent1.length);
        assertEquals(32, keyComponent2.length);
        assertEquals(32, keyComponent3.length);

        //bdk -> AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        //bdk ^ kAes1 -> bdk
        //aes(0000000000)[||] -> 5bfdf2a41dfbe2fdabbb538b327de4b2
        //bdk ^ kAes2 -> bdk
        //aes(0000000000)[bdk ^ kAes2] -> 5bfdf2a41dfbe2fdabbb538b327de4b2
        //key -> 5bfdf2a41dfbe2fdabbb538b327de4b25bfdf2a41dfbe2fdabbb538b327de4b2

        key = Hex.decodeHex("5bfdf2a41dfbe2fdabbb538b327de4b25bfdf2a41dfbe2fdabbb538b327de4b2".toCharArray());

        keyDerivator.setAesConstants(kAes1, kAes2);
        keyDerivator.setHmacConstants(kHmac1, kHmac2);
        keyDerivator.setKeyComponents(keyComponent1, keyComponent2, keyComponent3);
    }

    @Test
    public void testHmacKey() throws Exception {
        assertArrayEquals(key, keyDerivator.getHmacKey(serialNumber));
    }

    @Test
    public void testAesKey() throws Exception {
        assertArrayEquals(key, keyDerivator.getAesKey(serialNumber));
    }
}