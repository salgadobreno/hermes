package com.avixy.qrtoken.core;

import com.avixy.qrtoken.negocio.servico.operations.RandomGenerator;
import com.avixy.qrtoken.negocio.servico.operations.RandomProxy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.AbstractModule;

/**
 * Configuração do Google Guice(dependency injection).
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public class HermesModule extends AbstractModule {

    protected void configure() {
        bind(HeaderPolicy.class).to(QrtHeaderPolicy.class);
        bind(RandomGenerator.class).to(RandomProxy.class);

        bind(TimestampPolicy.class).to(SettableTimestampPolicy.class);
    }

}
