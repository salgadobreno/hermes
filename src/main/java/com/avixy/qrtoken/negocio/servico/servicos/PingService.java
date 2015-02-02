package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public class PingService extends AbstractService {

    @Inject
    public PingService(QrtHeaderPolicy headerPolicy) {
        super(headerPolicy);
    }

    @Override
    public String getServiceName() {
        return "Ping";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_PING;
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }
}
