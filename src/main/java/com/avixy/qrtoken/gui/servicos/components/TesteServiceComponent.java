package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import com.google.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 13/08/2014
 */
public class TesteServiceComponent extends NoParamServiceComponent {
    private Logger logger = LoggerFactory.getLogger(TesteServiceComponent.class);

    private static final String FXML_PATH = "/fxml/dindin.fxml";

    @Inject
    protected TesteServiceComponent() {
        super(new AbstractService(new QrtHeaderPolicy()) {
            @Override
            public String getServiceName() {
                return "teste";
            }

            @Override
            public ServiceCode getServiceCode() {
                return ServiceCode.SERVICE_EMPTY;
            }
            @Override
            public byte[] getMessage() {
                return new byte[0];
            }
        });
    }

    @Override
    public String getServiceName() {
        return "Teste";
    }

    @Override
    public Node getNode() {
        try {
            return FXMLLoader.load(getClass().getResource(FXML_PATH));
        } catch (IOException e) {
            logger.error("Missing FXML: ", e);
            return null;
        }
    }
}
