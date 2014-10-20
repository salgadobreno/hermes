package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.gui.controllers.HomeController;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrTokenCode;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.bouncycastle.crypto.CryptoException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * O <code>ServiceComponent</code> é o componente gráfico e controller para uma implementação de um serviço do Token
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 08/08/2014
 */
 public abstract class ServiceComponent extends VBox {
    protected Service service;
    protected QrCodePolicy qrCodePolicy;
    protected HomeController controller;

    /**
     *
     * @param service
     */
    protected ServiceComponent(Service service, QrCodePolicy qrCodePolicy) {
        this.service = service;
        this.qrCodePolicy = qrCodePolicy;
    }

    public Service getService() { return service; }

    public abstract Node getNode();

    public String getServiceName() { return service.getServiceName(); }

    public void setController(HomeController controller) {
        this.controller = controller;
    }

    public HomeController getController() {
        return controller;
    }

    public List<QrTokenCode> getQrs(QrSetup setup) throws GeneralSecurityException, CryptoException {
//        QrSetup qrSetup = new QrSetup(Version.getVersionForNumber(2), ErrorCorrectionLevel.L);
        return qrCodePolicy.getQrs(getService(), setup); //TODO
    }

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
