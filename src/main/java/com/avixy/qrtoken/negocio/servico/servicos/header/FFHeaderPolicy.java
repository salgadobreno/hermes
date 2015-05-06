package com.avixy.qrtoken.negocio.servico.servicos.header;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.servicos.Service;

import javax.annotation.Nullable;

/**
 * Header especial usado na atualização de firmware
 * Created on 03/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class FFHeaderPolicy implements HeaderPolicy {
    @Override
    public byte[] getHeader(Service service, @Nullable ServiceCode serviceCode) {
        return new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) service.getServiceCode().ordinal()};
    }
}
