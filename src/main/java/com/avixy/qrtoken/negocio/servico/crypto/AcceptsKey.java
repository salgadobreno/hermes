package com.avixy.qrtoken.negocio.servico.crypto;

import com.avixy.qrtoken.negocio.servico.crypto.Chave;
import com.avixy.qrtoken.negocio.servico.crypto.KeyPolicy;
import com.avixy.qrtoken.negocio.servico.crypto.KeyType;

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
