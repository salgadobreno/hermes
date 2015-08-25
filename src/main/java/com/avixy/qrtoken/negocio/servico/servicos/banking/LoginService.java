package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.*;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 23/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class LoginService extends AbstractEncryptedHmacTemplateMessageService {

    private HuffmanEncodedParam loginCode;

    @Inject
    public LoginService(QrtHeaderPolicy headerPolicy, RangedTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, passwordPolicy);
    }

    @Override
    public String getServiceName() { return "Login"; }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(templateSlot, loginCode).toByteArray();
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = new HuffmanEncodedParam(loginCode);
    }
}
