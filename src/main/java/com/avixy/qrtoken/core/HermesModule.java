package com.avixy.qrtoken.core;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.DeleteSymKeyService;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

/**
 * Configuração do Google Guice(dependency injection).
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public class HermesModule extends AbstractModule {
    private static Injector injector = Guice.createInjector(new HermesModule());
    protected void configure() {
        bind(HeaderPolicy.class).to(QrtHeaderPolicy.class);
        bind(KeyPolicy.class).annotatedWith(Names.named("Null")).to(NullKeyPolicy.class);
        bind(KeyPolicy.class).annotatedWith(Names.named("Hmac")).to(HmacKeyPolicy.class);
        bind(AesKeyPolicy.class);
        bind(NullKeyPolicy.class);
        bind(DeleteSymKeyService.class);
    }

    public static Injector getInjector(){
        return injector;
    }
}
