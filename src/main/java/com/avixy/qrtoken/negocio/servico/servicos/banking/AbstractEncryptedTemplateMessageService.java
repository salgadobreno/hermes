package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.behaviors.RangedTimestampAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.*;
import com.avixy.qrtoken.negocio.servico.params.template.TemplateSlotParam;
import com.avixy.qrtoken.negocio.servico.servicos.PasswordOptionalAbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class AbstractEncryptedTemplateMessageService extends PasswordOptionalAbstractService implements PinAble, RangedTimestampAble, TimestampAble, AesCrypted {

    protected TemplateSlotParam templateSlot;

    @Inject
    public AbstractEncryptedTemplateMessageService(QrtHeaderPolicy headerPolicy, RangedTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy, passwordPolicy);
        this.messagePolicy = aesCryptedMessagePolicy;
        this.timestampPolicy = timestampPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_ENCRYPTED_TEMPLATE_MESSAGE";
    }

    @Override
    public void setPin(String pin) {
        this.passwordPolicy.setPassword(pin);
    }

    public void setTemplateSlot(byte template) { this.templateSlot = new TemplateSlotParam(template); }

    @Override
    public void setTimestampRange(Date startDate, Date endDate) {
        ((RangedTimestampPolicy)this.timestampPolicy).setRange(startDate, endDate);
    }

    @Override
    public void setTimestamp(Date date) {
        this.timestampPolicy.setDate(date);
    }

    @Override
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy) messagePolicy).setKey(key);
    }

    @Override
    public boolean isRanged() {
        return ((RangedTimestampPolicy) timestampPolicy).isRanged();
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_HMAC_TEMPLATE_MESSAGE;
    }
}
