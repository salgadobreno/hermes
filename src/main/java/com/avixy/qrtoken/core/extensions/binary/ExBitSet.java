package com.avixy.qrtoken.core.extensions.binary;

import org.apache.commons.lang.ArrayUtils;

import java.util.BitSet;

/**
 * Utility class that turns a '0' and '1' <code>String</code> into a <code>byte</code> array
 * <code>' '</code>, <code>'_'</code> and <code>'.'</code> are ignored
 *
 * Ex.: <code>ExBitSet.bytesFromString("0000_0001_0000_0010"); # -> [1, 2] </code>
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 10/09/2014
 */
public class ExBitSet {
    private static byte[] createFromString(String s) {
        byte[] bytes = new byte[0];
        int size = (int) Math.ceil(s.length()/8);

        for (int i = 0; i < size ; i++) {
            BitSet t = new BitSet(8);

            int start, end;
            start = (s.length() -1) - i * 8;
            end = (s.length() -8) - i * 8;
            String word = s.substring(end, start + 1);
            bytes = ArrayUtils.add(bytes, (byte) Integer.parseInt(word, 2));
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
