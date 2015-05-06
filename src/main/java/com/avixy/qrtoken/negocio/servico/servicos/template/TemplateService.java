package com.avixy.qrtoken.negocio.servico.servicos.template;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.*;
import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.servico.params.template.TemplateSlotParam;
import com.avixy.qrtoken.negocio.servico.servicos.banking.AbstractEncryptedHmacTemplateMessageService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created on 16/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplateService extends AbstractEncryptedHmacTemplateMessageService {
    TemplateSlotParam templateSlotParam;
    List<Param> params;

    @Inject
    public TemplateService(QrtHeaderPolicy headerPolicy, RangedTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, passwordPolicy);
    }

    @Override
    public String getServiceName() {
        return "Executar Aplicação";
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(templateSlotParam).append(params).toByteArray();
    }

    public List<Param> getParams() {
        return params;
    }

    public void setTemplateSlot(Integer integer) {
        this.templateSlotParam = new TemplateSlotParam(integer.byteValue());
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }
}
