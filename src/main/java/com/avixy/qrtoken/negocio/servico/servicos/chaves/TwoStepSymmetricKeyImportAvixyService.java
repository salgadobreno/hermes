package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.RandomGenerator;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 02/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TwoStepSymmetricKeyImportAvixyService extends TwoStepSymmetricKeyImportService implements PasswordOptional {
    private final PasswordPolicy originalPasswordPolicy;
    @Inject
    public TwoStepSymmetricKeyImportAvixyService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy, RandomGenerator randomGenerator) {
        super(headerPolicy, timestampPolicy, passwordPolicy, randomGenerator);
        this.originalPasswordPolicy = passwordPolicy;
    }

    @Override
    public String getServiceName() {
        return "Importação de chave em 2 passos - Avixy";
    }

    @Override
    public ServiceCode getServiceCode() {
        if (passwordPolicy == originalPasswordPolicy) {
            return ServiceCode.SERVICE_TWO_STEP_AVIXY_SYM_KEY_IMPORT;
        } else {
            return ServiceCode.SERVICE_TWO_STEP_AVIXY_SYM_KEY_IMPORT_WITHOUT_PIN;
        }
    }

    @Override
    public void togglePasswordOptional(boolean passwordOptional) {
        if (passwordOptional) {
            this.passwordPolicy = NO_PASSWORD_POLICY;
        } else  {
            this.passwordPolicy = originalPasswordPolicy;
        }
    }
}
