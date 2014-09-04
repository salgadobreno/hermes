package com.avixy.qrtoken.negocio.servico.header;

import com.avixy.qrtoken.negocio.servico.Service;

/**
 * Created on 04/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class QrtHeaderPolicy implements HeaderPolicy {

    @Override
    public byte[] getHeader(Service service) {
        return new byte[]{0, 0, (byte) service.getServiceCode()};
    }
}
