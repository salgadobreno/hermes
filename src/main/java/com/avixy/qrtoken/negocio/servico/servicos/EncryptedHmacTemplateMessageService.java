package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 02/09/2014
 */
public class EncryptedHmacTemplateMessageService extends AbstractService {

    protected PinParam pin;
    protected ByteWrapperParam template;
    protected TimestampParam date;
    protected List<Param> params = new ArrayList<>();

    private KeyPolicy hmacKeyPolicy = new HmacKeyPolicy();

    @Inject
    public EncryptedHmacTemplateMessageService(AesKeyPolicy keyPolicy) {
        super(keyPolicy);
    }

    @Override
    public String getServiceName() { return "Encrypted Template Message"; }

    @Override
    public int getServiceCode() { return 10; }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        AesKeyPolicy aesKeyPolicy = (AesKeyPolicy) keyPolicy;
        byte[] initializationVector = aesKeyPolicy.getInitializationVector();
        byte[] data = aesKeyPolicy.apply(hmacKeyPolicy.apply(getMessage()));

        byte[] dataWithIv = ArrayUtils.addAll(initializationVector, data);

        return dataWithIv;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(date).append(pin).append(template).append(params).toByteArray();
    }

    @Override
    public KeyPolicy getKeyPolicy() { return null; }

    public void setPin(PinParam pin) { this.pin = pin; }

    public void setTemplate(ByteWrapperParam template) { this.template = template; }

    public void setParams(Param... params) {
        this.params = Arrays.asList(params);
    }

    public void setTimestamp(TimestampParam date) {
        this.date = date;
    }

    public void setChaveAes(Chave chaveAes) {
        keyPolicy.setKey(chaveAes.getValor().getBytes());
    }

    public void setChaveHmac(Chave chaveHmac) {
        hmacKeyPolicy.setKey(chaveHmac.getValor().getBytes());
    }
}
