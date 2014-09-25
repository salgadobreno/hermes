package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class DesafioParam implements Param {
    private StringWrapperParam stringWrapperParam;

    public DesafioParam(String value) {
        if (value.length() > 8 || value.length() < 4){
            throw new IllegalArgumentException("Desafio size must be between 4 and 8.");
        }
        this.stringWrapperParam = new StringWrapperParam(value);
    }

    @Override
    public String toBinaryString() {
        return stringWrapperParam.toBinaryString();
    }
}
