package com.avixy.qrtoken.gui.servicos.components.banking;

import com.avixy.qrtoken.gui.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.banking.AutorizarTransferenciaBancariaService;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jidefx.scene.control.decoration.DecorationPane;
import org.bouncycastle.crypto.CryptoException;
import org.tbee.javafx.scene.layout.MigPane;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.time.ZoneOffset;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
@ServiceComponent.Category(category = ServiceCategory.BANCARIO)
@AcceptsKey(keyType = KeyType.HMAC)
public class AutorizarTransferenciaBancariaServiceComponent extends ServiceComponent {
    private Stage formStage = new Stage();
    private final AutorizarTransferenciaBancariaService service;

    @FXML private AnchorPane root;
    @FXML private Label titleLabel;
    @FXML private Label origemLabel;
    @FXML private Pane  origemPane;
    @FXML private Label destinoLabel;
    @FXML private Pane  destinoPane;
    @FXML private Label chaveLabel;
    @FXML private Pane  chavesPane;
    @FXML private Pane  dadosPane;
    @FXML private Button okButton;

    private SerialNumberField serialNumberField = new SerialNumberField();
    private TemplateSlotSelect templateComboBox = new TemplateSlotSelect();
    private PasswordField pinTextField = new PasswordField();
    private DatePicker datePicker = new FormattedDatePicker();
    private TextField tanTextField = new TextField();
    private RestrictiveTextField valorTextField = new RestrictiveTextField();
    private TextField origemNomeTextField = new TextField();
    private RestrictiveTextField origemAgenciaTextField = new RestrictiveTextField();
    private TextField destinoNomeTextField = new TextField();
    private TextField origemContaTextField = new TextField();
    private TextField destinoContaTextField = new TextField();
    private RestrictiveTextField destinoAgenciaTextField = new RestrictiveTextField();
    private TimestampField timestampField = new TimestampField();

    {
        valorTextField.setRestrict("[0-9,.rR$\\s]");
        origemAgenciaTextField.setRestrict("[0-9\\-]");
        destinoAgenciaTextField.setRestrict("[0-9\\-]");
    }

    @Inject
    public AutorizarTransferenciaBancariaServiceComponent(AutorizarTransferenciaBancariaService service) {
        super(service);
        this.service = service;

        String FXML_PATH = "/fxml/transfcc.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_PATH));
        fxmlLoader.setController(this);
        try {
            Parent parent = fxmlLoader.load();
            formStage.setScene(new Scene(new DecorationPane(parent)));

            // setup the formStage
            origemLabel.setText("Origem");
            destinoLabel.setText("Destino");
            chaveLabel.setText("");
            // setup  the form

            //title
            titleLabel.setText(service.getServiceName());

            //origem
            VBox vBox = new VBox();
            Label nomeL = new Label("Nome:");
            Label agL = new Label("Ag:");
            Label ccL = new Label("CC:");

            vBox.getChildren().addAll(nomeL, origemNomeTextField, agL, origemAgenciaTextField, ccL, origemContaTextField);
            origemPane.getChildren().add(vBox);
            //endorigem

            //destino
            VBox vBoxDes = new VBox();
            Label nomeDes = new Label("Nome:");
            Label agDesL = new Label("Ag:");
            Label ccDesL = new Label("CC:");

            vBoxDes.getChildren().addAll(nomeDes, destinoNomeTextField, agDesL, destinoAgenciaTextField, ccDesL, destinoContaTextField);
            destinoPane.getChildren().add(vBoxDes);
            //enddestino

            //chaves
            VBox vBoxChaves = new VBox();

            pinTextField.setMaxWidth(150);
            vBoxChaves.getChildren().addAll(new Label("Slot:"), templateComboBox, new Label("Timestamp:"), timestampField, new Label("PIN:"), pinTextField);
            chavesPane.getChildren().add(vBoxChaves);
            //endchaves

            //outros dados
            MigPane migPane = new MigPane();
            migPane.add(new Label("Valor:"), "label");
            migPane.add(valorTextField);
            migPane.add(new Label("TAN:"));
            migPane.add(tanTextField, "wrap");
            migPane.add(new Label("Data:"));
            datePicker.setPrefWidth(150);
            migPane.add(datePicker);

            migPane.add(new Label("Serial Number:"));
            migPane.add(serialNumberField);

            dadosPane.getChildren().add(migPane);
            //end

            okButton.setOnAction(actionEvent -> {
                formStage.close();
            });
            //endsetup
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Node getNode() {
        MigPane migPane = new MigPane();
        Label label = new Label(service.getServiceName());
        label.setFont(new Font(18));
        Button button = new Button("Clique para setar os parâmetros");

        button.setOnAction(actionEvent -> openForm());
        migPane.add(label, "wrap");
        migPane.add(button);

        return migPane;
    }

    public void openForm(){
        formStage.show();
        formStage.toFront();
    }

    @Override
    public Service getService() throws AvixyKeyConfiguration.AvixyKeyNotConfigured, GeneralSecurityException, CryptoException {
        try {
            service.setAesKey(AvixyKeyConfiguration.getSelected().getAesKey(serialNumberField.getText()));
            service.setHmacKey(AvixyKeyConfiguration.getSelected().getHmacKey(serialNumberField.getText()));
            service.setTemplateSlot(templateComboBox.getValue().byteValue());
            //pin e tan
            service.setPin(pinTextField.getText());
            service.setTan(tanTextField.getText());

            //timestamp
            service.setTimestamp(timestampField.getValue());
            //data
            service.setDate(Date.from(datePicker.getValue().atStartOfDay().toInstant(ZoneOffset.UTC)));
            //origem
            service.setNomeOrigem(origemNomeTextField.getText());
            service.setAgenciaOrigem(origemAgenciaTextField.getText());
            service.setContaOrigem(origemContaTextField.getText());
            //destino
            service.setNomeDestino(destinoNomeTextField.getText());
            service.setAgenciaDestino(destinoAgenciaTextField.getText());
            service.setContaDestino(destinoContaTextField.getText());
            //valor
            service.setValor(valorTextField.getText());
            return service;
        } catch (IllegalArgumentException e) {
            openForm();
            controller.handleException(e);
            return null;
        }
    }
}
