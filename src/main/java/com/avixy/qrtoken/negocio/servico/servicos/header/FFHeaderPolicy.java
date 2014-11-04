package com.avixy.qrtoken.negocio.servico.servicos.header;

import com.avixy.qrtoken.negocio.servico.servicos.Service;

/**
 * Created on 03/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class FFHeaderPolicy implements HeaderPolicy {
    @Override
    public byte[] getHeader(Service service) {
        return new byte[]{0xF, 0xF, (byte) service.getServiceCode()};
    }
}
