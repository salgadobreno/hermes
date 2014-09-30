package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.*;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;

import java.util.Date;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class OneStepDoubleSymmetricKeyImportService extends AbstractService {
    protected TimestampParam timestamp;
    protected PinParam pin;
    protected TemplateParam template;
    protected KeyTypeParam keyType1;
    protected KeyLengthParam keyLength1;
    protected KeyTypeParam keyType2;
    protected KeyLengthParam keyLength2;
    protected KeyParam key;
    protected CrcParam crc;
    protected DesafioParam desafio;

    protected OneStepDoubleSymmetricKeyImportService(NullKeyPolicy keyPolicy) {
        super(keyPolicy);
    }

    public void setTimestamp(Date timestamp){ this.timestamp = new TimestampParam(timestamp); }
    public void setPin(String pin) { this.pin = new PinParam(pin); }
    public void setTemplate(byte template) { this.template = new TemplateParam(template); }
    public void setKeyType1(KeyTypeParam.KeyType keyType){ this.keyType1 = new KeyTypeParam(keyType); }
    public void setKeyType2(KeyTypeParam.KeyType keyType){ this.keyType2 = new KeyTypeParam(keyType); }
    public void setKeyLength1(int keyLength){ this.keyLength1 = new KeyLengthParam(keyLength); }
    public void setKeyLength2(int keyLength){ this.keyLength2 = new KeyLengthParam(keyLength); }
    public void setDesafio(String desafio){ this.desafio = new DesafioParam(desafio); }

    public void setKeys(String key){
        this.key = new KeyParam(key);
        this.crc = new CrcParam(key.getBytes());
    }

    @Override
    public String getServiceName() {
        return "SERVICE_ONE_STEP_CLEARTEXT_DOUBLE_SYM_KEY_IMPORT";
    }

    @Override
    public int getServiceCode() {
        return 33;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp, pin, template, keyType1, keyLength1, keyType2, keyLength2, key, crc, desafio).toByteArray();
    }
}
