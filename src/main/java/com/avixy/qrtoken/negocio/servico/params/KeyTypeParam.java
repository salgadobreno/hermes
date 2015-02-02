package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class KeyTypeParam implements Param {
//    public enum KeyType {
//        /*
//        4 bits para seleção de chave, os valores são fixos e ordenados. Esse enum seta eles na ordem.
//         */
//        RNG, TDES, SYMMETRIC_ENCRYPTION, RSA_ENCRYPTION, RSA_SIGNATURE, ECC_ENCRYPTION, ECC_SIGNATURE, SYMMETRIC_AUTHENTICATION;
//    }
    private KeyType keyType;

    public KeyTypeParam(KeyType keyType) {
        this.keyType = keyType;
    }

    @Override
    public String toBinaryString() {
        /*
            & 0xFF preserva os 8 bits do byte sem transformar em número negativo caso o primeiro bit esteja ligado
            + 0x10 liga o quinto bit para que o toBinaryString não perca os zero à esquerda, e remove o quinto bit com o substring.
         */
        return Integer.toBinaryString((keyType.ordinal() & 0xFF) + 0x10).substring(1);
    }
}
