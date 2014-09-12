package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.core.BinMsg;
import com.avixy.qrtoken.core.ExBitSet;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 02/09/2014
 */
public class EncryptedTemplateMessageService extends AbstractService {

    private PinParam pin;
    private ByteWrapperParam template;
    private TimestampParam date;
    private List<Param> params = new ArrayList<>();

    private KeyPolicy hmacKeyPolicy = new HmacKeyPolicy();

    @Inject
    public EncryptedTemplateMessageService(@Named("PinCypher") KeyPolicy keyPolicy) {
        super(keyPolicy);
    }

    @Override
    public String getServiceName() { return "Hmac Template Msg\nAutorizar Transferência Bancária"; }

    @Override
    public int getServiceCode() { return 10; }

    @Override
    public byte[] getData() throws GeneralSecurityException {
        return keyPolicy.apply(hmacKeyPolicy.apply(getMessage()));
    }

    @Override
    public byte[] getMessage() {
        return BinMsg.getInstance().append(date).append(pin).append(template).append(params).toByteArray();
    }

    @Override
    public KeyPolicy getKeyPolicy() { return null; }

    public void setPin(PinParam pin) { this.pin = pin; }

    public void setTemplate(ByteWrapperParam template) { this.template = template; }

    public void setParams(Param... params) {
        this.params = Arrays.asList(params);
    }

    public void setDate(TimestampParam date) {
        this.date = date;
    }

    public void setChaveAes(Chave chaveAes) {
        keyPolicy.setKey(chaveAes.getValor().getBytes());
    }

    public void setChaveHmac(Chave chaveHmac) {
        hmacKeyPolicy.setKey(chaveHmac.getValor().getBytes());
    }
}
