package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.*;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class DeleteSymKeyService extends AbstractService {
    private TimestampParam timestamp;
    private TemplateParam template;
    private KeyTypeParam keyType;

    private AesKeyPolicy aesKeyPolicy;

    @Inject
    protected DeleteSymKeyService(AesKeyPolicy keyPolicy) {
        this.aesKeyPolicy = keyPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_DELETE_SYM_KEY";
    }

    @Override
    public int getServiceCode() {
        return 36;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        return aesKeyPolicy.apply(getMessage());
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp).append(template).append(keyType).toByteArray();
    }

    public void setTimestamp(Date date){
        this.timestamp = new TimestampParam(date);
    }

    public void setTemplate(byte template){
        this.template = new TemplateParam(template);
    }

    public void setKeyType(KeyTypeParam.KeyType keyType){
        this.keyType = new KeyTypeParam(keyType);
    }
}
