package com.avixy.qrtoken.negocio.servico.params;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class KeyLengthParam implements Param {
    /*
     No token teremos 5 bits para seleção de chave, os valores são fixos e ordenados(1, 2, 3..), esse enum seta eles na ordem e têm um Map<Integer, KeyLength> pra fazer reverse lookup
     */
    private enum KeyLength {
        //in bits
        K64(8), K128(16), K160(20), K192(24), K224(28), K256(32), K320(40), K384(48), K512(64), K1024(128), K2048(256), K4096(512);

        private int length;
        KeyLength(int i) {
            this.length = i;
        }

        public int getLength(){ return length; }

        /* reverse lookup */
        private static final Map<Integer, KeyLength> reverseLookup = new HashMap<>();

        static {
            for (KeyLength k : EnumSet.allOf(KeyLength.class)){
                reverseLookup.put(k.getLength(), k);
            }
        }

        public static KeyLength lookup(int i){
            return reverseLookup.get(i);
        }
        /* /reverse lookup */
    }

    private KeyLength keyLength;

    public KeyLengthParam(KeyLength keyLength) {
        this.keyLength = keyLength;
    }

    public KeyLengthParam(int length) {
        keyLength = KeyLength.lookup(length);
        if (keyLength == null) {
            throw new IllegalArgumentException("Invalid Key Length");
        }
    }

    @Override
    public String toBinaryString() {
        /*
            & 0xFF preserva os 8 bits do byte sem transformar em número negativo caso o primeiro bit esteja ligado
            + 0x20 liga o sexto bit para que o toBinaryString não perca os zero à esquerda, e remove o sexto bit com o substring.
         */
        return Integer.toBinaryString((keyLength.ordinal() & 0xFF) + 0x20).substring(1);
    }
}
