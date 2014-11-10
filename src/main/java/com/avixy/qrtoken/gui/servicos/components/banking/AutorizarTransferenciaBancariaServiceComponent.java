package com.avixy.qrtoken.gui.servicos.components.banking;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.servicos.banking.AutorizarTransferenciaBancariaService;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.CalendarTextField;
import org.tbee.javafx.scene.layout.MigPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
@ServiceComponent.Category(category = ServiceCategory.BANCARIO)
@AcceptsKey(keyType = KeyType.HMAC)
public class AutorizarTransferenciaBancariaServiceComponent extends ServiceComponent {

    private Stage formStage = new Stage();
    private final String FXML_PATH = "/fxml/transfcc.fxml";
    private final AutorizarTransferenciaBancariaService service;
    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_PATH));

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

    private AesSelect comboAes = new AesSelect();
    private HmacSelect comboHmac = new HmacSelect();
    private TemplateSelect templateComboBox = new TemplateSelect();
    private PinField pinTextField = new PinField();
    private CalendarTextField dataCalendarTextField = new CalendarTextField();
    private TextField tanTextField = new TextField();
    private TextField valorTextField = new TextField();
    private TextField origemNomeTextField = new TextField();
    private TextField origemAgenciaTextField = new TextField();
    private TextField destinoNomeTextField = new TextField();
    private TextField origemContaTextField = new TextField();
    private TextField destinoContaTextField = new TextField();
    private TextField destinoAgenciaTextField = new TextField();
    private TimestampField timestampField = new TimestampField();

    @Inject
    public AutorizarTransferenciaBancariaServiceComponent(AutorizarTransferenciaBancariaService service, QrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;

        fxmlLoader.setController(this);
        try {
            Parent parent = (Parent) fxmlLoader.load();
            formStage.setScene(new Scene(parent));

            // setup the formStage
            origemLabel.setText("Origem");
            destinoLabel.setText("Destino");
            chaveLabel.setText("Chaves");
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
            Label labelAes = new Label("AES:");
            Label labelHmac = new Label("HMAC:");

            vBoxChaves.getChildren().addAll(labelAes, comboAes, labelHmac, comboHmac,new Label("Template:"), templateComboBox);
            chavesPane.getChildren().add(vBoxChaves);
            //endchaves

            //outros dados
            MigPane migPane = new MigPane();
            migPane.add(new Label("Valor:"), "label");
            migPane.add(valorTextField);
            migPane.add(new Label("TAN:"));
            migPane.add(tanTextField, "wrap");
            migPane.add(new Label("Data:"));
            migPane.add(dataCalendarTextField);

            migPane.add(new Label("Timestamp:"));
            migPane.add(timestampField, "wrap");
            migPane.add(new Label("PIN:"));
            migPane.add(pinTextField);

            dadosPane.getChildren().add(migPane);
            //end

            okButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    formStage.close();
                }
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

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openForm();
            }
        });
        migPane.add(label, "wrap");
        migPane.add(button);

        return migPane;
    }

    public void openForm(){
        formStage.show();
        formStage.toFront();
    }

    public void okForm(){
        formStage.close();
    }

    @Override
    public Service getService() {
        service.setAesKey(comboAes.getValue().getHexValue());
        service.setHmacKey(comboHmac.getValue().getHexValue());
        service.setTemplate(templateComboBox.getValue().byteValue());
        //pin e tan
        service.setPin(pinTextField.getText());
        service.setTan(tanTextField.getText());

        //timestamp
        service.setTimestamp(timestampField.getValue());
        //data
        service.setData(dataCalendarTextField.getValue().getTime());
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
    }
}
