package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
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
public class DeleteSymKeyService extends AbstractService implements AesCrypted, TimestampAble {
    private TemplateParam template;
    private KeyTypeParam keyType;

    @Inject
    public DeleteSymKeyService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy) {
        super(headerPolicy);
        this.headerPolicy = headerPolicy;
        this.messagePolicy = aesCryptedMessagePolicy;
        this.timestampPolicy = timestampPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_DELETE_SYM_KEY";
    }

    @Override
    public int getServiceCode() {
        return 36;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(template).append(keyType).toByteArray();
    }

    @Override
    public void setTimestamp(Date date){
        this.timestampPolicy.setDate(date);
    }

    public void setTemplate(byte template){
        this.template = new TemplateParam(template);
    }

    public void setKeyType(KeyTypeParam.KeyType keyType){
        this.keyType = new KeyTypeParam(keyType);
    }

    @Override
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy) messagePolicy).setKey(key);
    }
}
