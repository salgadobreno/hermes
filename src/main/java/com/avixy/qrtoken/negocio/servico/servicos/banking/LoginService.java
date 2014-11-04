package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TemplateParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractEncryptedHmacTemplateMessageService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

import static org.apache.commons.lang.ArrayUtils.*;

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
    public String getServiceName() { return "Login"; }

    @Override
    public byte[] getMessage() {
//        return BinnaryMsg.create().append(timestamp, template, loginCode, pin).toByteArray();
        return BinnaryMsg.create().append(template, loginCode).toByteArray();
    }

    public byte[] getData(){
        byte[] message = getMessage();
        byte[] data, header, tstamp, criptedParams, iv, hmac, pinBytes;
        data = new byte[0];
        try {
            //1 - header
            header = headerPolicy.getHeader(this);
            //1.1 - Timestamp
            tstamp = BinnaryMsg.get(timestamp.toBinaryString());
            //2- iv
            iv = aesKeyPolicy.getInitializationVector();
            //3- criptedParams
            criptedParams = aesKeyPolicy.apply(message);
            //4- hmac
            byte[] hmacBloc = addAll(header, tstamp);
            hmacBloc = addAll(hmacBloc, criptedParams);
            hmacBloc = addAll(hmacBloc, iv);
            hmac = hmacKeyPolicy.apply(hmacBloc);
            //5- pin
            pinBytes = BinnaryMsg.create().append(pin).toByteArray();
            //6- data = hmac + pin
            data = addAll(hmac, pinBytes);
        } catch (CryptoException | GeneralSecurityException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setTemplate(byte template) {
        this.template = new TemplateParam(template);
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = new HuffmanEncodedParam(loginCode);
    }
}
