package com.avixy.qrtoken.negocio.servico.chaves.crypto;

/**
 * Cryptographic operations used in the Token
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 26/08/2014
*/
public enum KeyType {
    TDES("TDES", new Integer[]{64, 128, 192}),
    AES("AES", new Integer[]{128, 192, 256}),
    HMAC("HMAC");
    //RSASIG("RSA Assinatura"), RSACRYPT("RSA Sigilo"), ECCSIG("ECC Assinatura"), ECCCRYPT("ECC Sigilo"), futura versao

    private String name;
    private Integer[] keyLengths;
    private static final Integer[] DEFAULT_KEYS = {64, 128, 160, 192, 224, 256, 320, 384, 512, 1024, 2048, 4096};


    KeyType(String name) {
        this.name = name;
    }

    KeyType(String name, Integer[] keyLengths) {
        this.name = name;
        this.keyLengths = keyLengths;
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer[] getKeyLengths(){
        return keyLengths == null ? DEFAULT_KEYS : keyLengths;
    }

}
