package com.avixy.qrtoken.core.extensions.binnary;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.BitSet;
import java.util.regex.Pattern;

/**
 * Util que transforma String de '0' e '1' em array de byte.
 * <code>' '</code>, <code>'_'</code> e <code>'.'</code> são ignorados e podem ser usados como forma de marcação.
 *
 * Ex.: <code>ExBitSet.bytesFromString("0000_0001_0000_0010"); # -> [1, 2] </code>
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 10/09/2014
 */
public class ExBitSet {
    //TODO: ajeitar documentação
    private static byte[] createFromString(String s) {
        byte[] bytes = new byte[0];
        int size = (int) Math.ceil(s.length()/8);

        for (int i = 0; i < size ; i++) {
            BitSet t = new BitSet(8);

            int start, end;
            start = (s.length() -1) - i * 8;
            end = (s.length() -8) - i * 8;
            System.out.println("start = " + start + " end = " + end );

            String bite = s.substring(end, start + 1);
            System.out.println("bite = " + bite);
            System.out.println("Parsed = " + Integer.parseInt(bite, 2));
            bytes = ArrayUtils.add(bytes, (byte) Integer.parseInt(bite, 2));
        }
        return bytes;
    }

    /**
     * @param s     String em formato binário. Ex.: "01100000"
     * @return      Array de <code>byte</code> equivalente à string '0' e '1'.
     */
    public static byte[] bytesFromString(String s){
        byte[] bytes = createFromString(s);
        ArrayUtils.reverse(bytes);
        return bytes;
    }
}
