package com.avixy.qrtoken.negocio.servico.behaviors;

/**
 * Defines a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} which requires a PUK signature
 *
 * Created on 18/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface PukAble {
    void setPuk(String puk);
}
