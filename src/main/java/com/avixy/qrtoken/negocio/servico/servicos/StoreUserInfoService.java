package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.params.StringWithLengthParam;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 01/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StoreUserInfoService extends AbstractService implements AesCrypted, HmacAble {
    private StringWithLengthParam nome;
    private StringWithLengthParam email;
    private StringWithLengthParam cliente;
    private StringWithLengthParam cpf;
    private StringWithLengthParam agencia;
    private StringWithLengthParam conta;
    private StringWithLengthParam telefone;

    @Inject
    protected StoreUserInfoService(QrtHeaderPolicy headerPolicy, AesCryptedMessagePolicy messagePolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy);
        this.messagePolicy = messagePolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
    }

    @Override
    public String getServiceName() {
        return "Gravar informações de Usuário\n";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_STORE_USER_INFO;
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(nome, email, cpf, cliente, agencia, conta, telefone).toByteArray();
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

    @Override
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy) messagePolicy).setKey(key);
    }

    @Override
    public void setHmacKey(byte[] key) {
        hmacKeyPolicy.setKey(key);
    }
}
