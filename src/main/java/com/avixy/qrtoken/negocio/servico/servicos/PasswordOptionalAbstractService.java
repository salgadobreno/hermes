package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.behaviors.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;

/**
 * Base implementation of a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} with optional PIN.
 * Implementing classes only need to check if <code>originalPasswordPolicy</code> is the same as current
 * {@link com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy} and return the correct {@link com.avixy.qrtoken.negocio.servico.ServiceCode}
 *
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
    public boolean hasPin() {
        return this.passwordPolicy == originalPasswordPolicy;
    }

    @Override
    public void setPin(String pin) {
        this.passwordPolicy.setPassword(pin);
    }
}
