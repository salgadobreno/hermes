package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.servicos.PinAble;
import com.avixy.qrtoken.negocio.servico.operations.PinPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.TimestampAble;
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
public class OneStepSymmetricKeyImportService extends AbstractService implements TimestampAble, PinAble {
    protected TemplateParam template;
    protected KeyComponentParam keyComponent;
    protected DesafioParam desafio;

    @Inject
    public OneStepSymmetricKeyImportService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PinPolicy pinPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.pinPolicy = pinPolicy;
    }

    @Override
    public void setTimestamp(Date timestamp) {
        this.timestampPolicy.setDate(timestamp);
    }

    @Override
    public void setPin(String pin) {
        this.pinPolicy.setPin(pin);
    }

    public void setTemplate(byte template) {
        this.template = new TemplateParam(template);
    }

    public void setKeyComponent(KeyTypeParam.KeyType keyType, int keyLength, String key) {
        this.keyComponent = new KeyComponentParam(keyType, keyLength, key);
    }

    @Override
    public String getServiceName() {
        return "SERVICE_ONE_STEP_CLEARTEXT_SYM_KEY_IMPORT";
    }

    @Override
    public int getServiceCode() {
       return 32;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(template, keyComponent, desafio).toByteArray();
    }

    public void setDesafio(String desafio) {
        this.desafio = new DesafioParam(desafio);
    }
}
