package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.crypto.KeyPolicy;

import java.security.GeneralSecurityException;

/**
 * Interface que modela um serviço suportado pelo Avixy QR Token
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 31/07/2014
 */
public interface Service {

    String getServiceName();

    int getServiceCode();

    /** @return Dados a serem convertidos em um código QR p/ executar uma função no Avixy QR Token */
    byte[] getData() throws GeneralSecurityException;

    /** @return Dados em claro do serviço */
    byte[] getMessage();

    KeyPolicy getKeyPolicy();

}
