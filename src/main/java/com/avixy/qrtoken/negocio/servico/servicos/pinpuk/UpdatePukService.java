package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
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
public class UpdatePukService extends AbstractService {
    private TimestampParam timestamp;
    private PukParam oldPuk;
    private PukParam newPuk;

    @Inject
    protected UpdatePukService() {
    }

    @Override
    public String getServiceName() {
        return "SERVICE_UPDATE_PUK";
    }

    @Override
    public int getServiceCode() {
        return 26;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp).append(oldPuk).append(newPuk).toByteArray();
    }

    public void setOldPuk(String oldPuk) {
        this.oldPuk = new PukParam(oldPuk);
    }

    public void setNewPuk(String newPuk) {
        this.newPuk = new PukParam(newPuk);
    }

    public void setTimestamp(Date date){
        this.timestamp = new TimestampParam(date);
    }
}
