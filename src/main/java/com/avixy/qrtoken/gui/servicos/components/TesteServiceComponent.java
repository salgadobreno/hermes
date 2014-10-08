package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.servico.servicos.PingService;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyPolicy;
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
@ServiceComponent.Category
public class TesteServiceComponent extends ServiceComponent {
    private Logger logger = LoggerFactory.getLogger(TesteServiceComponent.class);

    private static final String FXML_PATH = "/fxml/dindin.fxml";

    @Inject
    protected TesteServiceComponent(PingService ignored) {
        super(new Service() {
            @Override
            public String getServiceName() {
                return "teste";
            }

            @Override
            public int getServiceCode() {
                return 0;
            }

            @Override
            public byte[] getData() {
                return new byte[0];
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