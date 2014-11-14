package com.avixy.qrtoken.negocio.servico.servicos;

/**
 * Interface que modela um serviço suportado pelo Avixy QR Token
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 31/07/2014
 */
public interface Service extends MessageAble {

    String getServiceName();

    int getServiceCode();

    /** @return Dados a serem convertidos em um código QR p/ executar uma função no Avixy QR Token */
    byte[] run() throws Exception;

    /** @return Dados em claro do serviço */
    byte[] getMessage();
}
