package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.params.PinParam;

/**
 * Created on 05/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class PasswordPolicy {
    private PinParam pinParam;      /* Válido p/ PIN e PUK */

    public void setPassword(String password){
        this.pinParam = new PinParam(password);
    }

    public byte[] get(){
        return BinaryMsg.get(pinParam.toBinaryString());
    }
}
