package com.avixy.qrtoken.negocio.servico.params;

import org.apache.commons.lang.StringUtils;

import java.util.TimeZone;

/**
 * Created on 10/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class FusoHorarioParam implements Param {
    private static final int LENGTH = 8;
    private byte timeZone;

    public FusoHorarioParam(TimeZone timeZone) {
        int gmtOffset = timeZone.getRawOffset() / (60 * 60 * 1000);
        int absOffset = Math.abs(gmtOffset);
        if (absOffset > 12)
            throw new IllegalArgumentException("Timezone offset must be between -12 and 12");
        if (gmtOffset > 0) {
            this.timeZone = (byte) (absOffset | 0x10);
        } else {
            this.timeZone = (byte) absOffset;
        }
    }

    @Override
    public String toBinaryString() {
        return StringUtils.leftPad(Integer.toBinaryString(timeZone), LENGTH, '0') ;
    }
}
