package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.core.extensions.components.HexField;
import com.avixy.qrtoken.core.extensions.components.validators.JideSimpleValidator;
import com.avixy.qrtoken.core.extensions.components.validators.JideSizeValidator;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jidefx.scene.control.validation.ValidationEvent;
import jidefx.scene.control.validation.ValidationMode;
import jidefx.scene.control.validation.ValidationUtils;
import net.miginfocom.layout.CC;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.CryptoException;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.Arrays;

/**
 * Created on 27/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AvixyKeyConfigurationController extends MigPane {
    private TextField idField;
    private HexField component1Field;
    private HexField component2Field;
    private HexField component3Field;

    private HexField kAes1Field;
    private HexField kAes2Field;
    private HexField kHmac1Field;
    private HexField kHmac2Field;

    private Button saveButton = new Button("Salvar");
    @FXML
    private TableView<AvixyKeyConfiguration> perfisTable;

    @FXML
    private AnchorPane containerPane;

    boolean comp1Ok;
    boolean comp2Ok;
    boolean comp3Ok;

    public void initialize() {
        idField = new TextField();
        component1Field = new HexField(64);
        component2Field = new HexField(64);
        component3Field = new HexField(64);
        kAes1Field = new HexField(64);
        kAes2Field = new HexField(64);
        kHmac1Field = new HexField(64);
        kHmac2Field = new HexField(64);
        /*
        aqui estou incluindo validator ON_FLY e ON_DEMAND, o ON_DEMAND é chamado antes de validar e o ON_FLY serve p/ que
        o resultado seja avaliado enquanto o usuário digita
        */
        ValidationUtils.install(idField, new JideSimpleValidator(), ValidationMode.ON_FLY);
        ValidationUtils.install(component1Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(component2Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(component3Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(kAes1Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(kAes2Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(kHmac1Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(kHmac2Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(idField, new JideSimpleValidator(), ValidationMode.ON_DEMAND);
        ValidationUtils.install(component1Field, new JideSizeValidator(64), ValidationMode.ON_DEMAND);
        ValidationUtils.install(component2Field, new JideSizeValidator(64), ValidationMode.ON_DEMAND);
        ValidationUtils.install(component3Field, new JideSizeValidator(64), ValidationMode.ON_DEMAND);
        ValidationUtils.install(kAes1Field, new JideSizeValidator(64), ValidationMode.ON_DEMAND);
        ValidationUtils.install(kAes2Field, new JideSizeValidator(64), ValidationMode.ON_DEMAND);
        ValidationUtils.install(kHmac1Field, new JideSizeValidator(64), ValidationMode.ON_DEMAND);
        ValidationUtils.install(kHmac2Field, new JideSizeValidator(64), ValidationMode.ON_DEMAND);

        MigPane migPane = new MigPane();
        Label title = new Label("Configurar Chave Avixy");
        title.setFont(new Font(18));
        migPane.add(title, "span, wrap");
        migPane.add(new Label("Identificação:"));
        migPane.add(idField, "wrap");
        Label compTitle = new Label("Componentes de Chave");
        compTitle.setFont(new Font("System Bold", 12));
        migPane.add(compTitle, "span, wrap");
        migPane.add(new Label("Componente 1:"));
        migPane.add(component1Field, "wrap");
        migPane.add(new Label("Componente 2:"));
        migPane.add(component2Field, "wrap");
        migPane.add(new Label("Componente 3:"));
        migPane.add(component3Field, "wrap");
        Label derivTitle = new Label("Constantes de Derivação");
        derivTitle.setFont(new Font("System Bold", 12));
        migPane.add(derivTitle, "span, wrap");
        migPane.add(new Label("AES:"), "wrap");
        migPane.add(kAes1Field);
        migPane.add(kAes2Field, "wrap");
        migPane.add(new Label("HMAC:"), "wrap");
        migPane.add(kHmac1Field);
        migPane.add(kHmac2Field, "wrap");
        saveButton.setDefaultButton(true);
        migPane.add(saveButton, "span, wrap");

        CheckBox checkBox = new CheckBox("Ver componente");
        component1Field.addEventHandler(ValidationEvent.ANY, event2 -> { comp1Ok = event2.getEventType() == ValidationEvent.VALIDATION_OK; });
        component2Field.addEventHandler(ValidationEvent.ANY, event2 -> { comp2Ok = event2.getEventType() == ValidationEvent.VALIDATION_OK; });
        component3Field.addEventHandler(ValidationEvent.ANY, event2 -> { comp3Ok = event2.getEventType() == ValidationEvent.VALIDATION_OK; });

        EventHandler<ValidationEvent> showCheckboxEventHandler = event1 -> {
            if (comp1Ok && comp2Ok && comp3Ok) {
                if (!migPane.getChildren().contains(checkBox)) {
                    migPane.add(migPane.getChildren().indexOf(derivTitle), checkBox, "span, wrap");
                    checkBox.setVisible(true);
                }
            } else {
                checkBox.setVisible(false);
                checkBox.setSelected(false);
                migPane.remove(checkBox);
            }
        };
        component1Field.addEventHandler(ValidationEvent.ANY, showCheckboxEventHandler);
        component2Field.addEventHandler(ValidationEvent.ANY, showCheckboxEventHandler);
        component3Field.addEventHandler(ValidationEvent.ANY, showCheckboxEventHandler);

        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            Label label = new Label();
            {
                checkBox.visibleProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (!newValue1) migPane.remove(label);
                });
            }
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (comp1Ok && comp2Ok && comp3Ok) {
                        try {
                            byte[] keyComponent1 = Hex.decodeHex(component1Field.getText().toCharArray());
                            byte[] keyComponent2 = Hex.decodeHex(component2Field.getText().toCharArray());
                            byte[] keyComponent3 = Hex.decodeHex(component3Field.getText().toCharArray());
                            byte[] baseDerivationKey = new byte[32];
                            for (int i = 0; i < 32; i++) {
                                baseDerivationKey[i] = (byte) (keyComponent1[i] ^ keyComponent2[i] ^ keyComponent3[i]);
                            }
                            label.setTextFill(Color.RED);
                            label.setText(Hex.encodeHexString(baseDerivationKey));
                            migPane.remove(label);
                            migPane.add(migPane.getChildren().indexOf(checkBox) + 1, label, new CC().wrap().spanX());
                        } catch (DecoderException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    migPane.remove(label);
                }
            }
        });
        containerPane.getChildren().add(migPane);
        containerPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            MainController.keyConfigStage.setHeight(MainController.keyConfigStage.getHeight() + (newValue.doubleValue() - oldValue.doubleValue()));
        });

        //table code
        ChavesTableUtil.setupTable(perfisTable);

        saveButton.setOnAction(event -> {
            if (ValidationUtils.validateOnDemand(idField.getParent().getParent())) {
                AvixyKeyConfiguration avixyKeyConfiguration = new AvixyKeyConfiguration();
                avixyKeyConfiguration.setName(idField.getText());
                avixyKeyConfiguration.setKeyComponents(component1Field.getValue(), component2Field.getValue(), component3Field.getValue());
                avixyKeyConfiguration.setAesConstants(kAes1Field.getValue(), kAes2Field.getValue());
                avixyKeyConfiguration.setHmacConstants(kHmac1Field.getValue(), kHmac2Field.getValue());

                AvixyKeyConfiguration.getConfigList().add(avixyKeyConfiguration);
                if (AvixyKeyConfiguration.getConfigList().size() == 1)
                    AvixyKeyConfiguration.select(avixyKeyConfiguration);
            }
        });

        class CheckValueEventHandler implements EventHandler<ValidationEvent> {
            Label label = new Label("Check Value:");
            {
                label.setTextFill(Color.RED);
            }
            @Override
            public void handle(ValidationEvent event) {
                if (event.getEventType() == ValidationEvent.VALIDATION_OK) {
                    try {
                        label.setText("Check Value: " + Hex.encodeHexString(calcCheckValue(Hex.decodeHex(((TextField)event.getSource()).getText().toCharArray()))));
                        migPane.remove(label);
                        migPane.add(migPane.getChildren().indexOf(event.getSource()) + 1, label, new CC().wrap());
                    } catch (DecoderException | CryptoException e) {
                        e.printStackTrace();
                    }
                } else {
                    migPane.getChildren().remove(label);
                }
            }

            byte[] calcCheckValue(byte[] key) throws CryptoException {
                AesKeyPolicy aesKeyPolicy = new AesKeyPolicy(new byte[16], true);
                aesKeyPolicy.setKey(key);
                byte[] message = new byte[16];
                return Arrays.copyOfRange(aesKeyPolicy.apply(message), 0, 6);
            }
        };
        class AesCheckValueEventHandler extends CheckValueEventHandler {
            @Override
            public void handle(ValidationEvent event) {
                if (event.getEventType() == ValidationEvent.VALIDATION_OK) {
                    try {
                        byte[] kAes1 = Hex.decodeHex(kAes1Field.getText().toCharArray());
                        byte[] kAes2 = Hex.decodeHex(kAes2Field.getText().toCharArray());
                        if (kAes1.length != 32 || kAes2.length != 32) { return; }

                        byte[] aesConstant = new byte[32];
                        for (int i = 0; i < 32; i++) {
                            aesConstant[i] = (byte) (kAes1[i] ^ kAes2[i]);
                        }
                        label.setText("AES Check Value: " + Hex.encodeHexString(calcCheckValue(aesConstant)));
                        migPane.remove(label);
                        migPane.add(migPane.getChildren().indexOf(kAes2Field) + 1, label, new CC().wrap().spanX());
                    } catch (DecoderException | CryptoException e) {
//                        e.printStackTrace();
                    }
                } else {
                    migPane.getChildren().remove(label);
                }
            }
        };
        class HmacCheckValueEventHandler extends CheckValueEventHandler {
            @Override
            public void handle(ValidationEvent event) {
                if (event.getEventType() == ValidationEvent.VALIDATION_OK) {
                    if (kHmac1Field.getProperties().get("Validation.Result") == "VALIDATION_OK" && kHmac2Field.getProperties().get("Validation.Result") == "VALIDATION_OK") {
                        try {
                            byte[] kHmac1 = Hex.decodeHex(kHmac1Field.getText().toCharArray());
                            byte[] kHmac2 = Hex.decodeHex(kHmac2Field.getText().toCharArray());
                            if (kHmac1.length != 32 || kHmac2.length != 32) { return; }

                            byte[] hmacConstant = new byte[32];
                            for (int i = 0; i < 32; i++) {
                                hmacConstant[i] = (byte) (kHmac1[i] ^ kHmac2[i]);
                            }
                            label.setText("HMAC Check Value: " + Hex.encodeHexString(calcCheckValue(hmacConstant)));
                            migPane.remove(label);
                            migPane.add(migPane.getChildren().indexOf(kHmac2Field) + 1, label, new CC().wrap().spanX());
                        } catch (DecoderException | CryptoException e) {
//                            e.printStackTrace();
                        }

                    }
                } else {
                    migPane.getChildren().remove(label);
                }
            }
        }
        component1Field.addEventHandler(ValidationEvent.ANY, new CheckValueEventHandler());
        component2Field.addEventHandler(ValidationEvent.ANY, new CheckValueEventHandler());
        component3Field.addEventHandler(ValidationEvent.ANY, new CheckValueEventHandler());
        AesCheckValueEventHandler aesCheckValueEventHandler = new AesCheckValueEventHandler();
        kAes1Field.addEventHandler(ValidationEvent.ANY, aesCheckValueEventHandler);
        kAes2Field.addEventHandler(ValidationEvent.ANY, aesCheckValueEventHandler);
        HmacCheckValueEventHandler hmacCheckValueEventHandler = new HmacCheckValueEventHandler();
        kHmac1Field.addEventHandler(ValidationEvent.ANY, hmacCheckValueEventHandler);
        kHmac2Field.addEventHandler(ValidationEvent.ANY, hmacCheckValueEventHandler);
    }

    /**
     * Util for setting up the {@link com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration} table
     *
     * @author Breno Salgado <breno.salgado@avixy.com>
     *
     * Created on 20/08/2014
     */
    public static class ChavesTableUtil {

        public static void setupTable(final TableView<AvixyKeyConfiguration> tabela) {
            /* colunas */
            TableColumn<AvixyKeyConfiguration, Boolean> colunaSelect = new TableColumn<>("");
            TableColumn<AvixyKeyConfiguration, String> colunaId = new TableColumn<>("");
            TableColumn<AvixyKeyConfiguration, AvixyKeyConfiguration> colunaDelete = new TableColumn<>("");

            /* Value factories */
            colunaSelect.setCellFactory(param1 -> new CheckBoxTableCell<>(param -> AvixyKeyConfiguration.getConfigList().get(param).selectedProperty()));
            colunaId.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().toString()));
            colunaDelete.setCellFactory(chaveBooleanTableColumn -> new AvxConfigDeleteCell());
            colunaDelete.setCellValueFactory(features -> new SimpleObjectProperty<>(features.getValue()));

            /* widths */
            colunaSelect.prefWidthProperty().bind(tabela.widthProperty().multiply(0.15));
            colunaId.prefWidthProperty().bind(tabela.widthProperty().multiply(0.7));
            colunaDelete.prefWidthProperty().bind(tabela.widthProperty().multiply(0.15));

            tabela.itemsProperty().set(AvixyKeyConfiguration.getConfigList());
            tabela.getColumns().addAll(colunaSelect, colunaId, colunaDelete);

            tabela.setItems(AvixyKeyConfiguration.getConfigList());

            tabela.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                AvixyKeyConfiguration.select(newValue);
            });
        }

        /** Célula com botão para deletar uma <code>Chave</code>. */
        public static class AvxConfigDeleteCell extends TableCell<AvixyKeyConfiguration, AvixyKeyConfiguration> {
            final Button xButton = new Button("x");
            final StackPane paddedButton = new StackPane();

            AvxConfigDeleteCell() {
                paddedButton.setPadding(new Insets(3));
                paddedButton.getChildren().add(xButton);
                xButton.setOnAction(actionEvent -> {
                    AvixyKeyConfiguration.remove(getItem());
                });
            }

            /** places a button in the row only if the row is not empty. */
            @Override
            protected void updateItem(AvixyKeyConfiguration item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(paddedButton);
                }
            }
        }
    }
}
