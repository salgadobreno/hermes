package com.avixy.qrtoken.negocio.servico.operations;

/**
 * A kind of wrapper on {@link java.util.Random}
 *
 * Created on 03/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface RandomGenerator {
    public void nextBytes(byte[] bytes);
}
