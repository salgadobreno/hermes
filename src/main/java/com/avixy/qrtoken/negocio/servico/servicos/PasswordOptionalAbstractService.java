package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;

/**
 * Created on 17/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class PasswordOptionalAbstractService extends AbstractService implements PasswordOptional, PinAble {
    protected final PasswordPolicy originalPasswordPolicy;

    public PasswordOptionalAbstractService(HeaderPolicy headerPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy);
        this.passwordPolicy = passwordPolicy;

        this.originalPasswordPolicy = passwordPolicy;
    }

    @Override
    public void togglePasswordOptional(boolean passwordOptional) {
        if (passwordOptional) {
            this.passwordPolicy = NO_PASSWORD_POLICY;
        } else  {
            this.passwordPolicy = originalPasswordPolicy;
        }
    }

    @Override
    public void setPin(String pin) {
        this.passwordPolicy.setPassword(pin);
    }
}
