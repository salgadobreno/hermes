package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.TemplateParam;
import com.avixy.qrtoken.negocio.servico.params.TemplateSlotParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.template.Template;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 03/03/2015
 *
 * @author I7
 */
public class UpdateTemplateService extends AbstractService implements TimestampAble, HmacAble, PinAble {
    private TemplateSlotParam templateSlotParam;
    private TemplateParam templateParam;

    @Inject
    public UpdateTemplateService(HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
        this.passwordPolicy = passwordPolicy;
    }

    @Override
    public String getServiceName() {
        return "Atualizar Template";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_TEMPLATE_SYM_UPDATE;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(templateSlotParam, templateParam).toByteArray();
    }

    public void setTemplateSlot(byte slot) {
        this.templateSlotParam = new TemplateSlotParam(slot);
    }

    public void setTemplate(Template template) {
        this.templateParam = new TemplateParam(template);
    }

    @Override
    public void setHmacKey(byte[] key) {
        hmacKeyPolicy.setKey(key);
    }

    @Override
    public void setPin(String pin) {
        passwordPolicy.setPassword(pin);
    }

    @Override
    public void setTimestamp(Date date) {
        timestampPolicy.setDate(date);
    }
}
