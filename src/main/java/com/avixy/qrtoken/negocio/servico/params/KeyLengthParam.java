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
        K64(64), K128(128), K160(160), K192(192), K224(224), K256(256), K320(320), K384(384), K512(512), K1024(1024), K2048(2048), K4096(4096);

        private static final Map<Integer, KeyLength> reverseLookup = new HashMap<>();
        private int length;

        /* popula o map p/ reverse lookup */
        static {
            for (KeyLength k : EnumSet.allOf(KeyLength.class)){
                reverseLookup.put(k.getLength(), k);
            }
        }

        KeyLength(int i) {
            this.length = i;
        }

        public int getLength(){ return length; }

        public static KeyLength lookup(int i){ return reverseLookup.get(i); }
    }

    private KeyLength keyLength;

    public KeyLengthParam(int i) {
        this.keyLength = KeyLength.lookup(i);
        if (this.keyLength == null) {
            throw new IllegalArgumentException("Invalid key length.");
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
