package com.avixy.qrtoken.negocio.servico;

import org.apache.commons.lang.ArrayUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created on 31/07/2014
 * @author I7
 */
public class HmacRtcService implements Service {
    private final int SERVICE_CODE = 50;
    private final String SERVICE_NAME = "Atualizar RTC - HMAC";

    private Date data;
    private TimeZone timeZone;
    private String key;

    /** TODO:
     *  HMAC
     *  bitwise args
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
        //TODO: keys
        byte[] msg = getMessage();
        //apply hmac
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac sha1Mac = Mac.getInstance("HmacSHA1");
        sha1Mac.init(secretKeySpec);
        byte[] hmac = sha1Mac.doFinal(getMessage());

        return ArrayUtils.addAll(msg, hmac);
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
        this.key = key;
    }
}
