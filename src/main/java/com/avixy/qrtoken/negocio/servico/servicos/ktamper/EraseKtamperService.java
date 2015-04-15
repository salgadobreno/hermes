package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.negocio.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.PukAble;
import com.avixy.qrtoken.negocio.servico.operations.NoPasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class EraseKtamperService extends AbstractService implements TimestampAble, PinAble, PukAble, PasswordOptional {
    private final PasswordPolicy originalPasswordPolicy;

    @Inject
    public EraseKtamperService(HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.passwordPolicy = passwordPolicy;
        originalPasswordPolicy = passwordPolicy;
    }

    @Override
    public String getServiceName() {
        return "Apagar K_Tamper";
    }

    @Override
    public ServiceCode getServiceCode() {
        if (passwordPolicy == originalPasswordPolicy) {
            return ServiceCode.SERVICE_ERASE_KTAMPER;
        } else {
            return ServiceCode.SERVICE_ERASE_KTAMPER_WITHOUT_PIN;
        }
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }

    @Override
    public void setPin(String pin){
        this.passwordPolicy.setPassword(pin);
    }

    @Override
    public void setTimestamp(Date date){
        this.timestampPolicy.setDate(date);
    }

    @Override
    public void setPuk(String puk) {
        /* "é obrigatório OU o PIN OU PUK" */
        setPin(puk);
    }

    @Override
    public void togglePasswordOptional(boolean passwordOptional) {
        if (passwordOptional) {
            this.passwordPolicy = NO_PASSWORD_POLICY;
        } else  {
            this.passwordPolicy = originalPasswordPolicy;
        }
    }

}
