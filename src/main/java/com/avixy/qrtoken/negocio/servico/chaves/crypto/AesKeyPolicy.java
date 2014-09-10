package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import com.google.inject.Inject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
public class AesKeyPolicy extends AbstractKeyPolicy {

    private byte[] initializationVector;
    private Cipher cipher;
    private boolean doPadding = true;

    @Inject
    public AesKeyPolicy() {
        // default: padding and random CBC Initializer vector
    }

    public AesKeyPolicy(byte[] initializationVector, boolean doPadding) {
        this.doPadding = doPadding;
        this.initializationVector = initializationVector;
    }

    @Override
    public byte[] apply(byte[] msg) throws GeneralSecurityException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

        if (doPadding) {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } else {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        }

        if (initializationVector == null) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(initializationVector));
        }

        return cipher.doFinal(msg);
    }

    public void setKey(byte[] key, byte[] initializationVector) {
        super.setKey(key);
        this.initializationVector = initializationVector;
    }
}
