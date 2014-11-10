package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * Created on 05/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface MessagePolicy {

    byte[] get(Service service) throws Exception;
}
