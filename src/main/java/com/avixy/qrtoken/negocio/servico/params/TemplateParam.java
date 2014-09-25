package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplateParam implements Param {
    private byte template;
    public TemplateParam(byte template) {
        if (template > 31 || template < 0){
            throw new IllegalArgumentException("Template value must be between 0 and 31.");
        }
        this.template = template;
    }

    @Override
    public String toBinaryString() {
        /*
            & 0xFF preserva os 8 bits do byte sem transformar em número negativo caso o primeiro bit esteja ligado
            + 0x20 liga o sexto bit para que o toBinaryString não perca os zero à esquerda, e remove o sexto bit com o substring.
         */
        return Integer.toBinaryString((template & 0xFF) + 0x20).substring(1);
    }
}
