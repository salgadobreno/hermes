package com.avixy.qrtoken.core;

import com.avixy.qrtoken.negocio.servico.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.header.QrtHeaderPolicy;
import com.google.inject.AbstractModule;

/**
 * Created on 03/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HermesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HeaderPolicy.class).to(QrtHeaderPolicy.class);
    }
}
