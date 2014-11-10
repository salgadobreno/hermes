package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.servicos.PinAble;
import com.avixy.qrtoken.negocio.servico.operations.PinPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.TimestampAble;
import com.avixy.qrtoken.negocio.servico.params.KeyLengthParam;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class CreateAndExportSymKeyService extends AbstractService implements PinAble, TimestampAble {

    private KeyTypeParam keyType;
    private KeyLengthParam keyLength;

    @Inject
    public CreateAndExportSymKeyService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PinPolicy pinPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.pinPolicy = pinPolicy;
    }

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
        return BinnaryMsg.create().append(keyType, keyLength).toByteArray();
    }

    public void setPin(String pin) {
        this.pinPolicy.setPin(pin);
    }

    public void setKeyType(KeyTypeParam.KeyType keyType){
        this.keyType = new KeyTypeParam(keyType);
    }

    public void setKeyLength(int keyLength){
        this.keyLength = new KeyLengthParam(keyLength);
    }

    @Override
    public void setTimestamp(Date date){
        this.timestampPolicy.setDate(date);
    }
}
