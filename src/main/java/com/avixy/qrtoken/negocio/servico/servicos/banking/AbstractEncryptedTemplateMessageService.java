package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.negocio.servico.servicos.AesCrypted;
import com.avixy.qrtoken.negocio.servico.servicos.PinAble;
import com.avixy.qrtoken.negocio.servico.servicos.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.*;
import com.avixy.qrtoken.negocio.servico.params.*;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class AbstractEncryptedTemplateMessageService extends AbstractService implements PinAble, TimestampAble, AesCrypted {

    protected TemplateParam template;

    @Inject
    public AbstractEncryptedTemplateMessageService(QrtHeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy, PinPolicy pinPolicy) {
        super(headerPolicy);
        this.messagePolicy = aesCryptedMessagePolicy;
        this.pinPolicy = pinPolicy;
        this.timestampPolicy = timestampPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_ENCRYPTED_TEMPLATE_MESSAGE";
    }

    @Override
    public int getServiceCode() {
        return 12;
    }

    @Override
    public void setPin(String pin) {
        this.pinPolicy.setPin(pin);
    }

    public void setTemplate(byte template) { this.template = new TemplateParam(template); }

    @Override
    public void setTimestamp(Date date) {
        this.timestampPolicy.setDate(date);
    }

    @Override
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy) messagePolicy).setKey(key);
    }
}
