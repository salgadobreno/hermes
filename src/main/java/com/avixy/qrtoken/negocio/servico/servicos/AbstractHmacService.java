package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * Created on 08/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class AbstractHmacService extends AbstractService {
    protected HmacKeyPolicy hmacKeyPolicy;

    public AbstractHmacService(HeaderPolicy headerPolicy, HmacKeyPolicy keyPolicy) {
        super(headerPolicy);
        hmacKeyPolicy = keyPolicy;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        byte[] plain = ArrayUtils.addAll(headerPolicy.getHeader(this), getMessage());
        return hmacKeyPolicy.apply(plain);
    }
}
