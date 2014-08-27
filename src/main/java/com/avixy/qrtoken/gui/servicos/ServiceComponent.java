package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.Service;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 08/08/2014
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
 public abstract class ServiceComponent extends VBox {
    static Logger log = LoggerFactory.getLogger(ServiceComponent.class);

    public ServiceComponent(){}

    public abstract Service getService();

    public abstract Node getNode();

    public abstract String getServiceName();

    public Logger getLogger() {
        return log;
    }

}
