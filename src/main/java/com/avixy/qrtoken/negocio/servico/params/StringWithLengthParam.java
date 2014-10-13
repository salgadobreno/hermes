package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 01/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StringWithLengthParam implements Param {
    private ByteWrapperParam length;
    private StringWrapperParam string;

    public StringWithLengthParam(String string) {
        if (string.length() > 255) {
            throw new IllegalArgumentException("String muito grande!");
        }
        this.length = new ByteWrapperParam((byte) string.length());
        this.string = new StringWrapperParam(string);
    }

    @Override
    public String toBinaryString() {
        return length.toBinaryString() + string.toBinaryString();
    }
}
