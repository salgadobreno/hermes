package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.google.inject.Inject;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TwoStepSymmetricKeyImportService extends OneStepSymmetricKeyImportService {
    @Inject
    protected TwoStepSymmetricKeyImportService() { }

    @Override
    public String getServiceName() {
        return "SERVICE_TWO_STEP_SYM_KEY_IMPORT";
    }

    @Override
    public int getServiceCode() {
        return 34;
    }
}
