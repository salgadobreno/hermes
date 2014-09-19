package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.core.HermesModule;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import java.util.Date;

public class AutorizarTransferenciaBancariaServiceTest {
    Injector injector = Guice.createInjector(new HermesModule());
    AutorizarTransferenciaBancariaService service = injector.getInstance(AutorizarTransferenciaBancariaService.class);

    @Test
    public void testParameters() throws Exception {
        /* 'excercitando o codigo' */

        String teste = "teste";
        //nome, conta, agencia de origem
        service.setNomeOrigem(teste);
        service.setAgenciaOrigem(teste);
        service.setContaOrigem(teste);
        //nome, conta, agencia de destino
        service.setNomeDestino(teste);
        service.setAgenciaDestino(teste);
        service.setContaDestino(teste);
        //valor, data, tan, timestamp
        service.setValor(teste);
        service.setData(new Date());
        service.setTan(teste);
        service.setTimestamp(new Date());
        //chaves, pin
        service.setPin("1234");
        service.setChaveAes(new Chave(teste, KeyType.AES, teste, 64));
        service.setChaveHmac(new Chave(teste, KeyType.AES, teste, 64));
    }
}