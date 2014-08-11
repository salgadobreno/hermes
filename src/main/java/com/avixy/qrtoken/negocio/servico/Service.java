package com.avixy.qrtoken.negocio.servico;

/**
 * Created on 31/07/2014
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface Service {
    public String getServiceName();

    public int getServiceCode();

    /**
     *
     * @return Tripa de dados que representam os parâmetros do serviço conforme definido no protocolo de serviços
     */
    byte[] getData();

}
