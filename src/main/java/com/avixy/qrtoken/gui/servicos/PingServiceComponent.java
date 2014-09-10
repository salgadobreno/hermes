package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.PingService;
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

    public PingServiceComponent() {
        this.service = injector.getInstance(PingService.class);
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
