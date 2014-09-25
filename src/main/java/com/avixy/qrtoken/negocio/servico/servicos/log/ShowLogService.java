package com.avixy.qrtoken.negocio.servico.servicos.log;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ShowLogService extends AbstractService {

    @Inject
    protected ShowLogService(NullKeyPolicy keyPolicy) {
        super(keyPolicy);
    }

    @Override
    public String getServiceName() {
        return "SERVICE_SHOW_LOG";
    }

    @Override
    public int getServiceCode() {
        return 57;
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return null;
    }
}
