package com.avixy.qrtoken.gui.servicos.components.chaves;

import com.avixy.qrtoken.core.extensions.components.AesSelect;
import com.avixy.qrtoken.core.extensions.components.KeySetSelect;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.chaves.DeleteSymKeyService;
import com.google.inject.Inject;
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
    private AesSelect aesSelect = new AesSelect();

    public DeleteKeySetServiceComponent(DeleteSymKeyService service, QrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label("Deletar chaves");

        title.setFont(new Font(18));
        migPane.add(title, "wrap");

        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");

        migPane.add(new Label("Chave AES:"));
        migPane.add(aesSelect, "wrap");

        return migPane;
    }

    @Override
    public Service getService()
    {
        service.setTimestamp(timestampField.getValue());
        service.setAesKey(aesSelect.getValue().getHexValue());

        return service;
    }
}
