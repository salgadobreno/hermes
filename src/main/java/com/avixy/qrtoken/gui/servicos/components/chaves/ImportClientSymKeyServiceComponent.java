package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.core.extensions.components.AesKeySelect;
import com.avixy.qrtoken.core.extensions.components.HmacKeySelect;
import com.avixy.qrtoken.core.extensions.components.TextFieldLimited;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.ImportClientSymKeySetService;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 30/01/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.CHAVES)
public class ImportClientSymKeyServiceComponent extends ServiceComponent {
    private final ImportClientSymKeySetService service;

    private TimestampField timestampField = new TimestampField();

    private AesKeySelect clientAesKeySelect = new AesKeySelect();
    private HmacKeySelect clientAuthKeySelect = new HmacKeySelect();

    private TextFieldLimited serialNumberField = new TextFieldLimited(10);

    @Inject
    public ImportClientSymKeyServiceComponent(ImportClientSymKeySetService service) {
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

        migPane.add(new Label("Aes Key:"));
        migPane.add(clientAesKeySelect, "wrap");

        migPane.add(new Label("Auth Key::"));
        migPane.add(clientAuthKeySelect, "wrap");

        Separator separator = new Separator();
        separator.setPrefWidth(200);
        migPane.add(separator, "span, wrap");

        migPane.add(new Label("Serial Number:"));
        migPane.add(serialNumberField);

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        service.setTimestamp(timestampField.getValue());
        service.setAesKey(AvixyKeyConfiguration.getInstance().getAesKey(serialNumberField.getText()));
        service.setHmacKey(AvixyKeyConfiguration.getInstance().getHmacKey(serialNumberField.getText()));
        service.setClientAesKey(clientAesKeySelect.getValue().getHexValue());
        service.setClientAuthKey(clientAuthKeySelect.getValue().getHexValue());

        return service;
    }

}
