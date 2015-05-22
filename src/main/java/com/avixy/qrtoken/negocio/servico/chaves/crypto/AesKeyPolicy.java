package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import com.google.inject.Inject;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
@AcceptsKey(keyType = KeyType.AES)
public class AesKeyPolicy extends AbstractKeyPolicy {
    private byte[] initializationVector = new byte[16];
    private boolean doPadding = true;

    private SecureRandom secureRandom = new SecureRandom();

    @Inject
    public AesKeyPolicy() {
        /* default: padding and random CBC Innitialization Vector */
        newIv();
    }

    public AesKeyPolicy(byte[] initializationVector, boolean doPadding) {
        this.initializationVector = initializationVector.clone();
        this.doPadding = doPadding;
    }

    private void newIv(){
        secureRandom.nextBytes(initializationVector);
    }

    @Override
    public byte[] apply(byte[] msg) throws CryptoException {
        BufferedBlockCipher blockCipher = doPadding ? new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine())) : new BufferedBlockCipher(new CBCBlockCipher(new AESEngine()));

        CipherParameters cipherParameters = new ParametersWithIV(new KeyParameter(key), initializationVector);

        blockCipher.init(true, cipherParameters);

        byte[] output = new byte[blockCipher.getOutputSize(msg.length)];
        int bytesOut = blockCipher.processBytes(msg, 0, msg.length, output, 0);

        blockCipher.doFinal(output, bytesOut);

        newIv();

        return output;
    }

    public byte[] getInitializationVector() {
        if (this.initializationVector == null) { newIv(); }
        return Arrays.copyOf(initializationVector, initializationVector.length);
    }

    public void setInitializationVector(byte[] initializationVector) {
        this.initializationVector = initializationVector.clone();
    }

    public void setDoPadding(boolean doPadding) {
        this.doPadding = doPadding;
    }
}
