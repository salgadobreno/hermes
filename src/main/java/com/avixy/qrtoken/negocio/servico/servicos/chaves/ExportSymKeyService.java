package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.servicos.HmacAble;
import com.avixy.qrtoken.negocio.servico.servicos.PinAble;
import com.avixy.qrtoken.negocio.servico.servicos.TimestampAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.*;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;

import java.util.Date;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ExportSymKeyService extends AbstractService implements HmacAble, TimestampAble, PinAble {

    protected ExportSymKeyService(QrtHeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PinPolicy pinPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.pinPolicy = pinPolicy;
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
    public byte[] getMessage() {
        return new byte[0];
    }

    @Override
    public void setTimestamp(Date timestamp) {
        this.timestampPolicy.setDate(timestamp);
    }

    @Override
    public void setPin(String pin) {
        this.pinPolicy.setPin(pin);
    }

    @Override
    public void setHmacKey(byte[] key) {
        this.hmacKeyPolicy.setKey(key);
    }
}
