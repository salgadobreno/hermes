package com.avixy.qrtoken.core.extensions.binary;

import org.apache.commons.lang.StringUtils;

/**
 * Wraps an <code>int</code> in two <code>byte</code>s.
 *
 * Created on 28/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
*/
public class TwoBytesWrapper {
    public static byte[] get(Integer i) {
        byte[] bytes = new byte[2];
        String val = StringUtils.leftPad(Integer.toBinaryString(i.shortValue()), 16, '0');
        String[] strings = {
                val.substring(0, val.length()/2),
                val.substring(val.length()/2)
        };
        for (int j = 0; j < bytes.length; j++) {
            bytes[j] = ((byte) Integer.parseInt(strings[j], 2));
        }
        return bytes;
    }
}
