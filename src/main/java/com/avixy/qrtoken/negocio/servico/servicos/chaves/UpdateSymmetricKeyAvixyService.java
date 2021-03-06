package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 30/01/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdateSymmetricKeyAvixyService extends UpdateSymmetricKeyService {
    @Inject
    protected UpdateSymmetricKeyAvixyService(QrtHeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy messagePolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy, timestampPolicy, messagePolicy, hmacKeyPolicy);
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_UPDATE_AVIXY_SYM_KEYSET;
    }

    @Override
    public String getServiceName() {
        return "Atualizar chave simétrica - Avixy";
    }
}
