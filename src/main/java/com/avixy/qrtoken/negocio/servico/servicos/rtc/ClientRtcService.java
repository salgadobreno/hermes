package com.avixy.qrtoken.negocio.servico.servicos.rtc;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.servico.params.TemplateParam;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ClientRtcService extends HmacRtcService {
    private TemplateParam template;
    private List<Param> params = new ArrayList<>();

    @Inject
    public ClientRtcService(HmacKeyPolicy keyPolicy) {
        super(keyPolicy);
    }

    @Override
    public int getServiceCode() {
        return 52;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestamp).append(timezone).append(template).append(params).toByteArray();
    }

    @Override
    public String getServiceName() {
        return "Atualizar RTC - HMAC Cliente";
    }

    public void setTemplate(int template){
        this.template = new TemplateParam((byte) template);
    }

    public void setParams(Param... params) {
        this.params = Arrays.asList(params);
    }
}
