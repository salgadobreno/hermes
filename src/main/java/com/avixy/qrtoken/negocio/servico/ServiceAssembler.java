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

    public ServiceAssembler() {
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
    public byte[] get(Service service, HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, MessagePolicy messagePolicy, HmacKeyPolicy hmacKeyPolicy, PinPolicy pinPolicy) throws Exception {
        byte[] data = new byte[0];

        byte[] header, timestamp, message_content, pin;

        header = headerPolicy.getHeader(service);
        timestamp = timestampPolicy.get();
        message_content = messagePolicy.get(service);
        pin = pinPolicy.get();

        // NOTE: O Token não lê QR Nível 1, então estamos verificando isso aqui e colocando um padding
        // junto da mensagem pois o PIN fica sempre no fim do conteúdo
        int total_lengh = header.length + timestamp.length + message_content.length + pin.length;
        if (total_lengh < 18) {
            byte[] padding = new byte[18 - total_lengh];
            for (int i = 0; i < padding.length; i++) {
                padding[i] = 'x'; // NOTE: padding com 'x' pq vazio deu um padrão que confundia o leitor
            }
            message_content = addAll(message_content, padding);
        }

        //1- header
        data = addAll(data, header);
        //2- timestamp
        data = addAll(data, timestamp);
        //3- message cifrado ou n
        data = addAll(data, message_content);
        //4- hmac
        data = hmacKeyPolicy.apply(data);
        //5- pin
        data = addAll(data, pin);

        return data;
    }
}
