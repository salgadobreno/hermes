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
        this.length = new ByteWrapperParam((byte) string.length()); //TODO: verificar o tamanho da string?
        this.string = new StringWrapperParam(string);
    }

    @Override
    public String toBinaryString() {
        return length.toBinaryString() + string.toBinaryString();
    }
}
