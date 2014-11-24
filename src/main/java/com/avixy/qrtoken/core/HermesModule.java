package com.avixy.qrtoken.core;

import com.avixy.qrtoken.negocio.qrcode.BasicQrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.DeleteSymKeyService;
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
        bind(AesKeyPolicy.class);
        bind(DeleteSymKeyService.class);

        bind(QrCodePolicy.class).to(BasicQrCodePolicy.class);
        bind(TimestampPolicy.class).to(SettableTimestampPolicy.class);
    }

}
