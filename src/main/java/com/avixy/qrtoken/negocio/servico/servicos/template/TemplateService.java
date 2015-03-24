package com.avixy.qrtoken.negocio.servico.servicos.template;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.servico.params.TemplateSlotParam;
import com.avixy.qrtoken.negocio.servico.servicos.banking.AbstractEncryptedHmacTemplateMessageService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created on 16/03/2015
 *
 * @author I7
 */
public class TemplateService extends AbstractEncryptedHmacTemplateMessageService {
    TemplateSlotParam templateSlotParam;
    List<Param> params;

    @Inject
    public TemplateService(QrtHeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, passwordPolicy);
    }

    @Override
    public String getServiceName() {
        return "Executar Aplicação";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_HMAC_TEMPLATE_MESSAGE;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(templateSlotParam).append(params).toByteArray();
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
