package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.GeneralSecurityException;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public abstract class AbstractService implements Service {
    private Logger logger = LoggerFactory.getLogger(AbstractService.class);

    protected HeaderPolicy headerPolicy;

    public AbstractService(HeaderPolicy headerPolicy) {
        this.headerPolicy = headerPolicy;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        logger.info("getData(): {}", headerPolicy.getHeader(this), getMessage());
        return ArrayUtils.addAll(headerPolicy.getHeader(this), getMessage());
    }
}
