package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.gui.components.SerialNumberField;
import com.avixy.qrtoken.gui.components.TimestampField;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.chaves.ClientKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.DeleteSymKeyService;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created by Italo on 1/15/2015.
 */
public abstract class DeleteKeySetServiceComponent extends ServiceComponent {

    private DeleteSymKeyService service;
    private TimestampField timestampField = new TimestampField();
    private SerialNumberField serialNumberField = new SerialNumberField();

    public DeleteKeySetServiceComponent(DeleteSymKeyService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());

        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField);

        return migPane;
    }

    @Override
    public Service getService() throws Exception
    {
        service.setTimestamp(timestampField.getValue());
        service.setHmacKey(ClientKeyConfiguration.getSelected().getHmacKey(serialNumberField.getText()));

        return service;
    }
}
