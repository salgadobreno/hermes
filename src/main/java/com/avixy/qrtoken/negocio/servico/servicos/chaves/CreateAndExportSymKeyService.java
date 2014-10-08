package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyLengthParam;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class CreateAndExportSymKeyService extends AbstractService {

    private TimestampParam timestamp;
    private PinParam pin;
    private KeyTypeParam keyType;
    private KeyLengthParam keyLength;

    @Inject
    protected CreateAndExportSymKeyService() { }

    @Override
    public String getServiceName() {
        return "SERVICE_CREATE_AND_EXPORT_SYM_KEY";
    }

    @Override
    public int getServiceCode() {
        return 38;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp).append(pin).append(keyType).append(keyLength).toByteArray();
    }

    public void setPin(String pin) {
        this.pin = new PinParam(pin);
    }

    public void setKeyType(KeyTypeParam.KeyType keyType){
        this.keyType = new KeyTypeParam(keyType);
    }

    public void setKeyLength(int keyLength){
        this.keyLength = new KeyLengthParam(keyLength);
    }

    public void setTimestamp(Date date){
        this.timestamp = new TimestampParam(date);
    }
}
