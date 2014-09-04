package com.avixy.qrtoken.negocio.servico.chaves.crypto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define que tipo de {@link Chave} um {@link KeyPolicy} aceita
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 25/08/2014
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AcceptsKey {
    KeyType keyType();
}
