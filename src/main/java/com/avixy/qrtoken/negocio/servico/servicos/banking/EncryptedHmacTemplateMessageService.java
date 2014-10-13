package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 02/09/2014
 */
public class EncryptedHmacTemplateMessageService extends EncryptedTemplateMessageService {

    private KeyPolicy hmacKeyPolicy;
    private AesKeyPolicy aesKeyPolicy;

    @Inject
    public EncryptedHmacTemplateMessageService(QrtHeaderPolicy headerPolicy, AesKeyPolicy aesKeyPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy, aesKeyPolicy);
        this.aesKeyPolicy = aesKeyPolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    @Override
    public String getServiceName() { return "Encrypted Template Message"; }

    @Override
    public int getServiceCode() { return 10; }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        byte[] initializationVector = aesKeyPolicy.getInitializationVector();
        byte[] data = aesKeyPolicy.apply(hmacKeyPolicy.apply(getMessage()));

        return ArrayUtils.addAll(initializationVector, data);
    }

    @Override
    public byte[] getMessage() {
        return super.getMessage();
    }

    public void setChaveHmac(Chave chaveHmac) {
        hmacKeyPolicy.setKey(chaveHmac.getHexValue());
    }
}
