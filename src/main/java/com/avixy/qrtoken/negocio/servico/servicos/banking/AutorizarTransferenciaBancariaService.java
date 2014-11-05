package com.avixy.qrtoken.negocio.servico.servicos.banking;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractEncryptedHmacTemplateMessageService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.bouncycastle.crypto.CryptoException;

import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang.ArrayUtils.*;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 15/09/2014
 */
public class AutorizarTransferenciaBancariaService extends AbstractEncryptedHmacTemplateMessageService {
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private String origemTemplate = "${nomeOrigem}\nAgência: ${agenciaOrigem}\nConta:${contaOrigem}";
    private String destinoTemplate = "${nomeDestino}\nAgência: ${agenciaDestino}\nConta:${contaDestino}";
    private String dadosTemplate = "${data}\nValor: R$ ${valor}";

    private Map<String, String> templateParams = new HashMap<>();

    private HuffmanEncodedParam origemParam;
    private HuffmanEncodedParam destinoParam;
    private HuffmanEncodedParam dadosParam;
    private HuffmanEncodedParam tanParam;

    @Inject
    public AutorizarTransferenciaBancariaService(QrtHeaderPolicy headerPolicy, AesKeyPolicy keyPolicy, HmacKeyPolicy hmacKeyPolicy) {
        super(headerPolicy, keyPolicy, hmacKeyPolicy);
    }

    @Override
    public String getServiceName() { return "Encrypted Template Message\nAutorizar Transferência Bancária"; }

    @Override
    public byte[] getMessage() {
        StrSubstitutor substitutor = new StrSubstitutor(templateParams);
        String resolvedOrigem = substitutor.replace(origemTemplate);
        String resolvedDestino = substitutor.replace(destinoTemplate);
        String resolvedDados = substitutor.replace(dadosTemplate);

        origemParam = new HuffmanEncodedParam(resolvedOrigem);
        destinoParam = new HuffmanEncodedParam(resolvedDestino);
        dadosParam = new HuffmanEncodedParam(resolvedDados);

//        BinnaryMsg msg = BinnaryMsg.create().append(template, origemParam, destinoParam, dadosParam, pin);
        BinnaryMsg msg = BinnaryMsg.create().append(template, origemParam, destinoParam, dadosParam, tanParam);
        return msg.toByteArray();
    }

    @Override
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        byte[] message = getMessage();
        byte[] data, header, tstamp, criptedParams, iv, hmac, pinBytes;
        data = new byte[0];
        try {
            //1 - header
            header = headerPolicy.getHeader(this);
            //1.1 - Timestamp
            tstamp = BinnaryMsg.get(timestamp.toBinaryString());
            //2- iv
            iv = aesKeyPolicy.getInitializationVector();
            //3- criptedParams
            criptedParams = aesKeyPolicy.apply(message);
            //4- hmac
            byte[] hmacBloc = addAll(header, tstamp);
            hmacBloc = addAll(hmacBloc, criptedParams);
            hmacBloc = addAll(hmacBloc, iv);
            hmac = hmacKeyPolicy.apply(hmacBloc);
            //5- pin
            pinBytes = BinnaryMsg.create().append(pin).toByteArray();
            //6- data = hmac + pin
            data = addAll(hmac, pinBytes);
        } catch (CryptoException | GeneralSecurityException e) {
            e.printStackTrace();
        }

        return data;
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
        templateParams.put("valor", valor);
    }

    public void setData(Date data) {
        templateParams.put("data", dateFormat.format(data));
    }

    public void setTan(String tan) {
        this.tanParam = new HuffmanEncodedParam(tan);
    }
}
