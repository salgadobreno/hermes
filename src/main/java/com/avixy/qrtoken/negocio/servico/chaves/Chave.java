package com.avixy.qrtoken.negocio.servico.chaves;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* Generic Key POJO
* @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 28/08/2014
*/
public class Chave {
//    Logger logger = LoggerFactory.getLogger(Chave.class);

    private String id;
    private String valor;
    private KeyType keyType;
    private int length;

    private String errors = "";

    public Chave() {}

    public Chave(String id, KeyType keyType, String valor, int length) {
        this.id = id;
        setValor(valor);
        this.keyType = keyType;
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return new KeyTypeWrap(keyType, length).toString();
    }

    public byte[] getHexValue() {
        try {
            return Hex.decodeHex(this.valor.toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException("Toda chave deve ser em formato hexa.");
        }
    }

    public String getValor(){
        return this.valor;
    }

    public Integer getLength() {
        return length;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public Boolean isValid(){
        byte[] bytes;
        try {
//            logger.debug("valor: {}", valor);
            bytes = Hex.decodeHex(valor.toCharArray());
//            logger.debug("bytes: {}", bytes);
        } catch (DecoderException e) {
//            logger.error("isValid Error: {}", e);
            return false;
        }
        boolean valid = bytes.length == (length / 8);
        if (!valid)
            errors = "Invalid length;"; //TODO: mecânismo de erros melhor
        return valid;
    }

    @Override
    public String toString() {
        return getId() + " - " + getDisplayName();
    }

    public String getErrors() {
        return errors;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValor(String valor) {
//        logger.debug("setValor {}", valor);
        this.valor = valor;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    /** KeyType + Key Size */
    public static class KeyTypeWrap {
        private KeyType keyType;
        private int length;

        private KeyTypeWrap(KeyType keyType, int length) {
            this.keyType = keyType;
            this.length = length;
        }

        @Override
        public String toString() {
            return keyType.toString() + " " + length;
        }
    }
}
