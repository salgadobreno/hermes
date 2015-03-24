package com.avixy.qrtoken.gui.servicos.components.ktamper;

import com.avixy.qrtoken.core.extensions.components.TimeZoneField;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.gui.servicos.components.NoParamServiceComponent;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.ktamper.GenerateKtamperService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.Arrays;
import java.util.TimeZone;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.KTAMPER)
public class GenerateKtamperServiceComponent extends NoParamServiceComponent {
    private TimestampField timestampField = new TimestampField();
    private TimeZoneField timeZoneField = new TimeZoneField();

    @Inject
    public GenerateKtamperServiceComponent(GenerateKtamperService service) {
        super(service);
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label title = new Label(service.getServiceName());
        title.setFont(new Font(18));
        migPane.add(title, "wrap, span");
        migPane.add(new Label("Timestamp:"));
        migPane.add(timestampField, "wrap");
        migPane.add(new Label("Fuso horário:"));
        migPane.add(timeZoneField, "wrap");

        return migPane;
    }

    @Override
    public Service getService() throws Exception {
        GenerateKtamperService generateKtamperService = (GenerateKtamperService) service;
        generateKtamperService.setTimestamp(timestampField.getValue());
        generateKtamperService.setTimezone(timeZoneField.getTimeZone());

        return super.getService();
    }
}
