package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 30/01/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class DeleteSymKeyClientService extends DeleteSymKeyService {
    @Inject
    public DeleteSymKeyClientService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy, timestampPolicy, hmacKeyPolicy);
    }

    @Override
    public String getServiceName() {
        return "Deletar chave simétrica - Cliente";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_DELETE_CLIENT_SYM_KEYSET;
    }
}
