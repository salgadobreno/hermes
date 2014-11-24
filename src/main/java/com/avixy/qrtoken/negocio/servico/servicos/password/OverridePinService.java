package com.avixy.qrtoken.negocio.servico.servicos.password;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.behaviors.PukAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.StringWithLengthParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
//TODO
public class OverridePinService extends AbstractService implements TimestampAble, PukAble {
    private StringWithLengthParam newPin;

    @Inject
    public OverridePinService(HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.passwordPolicy = passwordPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_OVERRIDE_PIN";
    }

    @Override
    public int getServiceCode() {
        return 24;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(newPin).toByteArray();
    }

    public void setPin(String pin) {
        this.newPin = new StringWithLengthParam(pin);
    }

    @Override
    public void setPuk(String puk) {
        this.passwordPolicy.setPassword(puk);
    }

    @Override
    public void setTimestamp(Date date){
        this.timestampPolicy.setDate(date);
    }
}
