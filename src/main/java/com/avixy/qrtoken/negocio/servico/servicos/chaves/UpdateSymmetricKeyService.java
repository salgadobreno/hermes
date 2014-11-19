package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.params.StringWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.TemplateParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdateSymmetricKeyService extends AbstractService implements AesCrypted, TimestampAble {
    private TemplateParam template;
    private KeyTypeParam keyType;
    private StringWrapperParam key;

    @Inject
    protected UpdateSymmetricKeyService(QrtHeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy messagePolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.messagePolicy = messagePolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_UPDATE_SYMMETRIC_KEY";
    }

    @Override
    public int getServiceCode() {
        return 37;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(template).append(keyType).append(key).toByteArray();
    }

    @Override
    public void setTimestamp(Date date){
        timestampPolicy.setDate(date);
    }

    public void setTemplate(byte template){
        this.template = new TemplateParam(template);
    }

    public void setKeyType(KeyTypeParam.KeyType keyType){
        this.keyType = new KeyTypeParam(keyType);
    }

    public void setKey(String key){
        this.key = new StringWrapperParam(key);
    }

    @Override
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy) messagePolicy).setKey(key);
    }
}
