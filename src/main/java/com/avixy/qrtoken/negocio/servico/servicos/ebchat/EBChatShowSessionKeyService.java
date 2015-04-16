package com.avixy.qrtoken.negocio.servico.servicos.ebchat;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.ChallengeParam;
import com.avixy.qrtoken.negocio.servico.params.KeyParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 16/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class EBChatShowSessionKeyService extends AbstractService implements TimestampAble, AesCrypted, HmacAble {
    private ChallengeParam challenge;
    private KeyParam sessionSecrecyKey;
    private KeyParam sessionAuthKey;

    @Inject
    public EBChatShowSessionKeyService(HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, AesCryptedMessagePolicy messagePolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.messagePolicy = messagePolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    @Override
    public String getServiceName() {
        return "EB Chat - Chave de sessão";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_SESSION_KEY;
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(challenge, sessionSecrecyKey, sessionAuthKey).toByteArray();
    }

    @Override
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy)this.messagePolicy).setKey(key);
    }

    @Override
    public void setHmacKey(byte[] key) {
        hmacKeyPolicy.setKey(key);
    }

    @Override
    public void setTimestamp(Date date) {
        timestampPolicy.setDate(date);
    }

    public void setChallenge(String challenge) {
        this.challenge = new ChallengeParam(challenge);
    }

    public void setSessionSecrecyKey(byte[] sessionSecrecyKey) {
        this.sessionSecrecyKey = new KeyParam(sessionSecrecyKey);
    }

    public void setSessionAuthKey(byte[] sessionAuthKey) {
        this.sessionAuthKey = new KeyParam(sessionAuthKey);
    }
}
