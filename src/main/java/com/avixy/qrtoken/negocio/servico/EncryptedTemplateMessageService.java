package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.core.ExBitSet;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.Param;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.lang.ArrayUtils;

import java.security.GeneralSecurityException;
import java.util.*;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 02/09/2014
 */
public class EncryptedTemplateMessageService extends AbstractService {

    private String pin;
    private int template;
    private int date;
    private List<Param> params = new ArrayList<>();

    private KeyPolicy hmacKeyPolicy = new HmacKeyPolicy();

    @Inject
    public EncryptedTemplateMessageService(@Named("PinCypher") KeyPolicy keyPolicy) {
        //TODO: header.. ?
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
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte)(date >> 24));
        bytes.add((byte)(date >> 16));
        bytes.add((byte)(date >> 8));
        bytes.add((byte)(date >> 0)); //timestamp

        String pinString = pin + '$';
        for (char c : pinString.toCharArray()) {
            bytes.add((byte) c);
        }
        bytes.add((byte) template);

        for (Param param : params) {
            BitSet bitSet = ExBitSet.createFromString(param.toBinaryString());
            for (byte b : bitSet.toByteArray()) {
                bytes.add(b);
            }
        }

        return ArrayUtils.toPrimitive(bytes.toArray(new Byte[bytes.size()]));
    }

    @Override
    public KeyPolicy getKeyPolicy() { return null; }

    public void setPin(String pin) { this.pin = pin; }

    public void setTemplate(int template) { this.template = template; }

    public void setParams(Param... params) {
        this.params = Arrays.asList(params);
    }

    public void setDate(Date date) {
        this.date = (int)(date.getTime() / 1000);
    }

    public void setChaveAes(Chave chaveAes) {
        keyPolicy.setKey(chaveAes.getValor().getBytes());
    }

    public void setChaveHmac(Chave chaveHmac) {
        hmacKeyPolicy.setKey(chaveHmac.getValor().getBytes());
    }
}
