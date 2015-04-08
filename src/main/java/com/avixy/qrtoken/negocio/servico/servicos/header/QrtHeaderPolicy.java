package com.avixy.qrtoken.negocio.servico.servicos.header;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.Token;

/**
 * Header 'básico'
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
public class QrtHeaderPolicy implements HeaderPolicy {

    @Override
    public byte[] getHeader(Service service) {
        return new byte[]{0, 0, Token.PROTOCOL_VERSION, (byte) service.getServiceCode().ordinal()};
    }
}
