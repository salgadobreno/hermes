package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 20/02/2015
 *
 * @author I7
 */
public class RGB565Param implements Param {
    private final FourBitParam r,g,b;

    public RGB565Param(int r, int g, int b) {
        this.r = new FourBitParam((byte)r);
        this.g = new FourBitParam((byte)g);
        this.b = new FourBitParam((byte)b);
    }

    @Override
    public String toBinaryString() {
        return r.toBinaryString() + g.toBinaryString() + b.toBinaryString();
    }
}
