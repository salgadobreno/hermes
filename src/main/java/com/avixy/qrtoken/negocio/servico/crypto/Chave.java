package com.avixy.qrtoken.negocio.servico.crypto;

/**
* Bean de Chave
* @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 28/08/2014
*/
public class Chave {

    private String id;
    private String valor;
    private KeyType keyType;
    private int length;

    private String errors = "";

    public Chave() {}

    public Chave(String id, KeyType keyType, String valor, int length) {
        this.id = id;
        this.valor = valor;
        this.keyType = keyType;
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public KeyTypeWrap getAlgoritmo() {
        return new KeyTypeWrap(keyType, length);
    }

    public String getValor() {
        return valor;
    }

    public Integer getLength() {
        return length;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public Boolean isValid(){
        boolean valid = valor.length() == length;
        if (!valid)
            errors = "Invalid length;"; //TODO: mecânismo de erros melhor
        return valid;
    }

    @Override
    public String toString() {
        return getId() + " - " + getAlgoritmo();
    }

    public String getErrors() {
        return errors;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    /** Representa um algoritmo + tamanho de chave */
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
