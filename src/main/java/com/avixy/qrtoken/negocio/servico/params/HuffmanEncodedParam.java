package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.negocio.servico.TokenHuffmanEncoder;

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
        String length = new ByteWrapperParam((byte) text.length()).toBinaryString();
        String encoded = new TokenHuffmanEncoder().encode(text);
        return length + encoded;
    }
}