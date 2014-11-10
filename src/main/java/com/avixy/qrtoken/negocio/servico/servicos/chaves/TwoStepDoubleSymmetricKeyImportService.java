package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.operations.PinPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TwoStepDoubleSymmetricKeyImportService extends OneStepDoubleSymmetricKeyImportService {
    @Inject
    public TwoStepDoubleSymmetricKeyImportService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PinPolicy pinPolicy) {
        super(headerPolicy, timestampPolicy, pinPolicy);
    }

    @Override
    public int getServiceCode() {
        return 35;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_TWO_STEP_DOUBLE_SYM_KEY_IMPORT";
    }
}
