package com.avixy.qrtoken.negocio.servico.operations;

/**
 * Created on 03/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface RandomGenerator {
    public void nextBytes(byte[] bytes);
}
