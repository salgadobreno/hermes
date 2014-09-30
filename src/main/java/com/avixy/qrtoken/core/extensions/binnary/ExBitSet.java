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

    private static BitSet createFromString(String s) {

        BitSet t = new BitSet(s.length());
        int lastBitIndex = s.length() - 1;
        int i = lastBitIndex;
        while ( i >= 0) {
            if ( s.charAt(i) == '1'){
                t.set(lastBitIndex - i);
                i--;
            }
            else
                i--;
        }
        return t;
    }

    /**
     * @param s     String em formato binário. Ex.: "01100000"
     * @return      Array de <code>byte</code> equivalente à string '0' e '1'.
     */
    public static byte[] bytesFromString(String s){
        byte[] bytes = createFromString(s).toByteArray();
        ArrayUtils.reverse(bytes);
        return bytes;
    }
}
