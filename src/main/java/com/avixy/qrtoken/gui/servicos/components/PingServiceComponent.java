package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.servico.servicos.PingService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
@ServiceComponent.Category(category = ServiceCategory.OUTROS)
public class PingServiceComponent extends ServiceComponent {

    @Inject
    public PingServiceComponent(PingService service) {
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
