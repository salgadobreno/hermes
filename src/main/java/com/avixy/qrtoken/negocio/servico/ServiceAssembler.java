package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.MessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PinPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;

import static org.apache.commons.lang.ArrayUtils.addAll;

/**
 * Created on 06/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ServiceAssembler {
    private ServiceAssembler() {
    }

    /**
     * Monta um código QR
     * @param service           serviço
     * @param headerPolicy      política de header
     * @param timestampPolicy   política de timestamp
     * @param messagePolicy     política da mensagem
     * @param hmacKeyPolicy     política de HMAC
     * @param pinPolicy         política de PIN
     * @return                  dados para execução de um serviço no Token
     * @throws Exception
     */
    public static byte[] get(Service service, HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, MessagePolicy messagePolicy, HmacKeyPolicy hmacKeyPolicy, PinPolicy pinPolicy) throws Exception {
        byte[] data = new byte[0];
        //1- header
        data = addAll(data, headerPolicy.getHeader(service));
        //2- timestamp
        data = addAll(data, timestampPolicy.get());
        //3- message cifrado ou n
        data = addAll(data, messagePolicy.get(service));
        //4- hmac
        data = hmacKeyPolicy.apply(data);
        //5- pin
        data = addAll(data, pinPolicy.get());

        return data;
    }
}
