package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.ParamFactory;
import com.avixy.qrtoken.negocio.servico.params.TimeZoneParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.bouncycastle.crypto.CryptoException;

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
    private static final byte SERVICE_CODE = 50;
    private static final String SERVICE_NAME = "Atualizar RTC - HMAC";

    protected TimestampParam timestamp;
    protected TimeZoneParam timezone;

    @Inject
    public HmacRtcService(HmacKeyPolicy keyPolicy) {
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
    public byte[] getData() throws GeneralSecurityException, CryptoException {
        return keyPolicy.apply(getMessage());
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return keyPolicy;
    }

    public byte[] getMessage(){
        return BinnaryMsg.create().append(timestamp).append(timezone).toByteArray();
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = new TimeZoneParam(timezone);
    }

    public void setTimestamp(Date date) {
        this.timestamp = ParamFactory.getParam(date);
    }

    public void setKey(String key){
        keyPolicy.setKey(key.getBytes());
    }
}
