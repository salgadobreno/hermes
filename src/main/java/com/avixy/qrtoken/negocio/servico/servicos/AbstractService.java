package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.google.inject.Inject;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public abstract class AbstractService implements Service {

    protected KeyPolicy keyPolicy;

    @Inject
    protected AbstractService(KeyPolicy keyPolicy) {
        this.keyPolicy = keyPolicy;
    }
}
