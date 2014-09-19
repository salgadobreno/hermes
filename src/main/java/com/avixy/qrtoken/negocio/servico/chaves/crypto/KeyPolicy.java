package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * Representa uma estratégia de aplicação de uma operação criptográfica a ser executada em um {@link Service}.
 * Cada operação criptográfica nova deve ser gerar um novo <code>KeyPolicy</code> e.g.: AESKeyPolicy, DESKeyPolicy, TDESKeyPolicy...
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
