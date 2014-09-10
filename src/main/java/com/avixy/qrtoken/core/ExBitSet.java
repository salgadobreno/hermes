package com.avixy.qrtoken.core;

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
        return createFromString(s).toByteArray();
    }
}
