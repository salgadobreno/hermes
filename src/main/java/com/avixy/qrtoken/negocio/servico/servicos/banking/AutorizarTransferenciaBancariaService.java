package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractEncryptedHmacTemplateMessageService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 15/09/2014
 */
public class AutorizarTransferenciaBancariaService extends AbstractEncryptedHmacTemplateMessageService {
    private HuffmanEncodedParam nomeOrigem;
    private HuffmanEncodedParam agenciaOrigem;
    private HuffmanEncodedParam contaOrigem;
    private HuffmanEncodedParam nomeDestino;
    private HuffmanEncodedParam agenciaDestino;
    private HuffmanEncodedParam contaDestino;
    private HuffmanEncodedParam valor;
    private HuffmanEncodedParam data;
    private HuffmanEncodedParam tan;

    @Inject
    public AutorizarTransferenciaBancariaService(QrtHeaderPolicy headerPolicy, AesKeyPolicy keyPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy, keyPolicy, hmacKeyPolicy);
    }

    @Override
    public String getServiceName() { return "Encrypted Template Message\nAutorizar Transferência Bancária"; }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp, pin, template, nomeOrigem, agenciaOrigem, contaOrigem, nomeDestino, agenciaDestino, contaDestino, valor, data, tan).toByteArray();
    }

    public void setNomeOrigem(String nomeOrigem) {
        this.nomeOrigem = new HuffmanEncodedParam(nomeOrigem);
    }

    public void setAgenciaOrigem(String agenciaOrigem) {
        this.agenciaOrigem = new HuffmanEncodedParam(agenciaOrigem);
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = new HuffmanEncodedParam(contaOrigem);
    }

    public void setNomeDestino(String nomeDestino) {
        this.nomeDestino = new HuffmanEncodedParam(nomeDestino);
    }

    public void setAgenciaDestino(String agenciaDestino) {
        this.agenciaDestino = new HuffmanEncodedParam(agenciaDestino);
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = new HuffmanEncodedParam(contaDestino);
    }

    public void setValor(String valor) {
        this.valor = new HuffmanEncodedParam(valor);
    }

    public void setData(Date data) {
        this.data = new HuffmanEncodedParam(data.toString()); //TODO
    }

    public void setTan(String tan) {
        this.tan = new HuffmanEncodedParam(tan);
    }
}
