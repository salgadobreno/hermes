package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.*;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.params.TemplateParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import static org.apache.commons.lang.ArrayUtils.addAll;

/**
 * Created on 23/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class LoginService extends AbstractEncryptedHmacTemplateMessageService {

    private HuffmanEncodedParam loginCode;

    @Inject
    public LoginService(QrtHeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy, HmacKeyPolicy hmacKeyPolicy, PinPolicy pinPolicy) {
        super(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, pinPolicy);
    }

    @Override
    public String getServiceName() { return "Login"; }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(template, loginCode).toByteArray();
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = new HuffmanEncodedParam(loginCode);
    }
}
