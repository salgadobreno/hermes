package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 20/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class RGB565Param implements Param {
    private NBitsParam r,g,b;

    public RGB565Param(int r, int g, int b) {
        if (r > 255 || g > 255 || b > 255) throw new IllegalArgumentException();
        this.r = new NBitsParam((byte)5, (byte)(r >> (8 - 5))); //r = [0..255] - r >> (bits_in - bits_out)
        this.g = new NBitsParam((byte)6, (byte)(g >> (8 - 6)));
        this.b = new NBitsParam((byte)5, (byte)(b >> (8 - 5)));
    }

    @Override
    public String toBinaryString() {
        return r.toBinaryString() + g.toBinaryString() + b.toBinaryString();
    }
}
