package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.params.*;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class OneStepDoubleSymmetricKeyImportService extends AbstractService implements TimestampAble, PinAble {
    protected TemplateParam template;
    protected KeyTypeParam keyType1;
    protected KeyLengthParam keyLength1;
    protected KeyTypeParam keyType2;
    protected KeyLengthParam keyLength2;
    protected KeyParam key;
    protected CrcParam crc;
    protected DesafioParam desafio;

    @Inject
    public OneStepDoubleSymmetricKeyImportService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.passwordPolicy = passwordPolicy;
    }

    @Override
    public void setTimestamp(Date timestamp){
        this.timestampPolicy.setDate(timestamp);
    }
    @Override
    public void setPin(String pin) {
        this.passwordPolicy.setPassword(pin);
    }
    public void setTemplate(byte template) { this.template = new TemplateParam(template); }
    public void setKeyType1(KeyType keyType){ this.keyType1 = new KeyTypeParam(keyType); }
    public void setKeyType2(KeyType keyType){ this.keyType2 = new KeyTypeParam(keyType); }
    public void setKeyLength1(int keyLength){ this.keyLength1 = new KeyLengthParam(keyLength); }
    public void setKeyLength2(int keyLength){ this.keyLength2 = new KeyLengthParam(keyLength); }
    public void setDesafio(String desafio){ this.desafio = new DesafioParam(desafio); }

    public void setKeys(byte[] key){
        this.key = new KeyParam(key);
        this.crc = new CrcParam(key);
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(template, keyType1, keyLength1, keyType2, keyLength2, key, crc, desafio).toByteArray();
    }
}
