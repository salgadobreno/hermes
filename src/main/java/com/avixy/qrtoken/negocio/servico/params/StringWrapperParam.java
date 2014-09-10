package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 10/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class StringWrapperParam implements Param {
    private String string;

    public StringWrapperParam(String string) {
        this.string = string;
    }

    @Override
    public String toBinaryString() {
        return string;
    }
}
