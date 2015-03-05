package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 20/02/2015
 *
 * @author I7
 */
public class NBitsParam implements Param {
    private byte size;
    private byte value;
    public NBitsParam(byte size, byte value) {
        this.size = size;
        this.value = value;
    }

    @Override
    public String toBinaryString() {
        return Integer.toBinaryString((value & 0xFF) + (1 << size)).substring(1);
    }
}
