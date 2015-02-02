package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.TemplateParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class DeleteSymKeyService extends AbstractService implements AesCrypted, TimestampAble {
    private TemplateParam template;

    @Inject
    public DeleteSymKeyService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy) {
        super(headerPolicy);
        this.headerPolicy = headerPolicy;
        this.messagePolicy = aesCryptedMessagePolicy;
        this.timestampPolicy = timestampPolicy;
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
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy) messagePolicy).setKey(key);
    }
}
