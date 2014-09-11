package com.avixy.qrtoken.core;

import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang.ArrayUtils;

import java.util.BitSet;

/**
 * Created on 10/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ExBitSet {
    public static BitSet createFromString(String s) {
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

    public static byte[] bytesFromString(String s){
        byte[] bytes = createFromString(s).toByteArray();
        ArrayUtils.reverse(bytes);
        return bytes;
    }
}
