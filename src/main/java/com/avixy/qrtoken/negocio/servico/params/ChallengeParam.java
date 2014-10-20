package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 17/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ChallengeParam implements Param {
    protected char[] chars;

    public ChallengeParam(String challenge) {
        if (challenge.length() > 6) {
            throw new IllegalArgumentException("Challenge is 6 chars");
        }

        this.chars = challenge.toCharArray();
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
