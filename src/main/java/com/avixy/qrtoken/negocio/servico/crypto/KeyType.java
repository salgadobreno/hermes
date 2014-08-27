package com.avixy.qrtoken.negocio.servico.crypto;

/**
* Created on 26/08/2014
*
* @author Breno Salgado <breno.salgado@avixy.com>
*/
public enum KeyType {
    TDES("TDES"), AES("AES"), RSASIG("RSA Assinatura"), RSACRYPT("RSA Sigilo"), ECCSIG("ECC Assinatura"), ECCCRYPT("ECC Sigilo"), HMAC("HMAC");

    private String name;

    KeyType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
