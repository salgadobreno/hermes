package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 30/01/2015
 *
 * @author I7
 */
public class OneStepSymmetricKeyImportClientService extends OneStepSymmetricKeyImportService {
    @Inject
    public OneStepSymmetricKeyImportClientService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy, timestampPolicy, passwordPolicy);
    }

    @Override
    public String getServiceName() {
        return "Importar chave simétrica - Cliente";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_ONE_STEP_CLEARTEXT_CLIENT_SYM_KEY_IMPORT;
    }
}
