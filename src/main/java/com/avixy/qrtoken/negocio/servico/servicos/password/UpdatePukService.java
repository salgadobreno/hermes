package com.avixy.qrtoken.negocio.servico.servicos.password;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.behaviors.PukAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.StringWithLengthParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdatePukService extends AbstractService implements TimestampAble, PukAble {
    private StringWithLengthParam newPuk;

    @Inject
    protected UpdatePukService(QrtHeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.passwordPolicy = passwordPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_UPDATE_PUK";
    }

    @Override
    public int getServiceCode() {
        return 26;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(newPuk).toByteArray();
    }

    public void setNewPuk(String newPuk) {
        this.newPuk = new StringWithLengthParam(newPuk);
    }

    @Override
    public void setTimestamp(Date date){
        this.timestampPolicy.setDate(date);
    }

    @Override
    public void setPuk(String puk) {
        this.passwordPolicy.setPassword(puk);
    }
}
