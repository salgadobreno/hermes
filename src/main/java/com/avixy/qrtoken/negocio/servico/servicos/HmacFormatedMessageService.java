package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.*;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.RangedTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyParam;
import com.avixy.qrtoken.negocio.servico.params.template.TemplateParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.template.Template;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 03/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HmacFormatedMessageService extends PasswordOptionalAbstractService implements RangedTimestampAble, TimestampAble, HmacAble, PinAble, AesCrypted, PasswordOptional {
    private TemplateParam templateParam;
    private KeyParam secrecyKey;

    @Inject
    public HmacFormatedMessageService(HeaderPolicy headerPolicy, RangedTimestampPolicy timestampPolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy) {
        super(headerPolicy, passwordPolicy);
        this.timestampPolicy = timestampPolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
        this.messagePolicy = aesCryptedMessagePolicy;
    }

    @Override
    public String getServiceName() {
        return "Mensagem formatada";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_HMAC_FORMATTED_MESSAGE;
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(templateParam).toByteArray();
    }

    public void setTemplate(Template template) {
        this.templateParam = new TemplateParam(template);
    }

    @Override
    public void setHmacKey(byte[] key) {
        hmacKeyPolicy.setKey(key);
    }

    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy) messagePolicy).setKey(key);
    }

    @Override
    public void setPin(String pin) {
        passwordPolicy.setPassword(pin);
    }

    @Override
    public void setTimestamp(Date date) {
        timestampPolicy.setDate(date);
    }

    @Override
    public void setTimestampRange(Date startDate, Date endDate) {
        ((RangedTimestampPolicy)this.timestampPolicy).setRange(startDate, endDate);
    }

    @Override
    public boolean isRanged() {
        return ((RangedTimestampPolicy) timestampPolicy).isRanged();
    }
}
