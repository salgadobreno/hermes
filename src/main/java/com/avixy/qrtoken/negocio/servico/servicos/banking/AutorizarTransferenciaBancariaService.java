package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.avixy.qrtoken.negocio.servico.params.ParamFactory.getParam;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 15/09/2014
 */
public class AutorizarTransferenciaBancariaService extends EncryptedHmacTemplateMessageService {
    List<Param> paramsBuffer = new ArrayList<>();

    @Inject
    public AutorizarTransferenciaBancariaService(QrtHeaderPolicy headerPolicy, AesKeyPolicy keyPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy, keyPolicy, hmacKeyPolicy);
    }

    @Override
    public String getServiceName() { return "Encrypted Template Message\nAutorizar Transferência Bancária"; }

    @Override
    public byte[] getMessage() {
        this.params = paramsBuffer;
        paramsBuffer.clear(); // múltiplas chamadas devem ter o mesmo resultado - idempotência
        return super.getMessage();
    }

    public void setNomeOrigem(String nomeOrigem) {
        this.paramsBuffer.add(getParam(nomeOrigem));
    }

    public void setAgenciaOrigem(String agenciaOrigem) {
        this.paramsBuffer.add(getParam(agenciaOrigem));
    }

    public void setContaOrigem(String contaOrigem) {
        this.paramsBuffer.add(getParam(contaOrigem));
    }

    public void setNomeDestino(String nomeDestino) {
        this.paramsBuffer.add(getParam(nomeDestino));
    }

    public void setAgenciaDestino(String agenciaDestino) {
        this.paramsBuffer.add(getParam(agenciaDestino));
    }

    public void setContaDestino(String contaDestino) {
        this.paramsBuffer.add(getParam(contaDestino));
    }

    public void setValor(String valor) {
        this.paramsBuffer.add(getParam(valor));
    }

    public void setData(Date data) {
        this.paramsBuffer.add(getParam(data));
    }

    public void setTimestamp(Date timestamp) {
        this.date = getParam(timestamp);
    }

    public void setPin(String pin) {
        this.pin = new PinParam(pin);
    }

    public void setTan(String tan) {
        this.paramsBuffer.add(getParam(tan));
    }
}
