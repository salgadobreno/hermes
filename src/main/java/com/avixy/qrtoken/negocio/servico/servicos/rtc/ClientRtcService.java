package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
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
public class ClientRtcService extends HmacRtcService {
    private List<Param> params = new ArrayList<>();

    @Inject
    public ClientRtcService(QrtHeaderPolicy headerPolicy, HmacKeyPolicy keyPolicy) {
        super(headerPolicy, keyPolicy);
    }

    @Override
    public int getServiceCode() {
        return 52;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp).append(timezone).append(params).toByteArray();
    }

    @Override
    public String getServiceName() {
        return "Atualizar RTC - HMAC Cliente";
    }
}
