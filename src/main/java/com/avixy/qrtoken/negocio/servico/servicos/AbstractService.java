package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrTokenCode;
import com.avixy.qrtoken.negocio.servico.ServiceAssembler;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.*;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 03/09/2014
 */
public abstract class AbstractService implements Service {

    private ServiceAssembler serviceAssembler = new ServiceAssembler();

    protected HeaderPolicy headerPolicy = new NoHeaderPolicy();
    protected TimestampPolicy timestampPolicy = new NoTimestampPolicy();
    protected MessagePolicy messagePolicy = new DefaultMessagePolicy();
    protected HmacKeyPolicy hmacKeyPolicy = new NoMacPolicy();
    protected PasswordPolicy passwordPolicy = new NoPasswordPolicy();

    public AbstractService(HeaderPolicy headerPolicy) {
        this.headerPolicy = headerPolicy;
    }

    @Override
    public List<QrTokenCode> getQrs(QrSetup setup) throws Exception {
        List<QrTokenCode> tokenCodeList = new ArrayList<>();
        QrTokenCode tokenCode = new QrTokenCode(serviceAssembler.get(this, headerPolicy, timestampPolicy, messagePolicy, hmacKeyPolicy, passwordPolicy), setup);
        tokenCodeList.add(tokenCode);
        return tokenCodeList;
    }
}
