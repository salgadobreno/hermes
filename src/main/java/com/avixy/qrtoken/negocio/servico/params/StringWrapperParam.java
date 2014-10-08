package com.avixy.qrtoken.negocio.servico.params;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 10/09/2014
 */
public class StringWrapperParam implements Param {
    private String string;

    public StringWrapperParam(String string) {
        this.string = string;
    }

    @Override
    public String toBinaryString() {
        char[] chars = string.toCharArray();
        String bin = "";
        for (char c : chars) {
            bin += new ByteWrapperParam(c).toBinaryString();
        }
        return bin;
    }
}
