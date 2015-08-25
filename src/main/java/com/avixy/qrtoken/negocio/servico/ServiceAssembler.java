package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.behaviors.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.behaviors.RangedTimestampAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.MessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;

import static org.apache.commons.lang.ArrayUtils.addAll;

/**
 * Created on 06/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ServiceAssembler {

    /**
     * Assembles a QR Code
     * @param service
     * @param headerPolicy
     * @param timestampPolicy
     * @param messagePolicy
     * @param hmacKeyPolicy
     * @param passwordPolicy
     * @return                  Token formatted QR Code data
     * @throws Exception
     */
    public byte[] get(Service service, HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, MessagePolicy messagePolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy) throws Exception {
        byte[] message_content = messagePolicy.get(service);
        return get(service, message_content, headerPolicy, timestampPolicy, hmacKeyPolicy, passwordPolicy);
    }

    /**
     * Overloaded get() allows passing the message directly
     * @param service
     * @param message           <code>byte</code> array of the message
     * @param headerPolicy
     * @param timestampPolicy
     * @param hmacKeyPolicy
     * @param passwordPolicy
     * @return                  Token formatted QR Code data
     * @throws Exception
     */
    public byte[] get(Service service, byte[] message, HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, HmacKeyPolicy hmacKeyPolicy, PasswordPolicy passwordPolicy) throws Exception {
        byte[] data = new byte[0];

        byte[] header, timestamp, message_content, pin;
        message_content = message;

        if (service instanceof PasswordOptional || service instanceof RangedTimestampAble) {
            header = headerPolicy.getHeader(service, ServiceCodeLookup.getServiceCode(
                    service,
                    service instanceof PasswordOptional && ((PasswordOptional) service).hasPin(),
                    service instanceof RangedTimestampAble && ((RangedTimestampAble) service).isRanged()));
        } else {
            header = headerPolicy.getHeader(service, null);
        }
        timestamp = timestampPolicy.get();
        pin = passwordPolicy.get();

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
        //5- password
        data = addAll(data, pin);

        return data;
    }
}
