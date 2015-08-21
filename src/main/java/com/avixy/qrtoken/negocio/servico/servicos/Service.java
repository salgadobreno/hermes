package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrTokenCode;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.MessageAble;

import java.util.List;

/**
 * A service that QR Token supports
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 31/07/2014
 */
public interface Service extends MessageAble {

    String getServiceName();

    ServiceCode getServiceCode();

    List<QrTokenCode> getQrs(QrSetup qrSetup) throws Exception;

    byte[] getMessage();
}
