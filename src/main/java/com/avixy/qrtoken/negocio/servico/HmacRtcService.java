package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.crypto.KeyPolicy;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created on 31/07/2014
 * @author Breno Salgado
 */
public class HmacRtcService implements Service {
    private HmacKeyPolicy hmacKeyPolicy = new HmacKeyPolicy();
    private final int SERVICE_CODE = 50;
    private final String SERVICE_NAME = "Atualizar RTC - HMAC";

    private Date data;
    private TimeZone timeZone;

    /** TODO:
     *  bitwise args
     * FIXME: setData(tempo) ambiguo com getData(dados) -_-
     */

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public int getServiceCode() {
        return SERVICE_CODE;
    }

    @Override
    public byte[] getData() throws GeneralSecurityException {
        //apply hmac
        return hmacKeyPolicy.apply(getMessage());
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return hmacKeyPolicy;
    }

    public byte[] getMessage(){
        StringBuilder msg = new StringBuilder().append(data.getTime()).append(timeZone.getRawOffset());
        return msg.toString().getBytes();
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public void setData(Date data) {
        //FIXME: ambiguo com getData -_-
        this.data = data;
    }

    public void setKey(String key){
        hmacKeyPolicy.setKey(key.getBytes());
    }
}
