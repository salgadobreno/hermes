package com.avixy.qrtoken.negocio.servico.crypto;

import java.security.GeneralSecurityException;

/**
 * Created on 22/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface KeyPolicy {

    public byte[] apply(byte[] msg) throws GeneralSecurityException;

}
