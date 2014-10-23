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
        this.text = toEncode;
    }

    @Override
    public String toBinaryString() {
        return new TokenHuffman().encode(text);
    }
}
