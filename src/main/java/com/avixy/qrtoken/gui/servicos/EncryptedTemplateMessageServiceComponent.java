package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.EncryptedTemplateMessageService;
import com.avixy.qrtoken.negocio.servico.Service;
import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.PinParam;
import com.avixy.qrtoken.negocio.servico.params.StringWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.TimestampParam;
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
import jfxtras.labs.scene.control.CalendarTimeTextField;
import org.tbee.javafx.scene.layout.MigPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 04/09/2014
 */
@ServiceComponent.Category(category = ServiceCategory.BANCARIO)
@AcceptsKey(keyType = KeyType.HMAC)
public class EncryptedTemplateMessageServiceComponent extends ServiceComponent {

    private Stage formStage = new Stage();
    private final String FXML_PATH = "/fxml/transfcc.fxml";
    private final EncryptedTemplateMessageService encryptedTemplateMessageService;
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

    private ComboBox<Chave> comboAes = new ComboBox<>();
    private ComboBox<Chave> comboHmac = new ComboBox<>();
    private CalendarTextField timestampTextField = new CalendarTextField();
    private ComboBox<Integer> templateComboBox = new ComboBox<>();
    private TextField pinTextField = new TextField();
    private CalendarTextField dataCalendarTextField = new CalendarTextField();
    private TextField tanTextField = new TextField();
    private TextField valorTextField = new TextField();
    private TextField origemNomeTextField = new TextField();
    private TextField origemAgenciaTextField = new TextField();
    private TextField destinoNomeTextField = new TextField();
    private TextField origemContaTextField = new TextField();
    private TextField destinoContaTextField = new TextField();
    private TextField destinoAgenciaTextField = new TextField();
    CalendarTimeTextField timestampTimeField = new CalendarTimeTextField();

    public EncryptedTemplateMessageServiceComponent() {
        this.encryptedTemplateMessageService = injector.getInstance(EncryptedTemplateMessageService.class);
        this.service = encryptedTemplateMessageService;

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
            titleLabel.setText(encryptedTemplateMessageService.getServiceName());

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

            comboAes.setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.AES));
            comboHmac.setItems(ChavesSingleton.getInstance().observableChavesFor(KeyType.HMAC));

            //template param
            List<Integer> templates = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15);
            templateComboBox.setItems(FXCollections.observableList(templates));

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
            migPane.add(timestampTextField, "wrap");
            migPane.add(new Label("PIN:"));
            migPane.add(pinTextField);
            migPane.add(new Label("Time:"));
            migPane.add(timestampTimeField, "wrap");

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
        Label label = new Label(encryptedTemplateMessageService.getServiceName());
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
        encryptedTemplateMessageService.setChaveAes(comboAes.getValue());
        encryptedTemplateMessageService.setChaveHmac(comboHmac.getValue());
        encryptedTemplateMessageService.setTemplate(new ByteWrapperParam(templateComboBox.getValue().byteValue()));
        encryptedTemplateMessageService.setPin(new PinParam(pinTextField.getText()));
        encryptedTemplateMessageService.setDate(new TimestampParam(timestampTextField.getValue().getTime())); //TODO: date + time

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestampTextField.getValue().getTime());
        Calendar hora = timestampTimeField.getValue();
        calendar.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));

        TimestampParam timestampParam = new TimestampParam(calendar.getTime());
        TimestampParam dataParam = new TimestampParam(dataCalendarTextField.getValue().getTime());

        encryptedTemplateMessageService.setParams(
                new StringWrapperParam(origemNomeTextField.getText()),
                new StringWrapperParam(origemAgenciaTextField.getText()),
                new StringWrapperParam(origemContaTextField.getText()),
                new StringWrapperParam(destinoNomeTextField.getText()),
                new StringWrapperParam(destinoAgenciaTextField.getText()),
                new StringWrapperParam(destinoContaTextField.getText()),
                new StringWrapperParam(valorTextField.getText()),
                timestampParam,
                new StringWrapperParam(tanTextField.getText()),
                dataParam
        );
        return encryptedTemplateMessageService;
    }
}
