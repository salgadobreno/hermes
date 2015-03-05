package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrTokenCode;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.MessageAble;

import java.util.List;

/**
 * Interface que modela um serviço suportado pelo Avixy QR Token
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 31/07/2014
 */
public interface Service extends MessageAble {

    String getServiceName();

    ServiceCode getServiceCode();

    List<QrTokenCode> getQrs(QrSetup setup) throws Exception;

    /** @return Dados em claro do serviço */
    byte[] getMessage();
}
