package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;

import javax.annotation.Nullable;

public class NoHeaderPolicy implements HeaderPolicy {

    @Override
    public byte[] getHeader(Service service, @Nullable ServiceCode serviceCode) {
        return new byte[0];
    }
}
