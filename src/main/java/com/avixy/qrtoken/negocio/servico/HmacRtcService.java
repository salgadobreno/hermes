package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.core.BinMsg;
import com.avixy.qrtoken.core.ExBitSet;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.TimeZoneParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.security.GeneralSecurityException;

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

    private TimestampParam date;
    private TimeZoneParam timeZone;

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
        return BinMsg.getInstance().append(date).append(timeZone).toByteArray();
    }

    public void setTimeZone(TimeZoneParam timeZone) {
        this.timeZone = timeZone;
    }

    public void setDate(TimestampParam date) {
        this.date = date;
    }

    public void setKey(String key){
        hmacKeyPolicy.setKey(key.getBytes());
    }
}
