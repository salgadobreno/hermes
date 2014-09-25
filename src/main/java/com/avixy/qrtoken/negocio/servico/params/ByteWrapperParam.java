package com.avixy.qrtoken.negocio.servico.params;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 10/09/2014
 */
public class ByteWrapperParam implements Param {
    private Byte value;

    public ByteWrapperParam(byte value) { this.value = value; }

    public ByteWrapperParam(char value) { this.value = (byte) value; }

    @Override
    public String toBinaryString() {
        /*
            & 0xFF preserva os 8 bits do byte sem transformar em número negativo caso o primeiro bit esteja ligado
            + 0x100 liga o nono bit para que o toBinaryString não perca os zero à esquerda, e remove o nono bit com o substring.
         */

        return Integer.toBinaryString((value & 0xFF) + 0x100).substring(1);
    }
}
