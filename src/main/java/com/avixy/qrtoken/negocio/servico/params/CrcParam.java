package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.core.extensions.binary.CRC16CCITT;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class CrcParam implements Param {
    private int crc;

    StringWrapperParam stringWrapperParam;
    public CrcParam(byte[] bytes) {
        crc = CRC16CCITT.calc(bytes);
    }

    @Override
    public String toBinaryString() {
        // 0x10000 <- décimo bit ligado
        return Integer.toBinaryString((crc & 0xFFFF) + 0x10000).substring(1);
    }
}
