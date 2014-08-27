package com.avixy.qrtoken.negocio.servico;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 12/08/2014
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ServiceCategory {
    com.avixy.qrtoken.gui.servicos.ServiceCategory category() default com.avixy.qrtoken.gui.servicos.ServiceCategory.OUTROS;
}
