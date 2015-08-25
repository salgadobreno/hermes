package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;

import java.util.Date;

/**
 * Created on 25/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class UpdateSymmetricKeyService extends AbstractService implements AesCrypted, HmacAble, TimestampAble {
    protected KeyParam secretKey;
    protected KeyParam authKey;

    protected UpdateSymmetricKeyService(QrtHeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, AesCryptedMessagePolicy messagePolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.messagePolicy = messagePolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(secretKey, authKey).toByteArray();
    }

    @Override
    public void setTimestamp(Date date){
        timestampPolicy.setDate(date);
    }

    public void setSecretKey(byte[] key){
        this.secretKey = new KeyParam(key);
    }

    public void setAuthKey(byte[] key) {
        this.authKey = new KeyParam(key);
    }

    @Override
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy) messagePolicy).setKey(key);
    }

    @Override
    public void setHmacKey(byte[] key) {
        hmacKeyPolicy.setKey(key);
    }
}
