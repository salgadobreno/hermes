package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.header.HeaderPolicy;
import org.apache.commons.lang.ArrayUtils;

import java.security.GeneralSecurityException;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public class PingService extends AbstractService {
    private int SERVICE_CODE = 0b00000001;

    public PingService(HeaderPolicy headerPolicy, KeyPolicy keyPolicy) {
        super(headerPolicy, keyPolicy);
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
    public byte[] getData() throws GeneralSecurityException {
        return ArrayUtils.addAll(headerPolicy.getHeader(this), getMessage());
    }

    @Override
    public byte[] getMessage() {
        return new byte[]{(byte) SERVICE_CODE};
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return null;
    }
}
