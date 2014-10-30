package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class GenerateKtamperService extends AbstractService {
    @Inject
    public GenerateKtamperService(QrtHeaderPolicy headerPolicy) {
        super(headerPolicy);
    }

    @Override
    public String getServiceName() {
        return "Gerar K_Tamper";
    }

    @Override
    public int getServiceCode() {
        return 20;
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }

}
