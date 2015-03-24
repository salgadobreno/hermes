package com.avixy.qrtoken.negocio.servico.behaviors;

/**
 * Defines a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} in which content is HMAC signed
 *
 * Created on 05/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface HmacAble {
    void setHmacKey(byte[] key);
}
