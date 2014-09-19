package com.avixy.qrtoken.negocio.servico.params;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 11/09/2014
 */
public class PinParam implements Param {
    private char[] chars;

    public PinParam(String pin) {
        if (pin.length() > 4)
            throw new IllegalArgumentException("Pin is 4 chars"); //TODO: msg

        this.chars = new char[5];
        char[] charArray = pin.toCharArray();
        System.arraycopy(charArray, 0, chars, 0, charArray.length);
        chars[4] = '$';
    }

    @Override
    public String toBinaryString() {
        String binaryString = "";
        for (char c : chars) {
            binaryString += new ByteWrapperParam(c).toBinaryString();
        }
        return binaryString;
    }
}
