package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created by Breno on 15/05/2015.
 */
public class SerialNumberParam extends StringWithLengthParam {
    public SerialNumberParam(String string) {
        super(string);
        if (string.length() < 10) {
            throw new IllegalArgumentException("SerialNumber should be 10 characters long.");
        }
    }
}
