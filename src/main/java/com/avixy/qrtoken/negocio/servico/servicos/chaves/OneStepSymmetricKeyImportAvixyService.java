package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 30/01/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class OneStepSymmetricKeyImportAvixyService extends OneStepSymmetricKeyImportService {

    @Inject
    public OneStepSymmetricKeyImportAvixyService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy, timestampPolicy, passwordPolicy);
    }

    @Override
    public String getServiceName() {
        return "Importar chave simétrica - Avixy";
    }

    @Override
    public ServiceCode getServiceCode() {
        if (passwordPolicy == originalPasswordPolicy) {
            return ServiceCode.SERVICE_ONE_STEP_CLEARTEXT_AVIXY_SYM_KEY_IMPORT;
        } else {
            return ServiceCode.SERVICE_ONE_STEP_CLEARTEXT_AVIXY_SYM_KEY_IMPORT_WITHOUT_PIN;
        }
    }
}
