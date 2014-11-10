package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;

public class NoHeaderPolicy implements HeaderPolicy {
    @Override
    public byte[] getHeader(Service service) { return new byte[0]; }
}
