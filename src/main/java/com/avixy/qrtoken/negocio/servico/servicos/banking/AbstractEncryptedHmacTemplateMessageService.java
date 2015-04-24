package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.negocio.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 02/09/2014
 */
public abstract class AbstractEncryptedHmacTemplateMessageService extends AbstractEncryptedTemplateMessageService implements HmacAble {
    protected final PasswordPolicy originalPasswordPolicy;

    @Inject
    public AbstractEncryptedHmacTemplateMessageService(QrtHeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, passwordPolicy);
        this.hmacKeyPolicy = hmacKeyPolicy;
        this.originalPasswordPolicy = passwordPolicy;
    }

    @Override
    public String getServiceName() { return "Encrypted Template Message"; }

    @Override
    public void setHmacKey(byte[] key) {
        this.hmacKeyPolicy.setKey(key);
    }

}
