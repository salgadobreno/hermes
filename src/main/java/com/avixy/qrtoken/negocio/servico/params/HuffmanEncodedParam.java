package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.negocio.servico.TokenHuffman;

/**
 * Created on 23/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HuffmanEncodedParam implements Param {
    String text;
    public HuffmanEncodedParam(String toEncode) {
        if (toEncode.length() > 255) { throw new IllegalArgumentException(); }
        this.text = toEncode;
    }

    @Override
    public String toBinaryString() {
        String length;
        if (text.equals(TokenHuffman.ARG)) {
            length = "11111111";
        } else {
            length = new ByteWrapperParam((byte) text.length()).toBinaryString();
        }
        String encoded = new TokenHuffman().encode(text);
        return length + encoded;
    }
}