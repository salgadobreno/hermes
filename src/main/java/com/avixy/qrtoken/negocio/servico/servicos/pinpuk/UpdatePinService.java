package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdatePinService extends AbstractService {
    private PinParam oldPin;
    private PinParam newPin;
    private TimestampParam timestamp;

    @Inject
    public UpdatePinService(HeaderPolicy headerPolicy) {
        super(headerPolicy);
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
        return BinnaryMsg.create().append(timestamp).append(oldPin).append(newPin).toByteArray();
    }

    public void setOldPin(String oldPin) {
        this.oldPin = new PinParam(oldPin);
    }

    public void setNewPin(String newPin) {
        this.newPin = new PinParam(newPin);
    }

    public void setTimestamp(Date date) {
        this.timestamp = new TimestampParam(date);
    }
}
