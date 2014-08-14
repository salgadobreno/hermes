package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.Service;
import com.avixy.qrtoken.negocio.servico.ServiceCategory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Created on 13/08/2014
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceCategory
public class TesteServiceComponent extends ServiceComponent {

    private static final String FXML_PATH = "/fxml/dindin.fxml";

    @Override
    public String getServiceName() {
        return "Teste";
    }

    @Override
    public Service getService() {
        return new Service() {
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
        };
    }

    @Override
    public Node getNode() {
        try {
            return FXMLLoader.load(getClass().getResource(FXML_PATH));
        } catch (IOException e) {
            log.error("Missing FXML: ", e);
            return null;
        }
    }
}
