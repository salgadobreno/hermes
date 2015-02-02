package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 27/01/2015
 *
 * @author I7
 */
public class BinaryWrapperParam implements Param {
    byte[] binary;
    public BinaryWrapperParam(byte[] binary) {
        this.binary = binary;
    }


    @Override
    public String toBinaryString() {
        String r = "";
        for (byte b : binary) {
            r += Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
        }
        return r;
    }
}
