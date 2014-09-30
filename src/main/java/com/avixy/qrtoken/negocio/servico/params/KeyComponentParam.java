package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class KeyComponentParam implements Param {
    private StringWrapperParam keyParam;
    private KeyTypeParam keyTypeParam;
    private KeyLengthParam keyLengthParam;
    private CrcParam crcParam;

    public KeyComponentParam(KeyTypeParam.KeyType keyType, int keyLength, String key) {
        this.keyParam = new StringWrapperParam(key);
        this.keyLengthParam = new KeyLengthParam(keyLength);
        this.keyTypeParam = new KeyTypeParam(keyType);
        this.crcParam = new CrcParam(key.getBytes());
    }

    @Override
    public String toBinaryString() {
        return keyTypeParam.toBinaryString() + keyLengthParam.toBinaryString() + keyParam.toBinaryString() + crcParam.toBinaryString();
    }
}
