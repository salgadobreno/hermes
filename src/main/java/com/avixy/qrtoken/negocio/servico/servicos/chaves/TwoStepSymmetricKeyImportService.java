package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TwoStepSymmetricKeyImportService extends OneStepSymmetricKeyImportService {
    protected TwoStepSymmetricKeyImportService(NullKeyPolicy keyPolicy) {
        super(keyPolicy);
    }

    @Override
    public String getServiceName() {
        return "SERVICE_TWO_STEP_SYM_KEY_IMPORT";
    }

    @Override
    public int getServiceCode() {
        return 34;
    }
}
