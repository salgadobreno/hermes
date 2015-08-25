package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
* Created on 30/01/2015
*
* @author Breno Salgado <breno.salgado@avixy.com>
*/
public class ImportAvixySymKeySetService extends AbstractService implements TimestampAble {
    private KeyParam secrecyKey;
    private KeyParam authKey;

    @Inject
    public ImportAvixySymKeySetService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
    }

    @Override
    public String getServiceName() {
        return "Importar chave simétrica - Avixy";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_IMPORT_AVIXY_SYM_KEYSET;
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(secrecyKey, authKey).toByteArray();
    }

    @Override
    public void setTimestamp(Date date) {
        this.timestampPolicy.setDate(date);
    }

    public void setSecrecyKey(byte[] secrecyKey) {
        this.secrecyKey = new KeyParam(secrecyKey);
    }

    public void setAuthKey(byte[] authKey) {
        this.authKey = new KeyParam(authKey);
    }
}
