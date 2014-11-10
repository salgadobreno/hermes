package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.servicos.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.PukParam;
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
public class OverridePinService extends AbstractService implements TimestampAble {
    private PinParam pin;
    private PukParam puk;

    @Inject
    public OverridePinService(HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
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
        return BinnaryMsg.create().append(puk).append(pin).toByteArray();
    }

    public void setPin(String pin) {
        this.pin = new PinParam(pin);
    }

    public void setPuk(String puk) {
        this.puk = new PukParam(puk);
    }

    public void setTimestamp(Date date){
        this.timestampPolicy.setDate(date);
    }
}
