package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.*;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class AbstractEncryptedTemplateMessageService extends AbstractService {

    protected PinParam pin;
    protected TemplateParam template;
    protected TimestampParam timestamp;

    private AesKeyPolicy aesKeyPolicy;

    @Inject
    public AbstractEncryptedTemplateMessageService(QrtHeaderPolicy headerPolicy, AesKeyPolicy keyPolicy) {
        super(headerPolicy);
        aesKeyPolicy = keyPolicy;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        byte[] initializationVector = aesKeyPolicy.getInitializationVector();
        byte[] data = aesKeyPolicy.apply(getMessage());

        return ArrayUtils.addAll(initializationVector, data);
    }

    @Override
    public String getServiceName() {
        return "SERVICE_ENCRYPTED_TEMPLATE_MESSAGE";
    }

    @Override
    public int getServiceCode() {
        return 12;
    }

    public void setPin(String pin) { this.pin = new PinParam(pin); }

    public void setTemplate(byte template) { this.template = new TemplateParam(template); }

    public void setTimestamp(Date date) {
        this.timestamp = new TimestampParam(date);
    }

    public void setChaveAes(Chave chaveAes) {
        aesKeyPolicy.setKey(chaveAes.getHexValue());
    }
}
