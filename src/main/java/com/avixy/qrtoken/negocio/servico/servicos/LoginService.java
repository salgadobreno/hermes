package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.params.TemplateParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 23/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class LoginService extends AbstractEncryptedHmacTemplateMessageService {
    private TemplateParam template;
    private HuffmanEncodedParam loginCode;

    @Inject
    public LoginService(QrtHeaderPolicy headerPolicy, AesKeyPolicy aesKeyPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy, aesKeyPolicy, hmacKeyPolicy);
    }

    @Override
    public String getServiceName() {
        return "Login";
    }

    @Override
    public int getServiceCode() {
        return 0;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp, template, pin, loginCode).toByteArray();
    }

    public void setTemplate(byte template) {
        this.template = new TemplateParam(template);
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = new HuffmanEncodedParam(loginCode);
    }
}
