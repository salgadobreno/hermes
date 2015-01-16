package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created by AnaniasPC on 1/15/2015.
 */
public class KeySetParam implements Param {
    private byte keySet;

    public KeySetParam(byte keySet) {
        this.keySet = keySet;
    }

    @Override
    public String toBinaryString() {
        /*
            & 0xFF preserva os 8 bits do byte sem transformar em número negativo caso o primeiro bit esteja ligado
            + 0x10 liga o quinto bit para que o toBinaryString não perca os zero à esquerda, e remove o quinto bit com o substring.
         */
        return Integer.toBinaryString((keySet & 0xFF) + 0x10).substring(1);
    }
}
