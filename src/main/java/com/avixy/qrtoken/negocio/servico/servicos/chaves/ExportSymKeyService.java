package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.util.Date;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ExportSymKeyService extends AbstractService {

    private TimestampParam timestamp;
    private PinParam pin;

    private HmacKeyPolicy hmacKeyPolicy;

    @Inject
    protected ExportSymKeyService(HmacKeyPolicy hmacKeyPolicy) {
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_EXPORT_SYM_KEY";
    }

    @Override
    public int getServiceCode() {
        return 39;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        return hmacKeyPolicy.apply(getMessage());
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp).append(pin).toByteArray();
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = new TimestampParam(timestamp);
    }

    public void setPin(String pin) {
        this.pin = new PinParam(pin);
    }
}
