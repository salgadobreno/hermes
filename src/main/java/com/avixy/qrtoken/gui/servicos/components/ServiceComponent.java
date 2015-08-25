package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.gui.controllers.MainController;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrTokenCode;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Graphic component for a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} implementation
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 08/08/2014
 */
public abstract class ServiceComponent extends VBox {
    protected Service service;
    protected MainController controller;

    /**
     *
     * @param service
     */
    protected ServiceComponent(Service service) {
        this.service = service;
    }

    public abstract Service getService() throws Exception;

    public abstract Node getNode();

    public String getServiceName() { return service.getServiceName(); }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public List<QrTokenCode> getQrs(QrSetup qrSetup) throws Exception {
        Service service = getService();
        if (service != null) {
            return getService().getQrs(qrSetup); //TODO
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Annotation que define a categoria de um <code>ServiceComponent</code>. Usado para definir em que aba o serviço será listado na aplicação.
     * @author Breno Salgado <breno.salgado@avixy.com>
     *
     * Created on 12/08/2014
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Category {
        ServiceCategory category() default ServiceCategory.OUTROS;
    }
}
