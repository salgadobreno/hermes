package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class OneStepSymmetricKeyImportService extends AbstractService implements TimestampAble, PinAble, PasswordOptional {
    private KeyParam secrecyKey;
    private KeyParam authKey;

    protected final PasswordPolicy originalPasswordPolicy;

    @Inject
    public OneStepSymmetricKeyImportService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.passwordPolicy = passwordPolicy;

        this.originalPasswordPolicy = passwordPolicy;
    }

    @Override
    public void setTimestamp(Date timestamp) {
        this.timestampPolicy.setDate(timestamp);
    }

    @Override
    public void setPin(String pin) {
        this.passwordPolicy.setPassword(pin);
    }

    public void setAuthKey(byte[] key) {
        this.authKey = new KeyParam(key);
    }

    public void setSecrecyKey(byte[] key) {
        this.secrecyKey = new KeyParam(key);
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(secrecyKey, authKey).toByteArray();
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
