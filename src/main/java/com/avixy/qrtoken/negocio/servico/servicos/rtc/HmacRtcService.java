package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.TimeZoneParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractHmacService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.Date;
import java.util.TimeZone;

/**
 * Serviço de RTC com Hmac
 * @author Breno Salgado
 *
 * Created on 31/07/2014
 */
public class HmacRtcService extends AbstractHmacService {
    private static final byte SERVICE_CODE = 50;
    private static final String SERVICE_NAME = "Atualizar RTC - HMAC";

    protected TimestampParam timestamp;
    protected TimeZoneParam timezone;

    @Inject
    public HmacRtcService(QrtHeaderPolicy headerPolicy, HmacKeyPolicy keyPolicy) {
        super(headerPolicy, keyPolicy);
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public int getServiceCode() {
        return SERVICE_CODE;
    }

    public byte[] getMessage(){
        return BinnaryMsg.create().append(timestamp).append(timezone).toByteArray();
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = new TimeZoneParam(timezone);
    }

    public void setTimestamp(Date date) {
        this.timestamp = new TimestampParam(date);
    }

    public void setHmacKey(byte[] key){
        hmacKeyPolicy.setKey(key);
    }
}
