package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractHmacService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;

import java.util.Date;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ExportSymKeyService extends AbstractHmacService {

    private TimestampParam timestamp;
    private PinParam pin;

    protected ExportSymKeyService(QrtHeaderPolicy headerPolicy, HmacKeyPolicy keyPolicy) {
        super(headerPolicy, keyPolicy);
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
