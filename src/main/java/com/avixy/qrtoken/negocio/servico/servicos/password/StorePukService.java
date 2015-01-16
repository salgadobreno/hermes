package com.avixy.qrtoken.negocio.servico.servicos.password;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.params.PukParam;
import com.avixy.qrtoken.negocio.servico.params.StringWithLengthParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StorePukService extends AbstractService {
    private StringWithLengthParam puk;

    @Inject
    public StorePukService(HeaderPolicy headerPolicy) {
        super(headerPolicy);
    }

    @Override
    public String getServiceName() {
        return "Gravar PUK";
    }

    @Override
    public int getServiceCode() {
        return 25;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(puk).toByteArray();
    }

    public void setPuk(String puk) {
        this.puk = new StringWithLengthParam(puk);
    }

}
