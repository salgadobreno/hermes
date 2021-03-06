package com.avixy.qrtoken.negocio.servico.servicos.password;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.PukAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class OverridePinService extends AbstractService implements TimestampAble, PukAble {
    private PinParam newPin;

    @Inject
    public OverridePinService(HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.passwordPolicy = passwordPolicy;
    }

    @Override
    public String getServiceName() {
        return "Sobreescrever PIN com PUK";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_OVERRIDE_PIN;
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(newPin).toByteArray();
    }

    //TODO: ?
    public void setPin(String pin) {
        this.newPin = new PinParam(pin);
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
