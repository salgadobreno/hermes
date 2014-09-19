package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class AesKeyPolicyTest {
    AesKeyPolicy keyPolicy;

    @Test
    public void testDoesNotRepeatIv() throws Exception {
        byte[] iv128 = Hex.decodeHex("2FE2B333CEDA8F98F4A99B40D2CD34A8".toCharArray());
        byte[] text128 = Hex.decodeHex("45CF12964FC824AB76616AE2F4BF0822".toCharArray());
        byte[] key128 = Hex.decodeHex("1F8E4973953F3FB0BD6B16662E9A3C17".toCharArray());

        AesKeyPolicy aesKeyPolicy = new AesKeyPolicy(iv128, false);
        aesKeyPolicy.setKey(key128);
        aesKeyPolicy.apply(text128);

        assertNotEquals(iv128, aesKeyPolicy);
        assertFalse(ArrayUtils.isEquals(iv128, aesKeyPolicy.getInitializationVector()));
    }

    @Test
    public void testApply() throws Exception {
        /*
        128
        KEY = 1F8E4973953F3FB0BD6B16662E9A3C17
        IV = 2FE2B333CEDA8F98F4A99B40D2CD34A8
        PLAINTEXT = 45CF12964FC824AB76616AE2F4BF0822
        CIPHERTEXT = 0F61C4D44C5147C03C195AD7E2CC12B2
        */

        byte[] iv128 = Hex.decodeHex("2FE2B333CEDA8F98F4A99B40D2CD34A8".toCharArray());
        byte[] text128 = Hex.decodeHex("45CF12964FC824AB76616AE2F4BF0822".toCharArray());
        byte[] key128 = Hex.decodeHex("1F8E4973953F3FB0BD6B16662E9A3C17".toCharArray());
        byte[] result128 = Hex.decodeHex("0F61C4D44C5147C03C195AD7E2CC12B2".toCharArray());

        keyPolicy = new AesKeyPolicy(iv128, false);
        keyPolicy.setKey(key128);
        assertArrayEquals(result128, keyPolicy.apply(text128));

        /*
        KEY = 0700D603A1C514E46B6191BA430A3A0C
        IV = AAD1583CD91365E3BB2F0C3430D065BB
        PLAINTEXT = 068B25C7BFB1F8BDD4CFC908F69DFFC5DDC726A197F0E5F720F730393279BE91
        CIPHERTEXT = C4DC61D9725967A3020104A9738F23868527CE839AAB1752FD8BDB95A82C4D00
        */

        iv128 = Hex.decodeHex("AAD1583CD91365E3BB2F0C3430D065BB".toCharArray());
        text128 = Hex.decodeHex("068B25C7BFB1F8BDD4CFC908F69DFFC5DDC726A197F0E5F720F730393279BE91".toCharArray());
        key128 = Hex.decodeHex("0700D603A1C514E46B6191BA430A3A0C".toCharArray());
        result128 = Hex.decodeHex("C4DC61D9725967A3020104A9738F23868527CE839AAB1752FD8BDB95A82C4D00".toCharArray());

        keyPolicy = new AesKeyPolicy(iv128, false);
        keyPolicy.setKey(key128);
        assertArrayEquals(result128, keyPolicy.apply(text128));

        /*
        KEY = 3348AA51E9A45C2DBE33CCC47F96E8DE
        IV = 19153C673160DF2B1D38C28060E59B96
        PLAINTEXT = 9B7CEE827A26575AFDBB7C7A329F887238052E3601A7917456BA61251C214763D5E1847A6AD5D54127A399AB07EE3599
        CIPHERTEXT = D5AED6C9622EC451A15DB12819952B6752501CF05CDBF8CDA34A457726DED97818E1F127A28D72DB5652749F0C6AFEE5
        */

        iv128 = Hex.decodeHex("19153C673160DF2B1D38C28060E59B96".toCharArray());
        text128 = Hex.decodeHex("9B7CEE827A26575AFDBB7C7A329F887238052E3601A7917456BA61251C214763D5E1847A6AD5D54127A399AB07EE3599".toCharArray());
        key128 = Hex.decodeHex("3348AA51E9A45C2DBE33CCC47F96E8DE".toCharArray());
        result128 = Hex.decodeHex("D5AED6C9622EC451A15DB12819952B6752501CF05CDBF8CDA34A457726DED97818E1F127A28D72DB5652749F0C6AFEE5".toCharArray());

        keyPolicy = new AesKeyPolicy(iv128, false);
        keyPolicy.setKey(key128);
        assertArrayEquals(result128, keyPolicy.apply(text128));

        /*
        256
        KEY = 6ed76d2d97c69fd1339589523931f2a6cff554b15f738f21ec72dd97a7330907
        IV = 851e8764776e6796aab722dbb644ace8
        PLAINTEXT = 6282b8c05c5c1530b97d4816ca434762
        CIPHERTEXT = 6acc04142e100a65f51b97adf5172c41
        */

        byte[] iv256 = Hex.decodeHex("851e8764776e6796aab722dbb644ace8".toCharArray());
        byte[] text256 = Hex.decodeHex("6282b8c05c5c1530b97d4816ca434762".toCharArray());
        byte[] key256 = Hex.decodeHex("6ed76d2d97c69fd1339589523931f2a6cff554b15f738f21ec72dd97a7330907".toCharArray());
        byte[] result256 = Hex.decodeHex("6acc04142e100a65f51b97adf5172c41".toCharArray());

        keyPolicy = new AesKeyPolicy(iv256, false);
        keyPolicy.setKey(key256);
        assertArrayEquals(result256, keyPolicy.apply(text256));

        /*
        KEY = dce26c6b4cfb286510da4eecd2cffe6cdf430f33db9b5f77b460679bd49d13ae
        IV = fdeaa134c8d7379d457175fd1a57d3fc
        PLAINTEXT = 50e9eee1ac528009e8cbcd356975881f957254b13f91d7c6662d10312052eb00
        CIPHERTEXT = 2fa0df722a9fd3b64cb18fb2b3db55ff2267422757289413f8f657507412a64c
        */

        iv256 = Hex.decodeHex("fdeaa134c8d7379d457175fd1a57d3fc".toCharArray());
        text256 = Hex.decodeHex("50e9eee1ac528009e8cbcd356975881f957254b13f91d7c6662d10312052eb00".toCharArray());
        key256 = Hex.decodeHex("dce26c6b4cfb286510da4eecd2cffe6cdf430f33db9b5f77b460679bd49d13ae".toCharArray());
        result256 = Hex.decodeHex("2fa0df722a9fd3b64cb18fb2b3db55ff2267422757289413f8f657507412a64c".toCharArray());

        keyPolicy = new AesKeyPolicy(iv256, false);
        keyPolicy.setKey(key256);
        assertArrayEquals(result256, keyPolicy.apply(text256));

        /*
        KEY = fe8901fecd3ccd2ec5fdc7c7a0b50519c245b42d611a5ef9e90268d59f3edf33
        IV = bd416cb3b9892228d8f1df575692e4d0
        PLAINTEXT = 8d3aa196ec3d7c9b5bb122e7fe77fb1295a6da75abe5d3a510194d3a8a4157d5c89d40619716619859da3ec9b247ced9
        CIPHERTEXT = 608e82c7ab04007adb22e389a44797fed7de090c8c03ca8a2c5acd9e84df37fbc58ce8edb293e98f02b640d6d1d72464
        */

        iv256 = Hex.decodeHex("bd416cb3b9892228d8f1df575692e4d0".toCharArray());
        text256 = Hex.decodeHex("8d3aa196ec3d7c9b5bb122e7fe77fb1295a6da75abe5d3a510194d3a8a4157d5c89d40619716619859da3ec9b247ced9".toCharArray());
        key256 = Hex.decodeHex("fe8901fecd3ccd2ec5fdc7c7a0b50519c245b42d611a5ef9e90268d59f3edf33".toCharArray());
        result256 = Hex.decodeHex("608e82c7ab04007adb22e389a44797fed7de090c8c03ca8a2c5acd9e84df37fbc58ce8edb293e98f02b640d6d1d72464".toCharArray());

        keyPolicy = new AesKeyPolicy(iv256, false);
        keyPolicy.setKey(key256);
        assertArrayEquals(result256, keyPolicy.apply(text256));
    }
}