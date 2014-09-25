package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.google.inject.Inject;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public abstract class AbstractService implements Service {

    protected KeyPolicy keyPolicy;

    @Inject
    protected AbstractService(KeyPolicy keyPolicy) {
        this.keyPolicy = keyPolicy;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        return getMessage();
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return keyPolicy;
    }
}
