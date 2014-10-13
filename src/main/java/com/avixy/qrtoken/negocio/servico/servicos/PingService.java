package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public class PingService extends AbstractService {
    private int SERVICE_CODE = 0b00000001;

    @Inject
    public PingService(QrtHeaderPolicy headerPolicy) {
        super(headerPolicy);
    }

    @Override
    public String getServiceName() {
        return "Ping";
    }

    @Override
    public int getServiceCode() {
        return SERVICE_CODE;
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }
}
