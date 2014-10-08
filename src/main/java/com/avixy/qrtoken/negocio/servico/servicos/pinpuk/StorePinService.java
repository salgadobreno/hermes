package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StorePinService extends AbstractService {

    private PinParam pin;

    @Inject
    protected StorePinService() { }

    @Override
    public String getServiceName() {
        return "SERVICE_STORE_PIN";
    }

    @Override
    public int getServiceCode() {
        return 22;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(pin).toByteArray();
    }

    public void setPin(String pin) {
        this.pin = new PinParam(pin);
    }
}
