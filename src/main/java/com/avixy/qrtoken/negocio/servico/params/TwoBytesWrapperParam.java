package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 17/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TwoBytesWrapperParam implements Param {
    private int value;
    public TwoBytesWrapperParam(int value) {
        this.value = value;
    }

    @Override
    public String toBinaryString() {
        /*
            & 0xFFFF preserva os 16 bits dos bytes sem transformar em número negativo caso o primeiro bit esteja ligado
            + 0x10000 liga o bit 17 para que o toBinaryString não perca os zero à esquerda, e remove o bit 17 com o substring.
         */

        return Integer.toBinaryString((value & 0xFFFF) + 0x10000).substring(1);
    }
}
