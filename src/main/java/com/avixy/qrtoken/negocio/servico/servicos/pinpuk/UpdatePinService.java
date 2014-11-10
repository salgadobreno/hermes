package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.servicos.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdatePinService extends AbstractService implements TimestampAble {
    private PinParam oldPin;
    private PinParam newPin;

    @Inject
    public UpdatePinService(HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_UPDATE_PIN";
    }

    @Override
    public int getServiceCode() {
        return 23;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(oldPin).append(newPin).toByteArray();
    }

    public void setOldPin(String oldPin) {
        this.oldPin = new PinParam(oldPin);
    }

    public void setNewPin(String newPin) {
        this.newPin = new PinParam(newPin);
    }

    @Override
    public void setTimestamp(Date date) {
        this.timestampPolicy.setDate(date);
    }
}
