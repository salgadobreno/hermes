package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.negocio.servico.params.PukParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StorePukService extends AbstractService {
    private PukParam puk;

    @Inject
    public StorePukService(HeaderPolicy headerPolicy) {
        super(headerPolicy);
    }

    @Override
    public String getServiceName() {
        return "SERVICE_STORE_PUK";
    }

    @Override
    public int getServiceCode() {
        return 25;
    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }

    public void setPuk(String puk) {
        this.puk = new PukParam(puk);
    }

}
