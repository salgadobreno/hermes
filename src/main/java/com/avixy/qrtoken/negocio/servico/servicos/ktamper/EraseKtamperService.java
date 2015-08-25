package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class EraseKtamperService extends AbstractService implements TimestampAble, HmacAble {

    @Inject
    public EraseKtamperService(HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    @Override
    public String getServiceName() {
        return "Apagar K_Tamper";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_ERASE_KTAMPER;
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }

    @Override
    public void setTimestamp(Date date){
        this.timestampPolicy.setDate(date);
    }

    @Override
    public void setHmacKey(byte[] key) {
        this.hmacKeyPolicy.setKey(key);
    }
}
