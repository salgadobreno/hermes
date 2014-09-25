package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.params.StringWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
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
    private ByteWrapperParam template;
    private KeyTypeParam keyType;

    @Inject
    protected DeleteSymKeyService(AesKeyPolicy keyPolicy) {
        super(keyPolicy);
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
        return keyPolicy.apply(getMessage());
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp).append(template).append(keyType).toByteArray();
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return null;
    }

    public void setTimestamp(Date date){
        this.timestamp = new TimestampParam(date);
    }

    public void setTemplate(byte template){
        this.template = new ByteWrapperParam(template);
    }

    public void setKeyType(KeyTypeParam.KeyType keyType){
        this.keyType = new KeyTypeParam(keyType);
    }
}
