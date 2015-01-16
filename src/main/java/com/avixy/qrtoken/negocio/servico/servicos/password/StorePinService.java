package com.avixy.qrtoken.negocio.servico.servicos.password;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.params.StringWithLengthParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StorePinService extends AbstractService {

    private StringWithLengthParam pin;

    @Inject
    public StorePinService(QrtHeaderPolicy headerPolicy) {
        super(headerPolicy);
    }

    @Override
    public String getServiceName() {
        return "Gravar PIN";
    }

    @Override
    public int getServiceCode() {
        return 22;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(pin).toByteArray();
    }

    public void setPin(String pin) {
        this.pin = new StringWithLengthParam(pin);
    }
}
