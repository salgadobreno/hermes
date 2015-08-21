package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.RangedTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.lang.text.StrSubstitutor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 15/09/2014
 */
public class AutorizarTransferenciaBancariaService extends AbstractEncryptedHmacTemplateMessageService {
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private String origemTemplate = "${nomeOrigem}\nAgência: ${agenciaOrigem}\nConta:${contaOrigem}";
    private String destinoTemplate = "${nomeDestino}\nAgência: ${agenciaDestino}\nConta:${contaDestino}";

    private Map<String, String> templateParams = new HashMap<>();

    private HuffmanEncodedParam origemParam;
    private HuffmanEncodedParam destinoParam;
    private HuffmanEncodedParam dateParam;
    private HuffmanEncodedParam valorParam;
    private HuffmanEncodedParam tanParam;

    @Inject
    public AutorizarTransferenciaBancariaService(QrtHeaderPolicy headerPolicy, RangedTimestampPolicy timestampPolicy, AesCryptedMessagePolicy aesCryptedMessagePolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy) {
        super(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, passwordPolicy);
    }

    @Override
    public String getServiceName() { return "Encrypted Template Message\nAutorizar Transferência Bancária"; }

    @Override
    public byte[] getMessage() {
        StrSubstitutor substitutor = new StrSubstitutor(templateParams);
        String resolvedOrigem = substitutor.replace(origemTemplate);
        String resolvedDestino = substitutor.replace(destinoTemplate);

        origemParam = new HuffmanEncodedParam(resolvedOrigem);
        destinoParam = new HuffmanEncodedParam(resolvedDestino);

        BinaryMsg msg = BinaryMsg.create().append(templateSlot, origemParam, destinoParam, dateParam, valorParam, tanParam);
        return msg.toByteArray();
    }

    public void setNomeOrigem(String nomeOrigem) {
        templateParams.put("nomeOrigem", nomeOrigem);
    }

    public void setAgenciaOrigem(String agenciaOrigem) {
        templateParams.put("agenciaOrigem", agenciaOrigem);
    }

    public void setContaOrigem(String contaOrigem) {
        templateParams.put("contaOrigem", contaOrigem);
    }

    public void setNomeDestino(String nomeDestino) {
        templateParams.put("nomeDestino", nomeDestino);
    }

    public void setAgenciaDestino(String agenciaDestino) {
        templateParams.put("agenciaDestino", agenciaDestino);
    }

    public void setContaDestino(String contaDestino) {
        templateParams.put("contaDestino", contaDestino);
    }

    public void setValor(String valor) {
        valorParam = new HuffmanEncodedParam(valor);
    }

    public void setDate(Date date) {
        dateParam = new HuffmanEncodedParam(dateFormat.format(date));
    }

    public void setTan(String tan) {
        this.tanParam = new HuffmanEncodedParam(tan);
    }
}
