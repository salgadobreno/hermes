package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 30/01/2015
 *
 * @author I7
 */
public class DeleteSymKeyAvixyService extends DeleteSymKeyService {
    @Inject
    public DeleteSymKeyAvixyService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy, timestampPolicy, hmacKeyPolicy);
    }

    @Override
    public String getServiceName() {
        return "Deletar chave simétrica - Avixy";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_DELETE_SYM_KEY_AVIXY;
    }

}
