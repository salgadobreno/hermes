package com.avixy.qrtoken.negocio.servico.params;

import org.apache.commons.lang.StringUtils;

/**
 * Created on 10/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TimestampParam implements Param {
    private static int LENGTH = 32;
    private Integer timestamp;

    public TimestampParam(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toBinaryString() {
        return StringUtils.leftPad(Integer.toBinaryString(timestamp), LENGTH, '0');
    }
}
