package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 17/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ChallengeParam extends StringWithLengthParam {

    public ChallengeParam(String challenge) {
        super(challenge);
        if (challenge.length() > 6) {
            throw new IllegalArgumentException("Challenge is 6 chars maximum");
        }
    }
}
