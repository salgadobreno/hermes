package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.PukParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class OverridePinService extends AbstractService {
    private TimestampParam timestamp;
    private PinParam pin;
    private PukParam puk;

    @Inject
    protected OverridePinService(NullKeyPolicy keyPolicy) {
        super(keyPolicy);
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
        return BinnaryMsg.create().append(timestamp).append(puk).append(pin).toByteArray();
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return null;
    }

    public void setPin(String pin) {
        this.pin = new PinParam(pin);
    }

    public void setPuk(String puk) {
        this.puk = new PukParam(puk);
    }

    public void setTimestamp(Date date){
        this.timestamp = new TimestampParam(date);
    }
}
