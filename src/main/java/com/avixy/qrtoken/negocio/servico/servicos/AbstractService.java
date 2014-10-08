package com.avixy.qrtoken.negocio.servico.servicos;

import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public abstract class AbstractService implements Service {

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        return getMessage();
    }
}
