package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.Service;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * O <code>ServiceComponent</code> é o componente gráfico e controller para uma implementação de um serviço do Token
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 08/08/2014
 */
 public abstract class ServiceComponent extends VBox {
    static Logger log = LoggerFactory.getLogger(ServiceComponent.class);

    public ServiceComponent(){}

    public abstract Service getService();

    public abstract Node getNode();

    public abstract String getServiceName();

    public Logger getLogger() { return log; }

    /**
     * Define a categoria de um <code>ServiceComponent</code>
     * @author Breno Salgado <breno.salgado@avixy.com>
     *
     * Created on 12/08/2014
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public static @interface Category {
        ServiceCategory category() default ServiceCategory.OUTROS;
    }
}
