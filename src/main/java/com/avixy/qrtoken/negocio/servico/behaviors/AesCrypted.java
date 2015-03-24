package com.avixy.qrtoken.negocio.servico.behaviors;

/**
 * Defines a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} in which content is AES Encrypted
 *
 * Created on 06/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface AesCrypted {
    void setAesKey(byte[] key);
}
