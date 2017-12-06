package com.avixy.qrtoken.negocio.servico.params;


import org.apache.commons.lang.StringUtils;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 11/09/2014
 */
public class PinParam implements Param {
    protected String pin;

    public PinParam(String pin) {
        if (pin.length() < 4 || pin.length() > 16) {
            throw new IllegalArgumentException("O PIN/PUK deve conter no minimo 4 caracteres");
        }

        this.pin = pin;
    }

    @Override
    public String toBinaryString() {
        String binaryString = new StringWrapperParam(pin).toBinaryString();
        return StringUtils.rightPad(binaryString, 16*8, '0');
    }
}
