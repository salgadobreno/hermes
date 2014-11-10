package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.HmacAble;
import com.avixy.qrtoken.negocio.servico.servicos.TimestampAble;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.TimeZoneParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
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
public abstract class AbstractHmacRtcService extends AbstractService implements TimestampAble, HmacAble {

    protected TimeZoneParam timezone;

    @Inject
    public AbstractHmacRtcService(QrtHeaderPolicy headerPolicy, HmacKeyPolicy hmacKeyPolicy, TimestampPolicy timestampPolicy) {
        super(headerPolicy);
        this.hmacKeyPolicy = hmacKeyPolicy;
        this.timestampPolicy = timestampPolicy;
    }

    @Override
    public byte[] getMessage(){
        return BinnaryMsg.create().append(timezone).toByteArray();
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = new TimeZoneParam(timezone);
    }

    @Override
    public void setTimestamp(Date date) {
        timestampPolicy.setDate(date);
    }

    @Override
    public void setHmacKey(byte[] key){
        hmacKeyPolicy.setKey(key);
    }
}
