package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.header.HeaderPolicy;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.lang.ArrayUtils;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.TimeZone;

/**
 * Serviço de RTC com Hmac
 * @author Breno Salgado
 *
 * Created on 31/07/2014
 */
public class HmacRtcService extends AbstractService {
    private HmacKeyPolicy hmacKeyPolicy = new HmacKeyPolicy();
    private static final byte SERVICE_CODE = 50;
    private static final String SERVICE_NAME = "Atualizar RTC - HMAC";

    private int date;
    private int timeZone;

    @Inject
    public HmacRtcService(@Named("Hmac") KeyPolicy keyPolicy) {
        super(keyPolicy);
    }

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
        return hmacKeyPolicy.apply(getMessage());
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return hmacKeyPolicy;
    }

    public byte[] getMessage(){
        byte[] bytes = new byte[5];
        bytes[0] = (byte)(date >> 24);
        bytes[1] = (byte)(date >> 16);
        bytes[2] = (byte)(date >> 8);
        bytes[3] = (byte)(date >> 0);
        bytes[4] = (byte) timeZone;
        return bytes;
    }

    public void setTimeZone(TimeZone timeZone) {
        int hourOffset = timeZone.getRawOffset() / (60 * 60 * 1000);
        int absOffset = Math.abs(hourOffset);
        if (absOffset > 12)
            throw new IllegalArgumentException("Timezone offset must be between -12 and 12");
        this.timeZone = absOffset;
        if (hourOffset > 0)
            this.timeZone = this.timeZone | 0x10;
    }

    public void setDate(Date date) {
        this.date = (int)(date.getTime() / 1000);
    }

    public int getDate() {
        return date;
    }

    public void setKey(String key){
        hmacKeyPolicy.setKey(key.getBytes());
    }
}
