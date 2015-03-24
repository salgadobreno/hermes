package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 20/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class NBitsParam implements Param {
    private byte size;
    private byte value;
    public NBitsParam(byte size, byte value) {
        if (value < 0) throw new IllegalArgumentException("Value overflows 127");
        this.size = size;
        this.value = value;
    }

    @Override
    public String toBinaryString() {
        return Integer.toBinaryString((value & 0xFF) + (1 << size)).substring(1);
    }
}
