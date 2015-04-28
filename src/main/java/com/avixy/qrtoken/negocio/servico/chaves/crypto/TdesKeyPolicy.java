package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * Created on 18/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TdesKeyPolicy extends AbstractKeyPolicy {
    private byte[] initializationVector = new byte[16];

    public TdesKeyPolicy() {
    }

    public TdesKeyPolicy(byte[] initializationVector) {
        this.initializationVector = initializationVector;
    }

    @Override
    public void setKey(byte[] key) {
        byte[] newKey;

        if (key.length == 8) {
            // key + key + key
            newKey = ArrayUtils.addAll(key, ArrayUtils.addAll(key, key));
        } else if (key.length == 16) {
            // key1 + key2 + key1
            byte[] key1 = Arrays.copyOfRange(key, 0, key.length/2);
            byte[] key2 = Arrays.copyOfRange(key, key.length/2, key.length);
            newKey = ArrayUtils.addAll(key1, ArrayUtils.addAll(key2, key1));
        } else if (key.length == 24) {
            newKey = key;
        } else {
            throw new IllegalArgumentException("Invalid Key Length");
        }

        super.setKey(newKey);
    }

    @Override
    public byte[] apply(byte[] msg) throws CryptoException {
        BufferedBlockCipher blockCipher = new BufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
        CipherParameters cipherParameters = new ParametersWithIV(new KeyParameter(key), initializationVector);

        blockCipher.init(true, cipherParameters);

        byte[] output = new byte[blockCipher.getOutputSize(msg.length)];
        int bytesOut = blockCipher.processBytes(msg, 0, msg.length, output, 0);

        blockCipher.doFinal(output, bytesOut);

        return output;
    }

}
