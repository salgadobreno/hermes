package com.avixy.qrtoken.negocio.servico;

import java.security.GeneralSecurityException;

/**
 * Created on 31/07/2014
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface Service {

    String getServiceName();

    int getServiceCode();

    /**
     *
     * @return Tripa de dados que representam os parâmetros do serviço conforme definido no protocolo de serviços
     */
    byte[] getData() throws GeneralSecurityException;

}

