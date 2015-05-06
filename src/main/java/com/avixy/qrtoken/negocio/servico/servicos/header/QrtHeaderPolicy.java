package com.avixy.qrtoken.negocio.servico.servicos.header;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.Token;

import javax.annotation.Nullable;

/**
 * Header 'básico'
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
public class QrtHeaderPolicy implements HeaderPolicy {

    @Override
    public byte[] getHeader(Service service, @Nullable ServiceCode serviceCode) {
        return new byte[]{
                0, 0,
                Token.PROTOCOL_VERSION,
                (byte) (serviceCode == null ? service.getServiceCode().ordinal() : serviceCode.ordinal())
        };
    }
}
