package com.avixy.qrtoken.negocio.servico.servicos.chaves;

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
public class TwoStepSymmetricKeyImportAvixyService extends TwoStepSymmetricKeyImportService {
    @Inject
    public TwoStepSymmetricKeyImportAvixyService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy, RandomGenerator randomGenerator) {
        super(headerPolicy, timestampPolicy, passwordPolicy, randomGenerator);
    }

    @Override
    public String getServiceName() {
        return "Importação de chave em 2 passos - Avixy";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_TWO_STEP_AVIXY_SYM_KEY_IMPORT;
    }
}
