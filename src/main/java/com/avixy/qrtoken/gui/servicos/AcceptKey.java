package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.crypto.KeyPolicy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 25/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AcceptKey {
    KeyPolicy.KeyType keyTypes();
}
