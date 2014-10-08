package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.*;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class OneStepSymmetricKeyImportService extends AbstractService {
    protected TimestampParam timestamp;
    protected PinParam pin;
    protected TemplateParam template;
    protected KeyComponentParam keyComponent;
    protected DesafioParam desafio;

    @Inject
    protected OneStepSymmetricKeyImportService() { }

    public void setTimestamp(Date timestamp) {
        this.timestamp = new TimestampParam(timestamp);
    }

    public void setPin(String pin) {
        this.pin = new PinParam(pin);
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
        return BinnaryMsg.create().append(timestamp, pin, template, keyComponent, desafio).toByteArray();
    }

    public void setDesafio(String desafio) {
        this.desafio = new DesafioParam(desafio);
    }
}
