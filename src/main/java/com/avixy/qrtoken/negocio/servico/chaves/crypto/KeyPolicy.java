package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * Represents a kind of cryptographic operation to be run in {@link com.avixy.qrtoken.negocio.servico.servicos.Service}
 * Each new cryptographic operation generates a new <code>KeyPolicy</code> e.g.: AESKeyPolicy, DESKeyPolicy, TDESKeyPolicy...
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 22/08/2014
 */
public interface KeyPolicy {

    /**
     * Aplica a operação criptográfica
     * @param msg   Mensagem
     * @throws CryptoException
     * @throws GeneralSecurityException
     */
    public byte[] apply(byte[] msg) throws CryptoException, GeneralSecurityException;
    /* TODO: Usar só uma lib pra suportar só um tipo de exception de crypto generica? */

    void setKey(byte[] bytes);
}
