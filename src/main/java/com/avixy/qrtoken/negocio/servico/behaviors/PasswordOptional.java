package com.avixy.qrtoken.negocio.servico.behaviors;

import com.avixy.qrtoken.negocio.servico.operations.NoPasswordPolicy;

/**
 * Defines a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} in which it's {@link com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy}
 * can be turned on and off.
 *
 * See {@link com.avixy.qrtoken.negocio.servico.servicos.PasswordOptionalAbstractService} for basic implementation
 *
 * Created on 14/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface PasswordOptional {
    NoPasswordPolicy NO_PASSWORD_POLICY = new NoPasswordPolicy();

    boolean hasPin();

    void togglePasswordOptional(boolean isOptional);
}
