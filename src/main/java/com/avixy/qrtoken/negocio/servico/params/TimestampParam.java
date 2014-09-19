package com.avixy.qrtoken.negocio.servico.params;

import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 10/09/2014
 */
public class TimestampParam implements Param {
    private static int LENGTH = 32;
    private Integer timestamp;

    public TimestampParam(int timestamp) {
        this.timestamp = timestamp;
    }

    public TimestampParam(Date timestamp) {
        this.timestamp = (int) (timestamp.getTime() / 1000);
    }

    @Override
    public String toBinaryString() {
        /*
            & 0xFFFFFFFF preserva os 32 bits do timestamp sem transformar em número negativo caso o primeiro bit esteja ligado
            + 0x100000000 liga o nono bit para que o toBinaryString não perca os zero à esquerda, e remove o nono bit com o substring.
         */
        return Long.toBinaryString((timestamp & 0xFFFFFFFFL) + 0x100000000L).substring(1);
    }
}
