package com.avixy.qrtoken.gui.servicos.components.password;

import com.avixy.qrtoken.core.extensions.components.PasswordField;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.password.StorePukService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 20/11/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.PASSWORD)
public class StorePukServiceComponent extends ServiceComponent {
    StorePukService service;
    TextField pukField = new PasswordField();

    /**
     * @param service
     * @param qrCodePolicy
     */
    @Inject
    protected StorePukServiceComponent(StorePukService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label label = new Label("Armazenar PUK");
        label.setFont(new Font(18));
        migPane.add(label, "wrap, span");
        migPane.add(new Label("PUK:"));
        migPane.add(pukField, "wrap");
        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setPuk(pukField.getText());

        return service;
    }
}
