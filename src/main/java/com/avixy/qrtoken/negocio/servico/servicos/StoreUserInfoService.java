package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.StringWithLengthParam;
import com.avixy.qrtoken.negocio.servico.params.TemplateParam;
import com.google.inject.Inject;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;

/**
 * Created on 01/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StoreUserInfoService extends AbstractService {
    private TemplateParam template;
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
    protected StoreUserInfoService(AesKeyPolicy aesKeyPolicy, HmacKeyPolicy hmacKeyPolicy) {
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
        return aesKeyPolicy.apply(hmacKeyPolicy.apply(getMessage())); //TODO: put it in the abstract already
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(template, nome, email, cpf, conta, agencia, cliente, telefone).toByteArray();
    }

    public void setNome(String nome) {
        this.nome = new StringWithLengthParam(nome);
    }

    public void setEmail(String email) {
        this.email = new StringWithLengthParam(email);
    }

    public void setCliente(String cliente) {
        this.cliente = new StringWithLengthParam(cliente);
    }

    public void setCpf(String cpf) {
        this.cpf = new StringWithLengthParam(cpf);
    }

    public void setAgencia(String agencia) {
        this.agencia = new StringWithLengthParam(agencia);
    }

    public void setConta(String conta) {
        this.conta = new StringWithLengthParam(conta);
    }

    public void setTelefone(String telefone) {
        this.telefone = new StringWithLengthParam(telefone);
    }

    public void setTemplate(int template) {
        this.template = new TemplateParam((byte) template);
    }

    public void setAesKey(byte[] bytes){
        aesKeyPolicy.setKey(bytes);
    }

    public void setHmacKey(byte[] bytes){
        hmacKeyPolicy.setKey(bytes);
    }
}
