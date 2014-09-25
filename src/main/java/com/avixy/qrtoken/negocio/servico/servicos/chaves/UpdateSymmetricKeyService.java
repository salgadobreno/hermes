package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.params.StringWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.TemplateParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.util.Date;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdateSymmetricKeyService extends AbstractService {
    private TimestampParam timestamp;
    private TemplateParam template;
    private KeyTypeParam keyType;
    private StringWrapperParam key;

    @Inject
    protected UpdateSymmetricKeyService(AesKeyPolicy keyPolicy) {
        super(keyPolicy);
    }

    @Override
    public String getServiceName() {
        return "SERVICE_UPDATE_SYMMETRIC_KEY";
    }

    @Override
    public int getServiceCode() {
        return 37;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp).append(template).append(keyType).append(key).toByteArray();
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        return keyPolicy.apply(getMessage());
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

    public void setKey(String key){
        this.key = new StringWrapperParam(key);
    }
}
