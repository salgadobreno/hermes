package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.header.HeaderPolicy;

/**
 * Created on 03/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class AbstractService implements Service {
    protected HeaderPolicy headerPolicy;
    protected KeyPolicy keyPolicy;

    protected AbstractService(HeaderPolicy headerPolicy, KeyPolicy keyPolicy) {
        this.headerPolicy = headerPolicy;
        this.keyPolicy = keyPolicy;
    }
}
