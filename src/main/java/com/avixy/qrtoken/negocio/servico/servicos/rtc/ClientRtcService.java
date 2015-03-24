package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ClientRtcService extends AbstractHmacRtcService {
    private List<Param> params = new ArrayList<>();

    @Inject
    public ClientRtcService(QrtHeaderPolicy headerPolicy, HmacKeyPolicy hmacKeyPolicy, TimestampPolicy timestampPolicy) {
        super(headerPolicy, hmacKeyPolicy, timestampPolicy);
    }


    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_CLIENT_RTC_SYM_UPDATE;
    }

    @Override
    public byte[] getMessage() {
        return BinaryMsg.create().append(timezone).append(params).toByteArray();
    }

    @Override
    public String getServiceName() {
        return "Atualizar RTC - HMAC Cliente";
    }
}
