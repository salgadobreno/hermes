package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.PasswordOptionalAbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class AbstractImportSymKeySetService extends AbstractService implements TimestampAble {
    private KeyParam secrecyKey;
    private KeyParam authKey;

    @Inject
    public AbstractImportSymKeySetService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
    }

    @Override
    public void setTimestamp(Date timestamp) {
        this.timestampPolicy.setDate(timestamp);
    }

    public void setAuthKey(byte[] key) {
        this.authKey = new KeyParam(key);
    }

    public void setSecrecyKey(byte[] key) {
        this.secrecyKey = new KeyParam(key);
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(secrecyKey, authKey).toByteArray();
    }
}
