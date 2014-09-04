package com.avixy.qrtoken.negocio.servico.chaves.crypto;

/**
 * Operações criptográficas usadas pelo Avixy Qr Token
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 26/08/2014
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
