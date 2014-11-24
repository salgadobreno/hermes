package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.ServiceAssembler;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.*;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;

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
    public byte[] run() throws Exception {
        byte[] data = serviceAssembler.get(this, headerPolicy, timestampPolicy, messagePolicy, hmacKeyPolicy, passwordPolicy);
        return data;
    }
}
