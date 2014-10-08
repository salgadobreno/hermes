package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.servico.servicos.ktamper.GenerateKtamperService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.fxml.MigPane;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.CHAVES)
public class GenerateKtamperServiceComponent extends ServiceComponent {
    @Inject
    public GenerateKtamperServiceComponent(GenerateKtamperService service) {
        super(service);
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label label = new Label("Esse serviço não possui parâmetros.");
        label.setFont(new Font(18));
        migPane.add(label, "align center");

        return migPane;
    }
}
