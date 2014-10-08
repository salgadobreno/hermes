package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.*;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class EncryptedTemplateMessageService extends AbstractService {

    protected PinParam pin;
    protected TemplateParam template;
    protected TimestampParam date;
    protected List<Param> params = new ArrayList<>();

    private AesKeyPolicy aesKeyPolicy;

    @Inject
    protected EncryptedTemplateMessageService(AesKeyPolicy keyPolicy) {
        aesKeyPolicy = keyPolicy;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        byte[] initializationVector = aesKeyPolicy.getInitializationVector();
        byte[] data = aesKeyPolicy.apply(getMessage());

        byte[] dataWithIv = ArrayUtils.addAll(initializationVector, data);

        return dataWithIv;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_ENCRYPTED_TEMPLATE_MESSAGE";
    }

    @Override
    public int getServiceCode() {
        return 12;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(date).append(pin).append(template).append(params).toByteArray();
    }

    public void setPin(String pin) { this.pin = new PinParam(pin); }

    public void setTemplate(byte template) { this.template = new TemplateParam(template); }

    public void setParams(Param... params) {
        this.params = Arrays.asList(params);
    }

    public void setTimestamp(Date date) {
        this.date = new TimestampParam(date);
    }

    public void setChaveAes(Chave chaveAes) {
        aesKeyPolicy.setKey(chaveAes.getValor().getBytes());
    }

}
