package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 10/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ByteWrapperParam implements Param {
    private Byte value;

    public ByteWrapperParam(byte value) {
        this.value = value;
    }

    @Override
    public String toBinaryString() {
        return Integer.toBinaryString((value & 0xFF) + 0x100).substring(1);
    }
}
