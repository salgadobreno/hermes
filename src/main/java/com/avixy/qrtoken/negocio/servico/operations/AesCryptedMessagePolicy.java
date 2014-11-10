package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * Created on 05/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AesCryptedMessagePolicy implements MessagePolicy {
    private AesKeyPolicy aesKeyPolicy = new AesKeyPolicy(); //TODO: rename

    @Override
    public byte[] get(Service service) throws CryptoException, GeneralSecurityException {
        byte[] initVector = aesKeyPolicy.getInitializationVector();
        byte[] crypted = aesKeyPolicy.apply(service.getMessage());
        return ArrayUtils.addAll(crypted, initVector);
    }

    public void setKey(byte[] key) {
        aesKeyPolicy.setKey(key);
    }

    public void setIv(byte[] iv){
        aesKeyPolicy.setInitializationVector(iv);
    }

    public void setDoPadding(boolean doPadding){
        aesKeyPolicy.setDoPadding(doPadding);
    }
}
