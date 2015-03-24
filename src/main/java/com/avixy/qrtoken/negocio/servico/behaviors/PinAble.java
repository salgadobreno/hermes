package com.avixy.qrtoken.negocio.servico.behaviors;

/**
 * Defines a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} that requires PIN signature
 *
 * Created on 05/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface PinAble {
    void setPin(String pin);
}
