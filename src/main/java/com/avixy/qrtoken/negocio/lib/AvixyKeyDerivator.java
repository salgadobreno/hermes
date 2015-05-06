package com.avixy.qrtoken.negocio.lib;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

/**
 * Utility class for deriving the Avixy Token Keys for AES/HMAC
 *
 * Created on 23/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AvixyKeyDerivator {

    public class AvixyKeyNotConfigured extends Exception {
        @Override
        public String getMessage() {
            return "AvixyKeyDerivator has not been set up";
        }
    }

    private byte[] kComponent1, kComponent2, kComponent3;
    private byte[] kAes1, kAes2;
    private byte[] kHmac1, kHmac2;
    private byte[] baseDerivationKey;
    {
        kComponent1 = new byte[0];
        kComponent2 = new byte[0];
        kComponent3 = new byte[0];
        kAes1 = new byte[0];
        kAes2 = new byte[0];
        kHmac1 = new byte[0];
        kHmac2 = new byte[0];
    }
    private static final  byte[] CLEAN_IV = new byte[16];

    private AesKeyPolicy aesKeyPolicy = new AesKeyPolicy();

    public void setAesConstants(byte[] kAes1, byte[] kAes2) {
        this.kAes1 = kAes1;
        this.kAes2 = kAes2;
    }

    public void setHmacConstants(byte[] kHmac1, byte[] kHmac2) {
        this.kHmac1 = kHmac1;
        this.kHmac2 = kHmac2;
    }

    public void setKeyComponents(byte[] keyComponent1, byte[] keyComponent2, byte[] keyComponent3) {
        if (keyComponent1.length != 32 || keyComponent2.length != 32 || keyComponent3.length != 32) {
            return;
        }
        kComponent1 = keyComponent1;
        kComponent2 = keyComponent2;
        kComponent3 = keyComponent3;
        baseDerivationKey = new byte[32];

        for (int i = 0; i < 32; i++) {
            baseDerivationKey[i] = (byte) (keyComponent1[i] ^ keyComponent2[i] ^ keyComponent3[i]);
        }
    }

    /**
     * Derives the HMAC Key
     * @param serialNumber
     * @return Derived HMAC Key
     * @throws CryptoException
     * @throws GeneralSecurityException
     * @throws com.avixy.qrtoken.negocio.lib.AvixyKeyDerivator.AvixyKeyNotConfigured AvixyKeyDerivator has not been set up with required params
     */
    public byte[] getHmacKey(String serialNumber) throws CryptoException, GeneralSecurityException, AvixyKeyNotConfigured {
        if (kHmac1 == null | kHmac2 == null | baseDerivationKey == null) {
            throw new AvixyKeyNotConfigured();
        }
        aesKeyPolicy.setInitializationVector(CLEAN_IV);
        byte[] key = ArrayUtils.clone(baseDerivationKey);

        byte[] hmacKeySn1 = new byte[32];
        for (int i = 0; i < key.length; i++) {
            hmacKeySn1[i] = (byte) (key[i] ^ kHmac1[i]);
        }
        aesKeyPolicy.setKey(hmacKeySn1);
        hmacKeySn1 = aesKeyPolicy.apply(serialNumber.getBytes(Charset.forName("ISO-8859-1")));

        aesKeyPolicy.setInitializationVector(CLEAN_IV);
        byte[] hmacKeySn2 = new byte[32];
        for (int i = 0; i < key.length; i++) {
            hmacKeySn2[i] = (byte) (key[i] ^ kHmac2[i]);
        }
        aesKeyPolicy.setKey(hmacKeySn2);
        hmacKeySn2 = aesKeyPolicy.apply(serialNumber.getBytes(Charset.forName("ISO-8859-1")));

        return ArrayUtils.addAll(hmacKeySn1, hmacKeySn2);
    }

    /**
     * Derives the AES Key
     * @param serialNumber
     * @return Derived AES Key
     * @throws CryptoException
     * @throws GeneralSecurityException
     * @throws com.avixy.qrtoken.negocio.lib.AvixyKeyDerivator.AvixyKeyNotConfigured AvixyKeyDerivator has not been set up with required params
     */
    public byte[] getAesKey(String serialNumber) throws CryptoException, GeneralSecurityException, AvixyKeyNotConfigured {
        if (kHmac1 == null | kHmac2 == null | baseDerivationKey == null) {
            throw new AvixyKeyNotConfigured();
        }
        byte[] key = ArrayUtils.clone(baseDerivationKey);

        byte[] aesKeySn1 = new byte[32];
        for (int i = 0; i < key.length; i++) {
            aesKeySn1[i] = (byte) (key[i] ^ kAes1[i]);
        }
        aesKeyPolicy.setKey(aesKeySn1);
        aesKeyPolicy.setInitializationVector(CLEAN_IV);
        aesKeySn1 = aesKeyPolicy.apply(serialNumber.getBytes(Charset.forName("ISO-8859-1")));

        byte[] aesKeySn2 = new byte[32];
        for (int i = 0; i < key.length; i++) {
            aesKeySn2[i] = (byte) (key[i] ^ kAes2[i]);
        }
        aesKeyPolicy.setKey(aesKeySn2);
        aesKeyPolicy.setInitializationVector(CLEAN_IV);
        aesKeySn2 = aesKeyPolicy.apply(serialNumber.getBytes(Charset.forName("ISO-8859-1")));

        return ArrayUtils.addAll(aesKeySn1, aesKeySn2);
    }

    public byte[] getkComponent1() {
        return kComponent1;
    }

    public byte[] getkComponent2() {
        return kComponent2;
    }

    public byte[] getkComponent3() {
        return kComponent3;
    }

    public byte[] getkAes1() {
        return kAes1;
    }

    public byte[] getkAes2() {
        return kAes2;
    }

    public byte[] getkHmac1() {
        return kHmac1;
    }

    public byte[] getkHmac2() {
        return kHmac2;
    }
}
