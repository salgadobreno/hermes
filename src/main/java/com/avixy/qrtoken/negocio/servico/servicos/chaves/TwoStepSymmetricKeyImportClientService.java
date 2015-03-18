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
 * @author I7
 */
public class TwoStepSymmetricKeyImportClientService extends TwoStepSymmetricKeyImportService {
    @Inject
    public TwoStepSymmetricKeyImportClientService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy, RandomGenerator randomGenerator) {
        super(headerPolicy, timestampPolicy, passwordPolicy, randomGenerator);
    }

    @Override
    public String getServiceName() {
        return "Importação de chave em 2 passos - Cliente";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_TWO_STEP_CLIENT_KEY_IMPORT;
    }
}