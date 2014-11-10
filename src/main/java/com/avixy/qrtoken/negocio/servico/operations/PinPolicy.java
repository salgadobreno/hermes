package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.params.PinParam;

/**
 * Created on 05/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class PinPolicy {
    private PinParam pinParam;

    public void setPin(String pin){
        this.pinParam = new PinParam(pin);
    }

    public byte[] get(){
        return BinnaryMsg.get(pinParam.toBinaryString());
    }
}
