package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import static com.avixy.qrtoken.negocio.servico.params.ParamFactory.*;

import java.security.GeneralSecurityException;
import java.util.Date;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class EraseKtamperService extends AbstractService {
    private PinParam pinParam;
    private TimestampParam timestampParam;

    @Inject
    public EraseKtamperService(HeaderPolicy headerPolicy) {
        super(headerPolicy);
    }

    @Override
    public String getServiceName() {
        return "Apagar K_Tamper";
    }

    @Override
    public int getServiceCode() {
        return 21;
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.create().append(timestampParam).append(pinParam).toByteArray();
    }

    public void setPin(String pin){
        this.pinParam = new PinParam(pin);
    }

    public void setTimestamp(Date date){
        this.timestampParam = getParam(date);
    }

    public void setPuk(String puk) {
        setPin(puk);
    }
}
