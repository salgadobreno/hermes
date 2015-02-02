package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 30/01/2015
 *
 * @author I7
 */
public class DeleteSymKeyClientService extends DeleteSymKeyService {
    @Inject
    public DeleteSymKeyClientService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy) {
        super(headerPolicy, timestampPolicy, aesCryptedMessagePolicy);
    }

    @Override
    public String getServiceName() {
        return "Deletar chave simétrica - Cliente";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_DELETE_SYM_KEY_CLIENT;
    }
}
