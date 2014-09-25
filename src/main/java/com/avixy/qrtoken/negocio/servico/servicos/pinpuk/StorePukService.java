package com.avixy.qrtoken.negocio.servico.servicos.pinpuk;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.PukParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StorePukService extends AbstractService {
    private PukParam puk;

    @Inject
    protected StorePukService(NullKeyPolicy keyPolicy) {
        super(keyPolicy);
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

    @Override
    public KeyPolicy getKeyPolicy() {
        return null;
    }

    public void setPuk(String puk) {
        this.puk = new PukParam(puk);
    }

}
