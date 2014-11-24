package com.avixy.qrtoken.negocio.servico.params;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 11/09/2014
 */
public class PinParam implements Param {
    protected String pin;
    protected int length;

    public PinParam(String pin) {
        if (pin.length() < 4 || pin.length() > 12) {
            throw new IllegalArgumentException("Pin should be at least 4 chars");
        }

        this.pin = pin;
        this.length = pin.length();
    }

    @Override
    public String toBinaryString() {
        String binaryString = "";
        binaryString += new StringWrapperParam(pin).toBinaryString();
        binaryString += new ByteWrapperParam((byte) length).toBinaryString();
        return binaryString;
    }
}
