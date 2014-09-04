package com.avixy.qrtoken.negocio.servico.header;

import com.avixy.qrtoken.negocio.servico.Service;

/**
 * Created on 03/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface HeaderPolicy {
    byte[] getHeader(Service service);
}
