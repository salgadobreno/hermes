package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.HermesModule;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.google.inject.Guice;
import com.google.inject.Injector;
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
    protected Injector injector = Guice.createInjector(new HermesModule());

    protected Service service;

    public Service getService(){ return service; }

    public abstract Node getNode();

    public String getServiceName() { return service.getServiceName(); }

    public Logger getLogger() { return log; }

    /**
     * Annotation que define a categoria de um <code>ServiceComponent</code>. Usado para definir em que aba o serviço será listado na aplicação.
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
