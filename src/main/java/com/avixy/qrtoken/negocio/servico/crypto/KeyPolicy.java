package com.avixy.qrtoken.negocio.servico.crypto;

import java.security.GeneralSecurityException;

/**
 * Created on 22/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface KeyPolicy {
    public byte[] apply(byte[] msg) throws GeneralSecurityException;

    public static enum KeyType {
        TDES("TDES"), AES("AES"), RSASIG("RSA Assinatura"), RSACRYPT("RSA Sigilo"), ECCSIG("ECC Assinatura"), ECCCRYPT("ECC Sigilo"), HMAC("HMAC");

        private String name;
        KeyType(String name) {
            this.name = name;
        }

//        @Override
//        public String toString() {
//            return name;
//        }
    }
}
