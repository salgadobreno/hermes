package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.gui.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.chaves.ClientKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.UpdateSymmetricKeyClientService;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.UpdateSymmetricKeyService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 29/01/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.CHAVES)
public class UpdateSymmetricKeyClientServiceComponent extends ServiceComponent {
    private UpdateSymmetricKeyService service;

    private TimestampField timestampField = new TimestampField();

    private ClientKeyProfileSelect currentKeyProfileSelect = new ClientKeyProfileSelect();
    private ClientKeyProfileSelect newKeyProfileSelect = new ClientKeyProfileSelect();
    private SerialNumberField serialNumberField = new SerialNumberField();

    @Inject
    protected UpdateSymmetricKeyClientServiceComponent(UpdateSymmetricKeyClientService service) {
        super(service);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());

        title.setFont(new Font(18));
        migPane.add(title, "span, wrap");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField, "wrap");

        migPane.add(new Label("New Key Profile:"));
        migPane.add(newKeyProfileSelect, "wrap");

        Separator separator = new Separator();
        separator.setPrefWidth(200);
        migPane.add(separator, "span, wrap");

        migPane.add(new Label("Current Key Profile:"));
        migPane.add(currentKeyProfileSelect, "wrap");
        currentKeyProfileSelect.getSelectionModel().select(ClientKeyConfiguration.getSelected());

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setTimestamp(timestampField.getValue());
        service.setAesKey(currentKeyProfileSelect.getSelectionModel().getSelectedItem().getAesKey(serialNumberField.getText()));
        service.setHmacKey(currentKeyProfileSelect.getSelectionModel().getSelectedItem().getHmacKey(serialNumberField.getText()));

        service.setSecretKey(newKeyProfileSelect.getSelectionModel().getSelectedItem().getAesKey(serialNumberField.getText()));
        service.setAuthKey(newKeyProfileSelect.getSelectionModel().getSelectedItem().getHmacKey(serialNumberField.getText()));

        return service;
    }
}
