package com.avixy.qrtoken.negocio.servico.params;

import org.apache.commons.lang.StringUtils;

/**
 * Created on 10/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ByteWrapperParam implements Param {
    private static final int LENGTH = 8;
    private Byte value;

    public ByteWrapperParam(byte value) { this.value = value; }

    public ByteWrapperParam(char value) { this.value = (byte) value; }

    @Override
    public String toBinaryString() {
        return StringUtils.leftPad(Integer.toBinaryString((value & 0xFF) + 0x100).substring(1), LENGTH, '0');
    }
}
