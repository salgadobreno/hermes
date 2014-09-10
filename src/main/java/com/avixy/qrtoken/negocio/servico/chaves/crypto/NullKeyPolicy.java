package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import java.security.GeneralSecurityException;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
public class NullKeyPolicy implements KeyPolicy {

    @Override
    public byte[] apply(byte[] msg) throws GeneralSecurityException {
        return msg;
    }

    @Override
    public void setKey(byte[] bytes) {  }
}
