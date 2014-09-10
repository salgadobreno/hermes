package com.avixy.qrtoken.negocio.servico.chaves.crypto;

/**
 * Created on 05/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class AbstractKeyPolicy implements KeyPolicy {
    protected byte[] key;

    public void setKey(byte[] key) {
        this.key = key;
    }
}
