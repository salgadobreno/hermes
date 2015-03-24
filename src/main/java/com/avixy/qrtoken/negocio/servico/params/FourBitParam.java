package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 20/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class FourBitParam implements Param {
    protected byte b;

    public FourBitParam(byte b) {
        this.b = b;
    }

    @Override
    public String toBinaryString() {
        /*
            & 0xFF preserva os 8 bits do byte sem transformar em número negativo caso o primeiro bit esteja ligado
            + 0x10 liga o quinto bit para que o toBinaryString não perca os zero à esquerda, e remove o quinto bit com o substring.
         */
        return Integer.toBinaryString((b & 0xFF) + 0x10).substring(1);
    }
}
