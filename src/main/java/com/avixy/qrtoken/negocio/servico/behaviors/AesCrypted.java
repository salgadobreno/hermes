package com.avixy.qrtoken.negocio.servico.behaviors;

/**
 * Define que um serviço será encriptado com AES
 * Created on 06/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface AesCrypted {
    void setAesKey(byte[] key);
}
