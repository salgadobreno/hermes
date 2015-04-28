package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.core.extensions.components.TextFieldLimited;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 30/01/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ImportClientSymKeySetService extends AbstractService implements TimestampAble, AesCrypted, HmacAble {

    private KeyParam clientAesKey;
    private KeyParam clientAuthKey;

    @Inject
    public ImportClientSymKeySetService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy messagePolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.messagePolicy = messagePolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    @Override
    public String getServiceName() {
        return "Importar chave simétrica - Cliente";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_IMPORT_CLIENT_SYM_KEYSET;
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(clientAesKey, clientAuthKey).toByteArray();
    }

    @Override
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy)this.messagePolicy).setKey(key);
    }

    @Override
    public void setHmacKey(byte[] key) {
        this.hmacKeyPolicy.setKey(key);
    }

    public void setClientAuthKey(byte[] clientAuthKey) {
        this.clientAuthKey = new KeyParam(clientAuthKey);
    }

    public void setClientAesKey(byte[] clientAesKey) {
        this.clientAesKey = new KeyParam(clientAesKey);
    }

    @Override
    public void setTimestamp(Date date) {
        this.timestampPolicy.setDate(date);
    }
}
