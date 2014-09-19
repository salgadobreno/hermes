package com.avixy.qrtoken.negocio.servico.header;

import com.avixy.qrtoken.negocio.servico.Service;

/**
 * Estratégia de definição de header Qr Code de Serviço
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public interface HeaderPolicy {
    byte[] getHeader(Service service);
}
