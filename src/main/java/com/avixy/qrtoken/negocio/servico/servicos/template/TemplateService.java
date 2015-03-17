package com.avixy.qrtoken.negocio.servico.servicos.template;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.Param;
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
    List<Param> params;

    @Inject
    public TemplateService(QrtHeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, passwordPolicy);
    }

    @Override
    public String getServiceName() {
        return "Executar Template";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_HMAC_TEMPLATE_MESSAGE;
    }

    @Override
    public byte[] getMessage() {
        String string = "";
        for (Param param : params) {
            string += param.toBinaryString();
        }

        return string.getBytes();
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }
}
