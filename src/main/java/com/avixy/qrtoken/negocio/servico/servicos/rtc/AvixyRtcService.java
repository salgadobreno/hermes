package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AvixyRtcService extends AbstractHmacRtcService {

    @Inject
    public AvixyRtcService(QrtHeaderPolicy headerPolicy, HmacKeyPolicy hmacKeyPolicy, TimestampPolicy timestampPolicy) {
        super(headerPolicy, hmacKeyPolicy, timestampPolicy);
    }

    @Override
    public int getServiceCode() {
        return 50;
    }

    @Override
    public String getServiceName() {
        return "Atualizar RTC - HMAC Avixy";
    }
}
