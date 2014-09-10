package com.avixy.qrtoken.core;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.header.QrtHeaderPolicy;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

/**
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
        bind(KeyPolicy.class).annotatedWith(Names.named("PinCypher")).to(AesKeyPolicy.class);
    }

    public static Injector getInjector(){
        return injector;
    }
}
