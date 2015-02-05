package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.servico.servicos.Service;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 28/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public abstract class NoParamServiceComponent extends ServiceComponent {
    public NoParamServiceComponent(Service service) {
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
