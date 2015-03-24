package com.avixy.qrtoken.negocio.servico.operations;

import java.util.Random;

/**
 * Created on 03/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class RandomProxy implements RandomGenerator {

    @Override
    public void nextBytes(byte[] bytes) {
        new Random().nextBytes(bytes);
    }
}
