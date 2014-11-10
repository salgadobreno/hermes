package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.negocio.servico.operations.MessagePolicy;
import com.avixy.qrtoken.negocio.servico.servicos.Service;

public class DefaultMessagePolicy implements MessagePolicy {
    @Override
    public byte[] get(Service service) {
        return service.getMessage();
    }
}
