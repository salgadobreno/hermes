package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.StringWithLengthParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * Created on 01/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StoreUserInfoService extends AbstractService {
    private StringWithLengthParam nome;
    private StringWithLengthParam email;
    private StringWithLengthParam cliente;
    private StringWithLengthParam cpf;
    private StringWithLengthParam agencia;
    private StringWithLengthParam conta;
    private StringWithLengthParam telefone;

    private AesKeyPolicy aesKeyPolicy;
    private HmacKeyPolicy hmacKeyPolicy;

    @Inject
    protected StoreUserInfoService(QrtHeaderPolicy headerPolicy, AesKeyPolicy aesKeyPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.aesKeyPolicy = aesKeyPolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    @Override
    public String getServiceName() {
        return "SERVICE_STORE_USER_INFO\n";
    }

    @Override
    public int getServiceCode() {
        return 2;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        byte[] header = headerPolicy.getHeader(this);
        byte[] msg = getMessage();
        byte[] vi = aesKeyPolicy.getInitializationVector();
        byte[] body = ArrayUtils.addAll(aesKeyPolicy.apply(msg), vi);
        byte[] data = ArrayUtils.addAll(header, body);
        byte[] hmac = hmacKeyPolicy.apply(data);
        return hmac;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(nome, email, cpf, cliente, agencia, conta, telefone).toByteArray();
    }

    public void setNome(String nome) {
        if (nome.length() > 30) { throw new IllegalArgumentException(); }
        this.nome = new StringWithLengthParam(nome);
    }

    public void setEmail(String email) {
        if (email.length() > 40) { throw new IllegalArgumentException(); }
        this.email = new StringWithLengthParam(email);
    }

    public void setCpf(String cpf) {
        if (cpf.length() > 20) { throw new IllegalArgumentException(); }
        this.cpf = new StringWithLengthParam(cpf);
    }

    public void setCliente(String cliente) {
        if (cliente.length() > 20) { throw new IllegalArgumentException(); }
        this.cliente = new StringWithLengthParam(cliente);
    }

    public void setAgencia(String agencia) {
        if (agencia.length() > 10) { throw new IllegalArgumentException(); }
        this.agencia = new StringWithLengthParam(agencia);
    }

    public void setConta(String conta) {
        if (conta.length() > 10) { throw new IllegalArgumentException(); }
        this.conta = new StringWithLengthParam(conta);
    }

    public void setTelefone(String telefone) {
        if (telefone.length() > 20) { throw new IllegalArgumentException(); }
        this.telefone = new StringWithLengthParam(telefone);
    }

    public void setAesKey(byte[] bytes){
        aesKeyPolicy.setKey(bytes);
    }

    public void setHmacKey(byte[] bytes){
        hmacKeyPolicy.setKey(bytes);
    }
}
