package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import com.avixy.qrtoken.negocio.servico.Service;

import java.security.GeneralSecurityException;

/**
 * Representa uma estratégia de aplicação de uma operação criptográfica a ser executada em um {@link Service}.
 * Cada operação criptográfica nova deve ser gerar um novo <code>KeyPolicy</code> e.g.: AESKeyPolicy, DESKeyPolicy, TDESKeyPolicy...
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 22/08/2014
 */
public interface KeyPolicy {

    /** Aplica a operação criptográfica */
    public byte[] apply(byte[] msg) throws GeneralSecurityException;


    void setKey(byte[] bytes);
}
