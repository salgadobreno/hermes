package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.header.HeaderPolicy;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Created on 03/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class AbstractService implements Service {

    protected KeyPolicy keyPolicy;

    @Inject
    protected AbstractService(KeyPolicy keyPolicy) {
        this.keyPolicy = keyPolicy;
    }
}
