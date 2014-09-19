package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.core.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
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
    protected EraseKtamperService(@Named("Null")KeyPolicy keyPolicy) {
        super(keyPolicy);
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
    public byte[] getData() throws GeneralSecurityException {
        return getMessage();
    }

    @Override
    public byte[] getMessage() {
        return BinnaryMsg.getInstance().append(timestampParam).append(pinParam).toByteArray();
    }

    @Override
    public KeyPolicy getKeyPolicy() {
        return null;
    }

    public void setPin(String pin){
        this.pinParam = new PinParam(pin);
    }

    public void setTimestamp(Date date){
        this.timestampParam = getParam(date);
    }
}
