package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AvixyRtcService extends HmacRtcService {
    @Inject
    public AvixyRtcService(HmacKeyPolicy keyPolicy) {
        super(keyPolicy);
    }

    @Override
    public int getServiceCode() {
        return 50;
    }
}
