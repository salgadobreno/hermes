package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class DeleteSymKeyService extends AbstractService implements HmacAble, TimestampAble {

    @Inject
    public DeleteSymKeyService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.headerPolicy = headerPolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
        this.timestampPolicy = timestampPolicy;
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }

    @Override
    public void setHmacKey(byte[] key) {
        this.hmacKeyPolicy.setKey(key);
    }

    @Override
    public void setTimestamp(Date date){
        this.timestampPolicy.setDate(date);
    }

}
