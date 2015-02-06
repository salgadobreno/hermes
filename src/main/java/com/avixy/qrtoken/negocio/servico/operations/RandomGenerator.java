package com.avixy.qrtoken.negocio.servico.operations;

/**
 * Created on 03/02/2015
 *
 * @author I7
 */
public interface RandomGenerator {
    public void nextBytes(byte[] bytes);
}
