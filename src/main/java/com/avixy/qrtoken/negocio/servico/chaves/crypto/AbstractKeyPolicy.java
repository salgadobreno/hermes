package com.avixy.qrtoken.negocio.servico.chaves.crypto;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 05/09/2014
 */
public abstract class AbstractKeyPolicy implements KeyPolicy {
    protected byte[] key;

    public void setKey(byte[] key) {
        this.key = key;
    }
}
