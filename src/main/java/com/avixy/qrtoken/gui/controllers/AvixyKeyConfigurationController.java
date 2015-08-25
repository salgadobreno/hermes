package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.gui.components.HexField;
import com.avixy.qrtoken.gui.components.validators.JideSimpleValidator;
import com.avixy.qrtoken.gui.components.validators.JideSizeValidator;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import javafx.beans.binding.Bindings;
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
import javafx.scene.layout.Pane;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 27/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AvixyKeyConfigurationController extends MigPane {
    private TextField idField = new TextField();
    private ComponentInput comp1 = new ComponentInput();
    private ComponentInput comp2 = new ComponentInput();
    private ComponentInput comp3 = new ComponentInput();

    private HexField kAes1Field = new HexField(64);
    private HexField kAes2Field = new HexField(64);
    private HexField kHmac1Field = new HexField(64);
    private HexField kHmac2Field = new HexField(64);

    {
        HexField[] fields = new HexField[] {
                kAes1Field, kAes2Field, kHmac1Field, kHmac2Field
        };
        for (HexField field : fields) {
            field.setPrefColumnCount(16+1);
        }
    }

    private Button saveButton = new Button("Salvar");
    @FXML
    private TableView<AvixyKeyConfiguration> perfisTable;

    @FXML
    private AnchorPane containerPane;

    boolean comp1Ok;
    boolean comp2Ok;
    boolean comp3Ok;

    class ComponentInput extends MigPane {
        HexField resultTextField = new HexField(64);
        HexField boundTF1 = new HexField(8);
        HexField boundTF2 = new HexField(8);
        HexField boundTF3 = new HexField(8);
        HexField boundTF4 = new HexField(8);
        HexField boundTF5 = new HexField(8);
        HexField boundTF6 = new HexField(8);
        HexField boundTF7 = new HexField(8);
        HexField boundTF8 = new HexField(8);
        List<HexField> hexFields;

        {
            Pane pane = new Pane(resultTextField); // to prevent an error of ValidationUtils asking getParent() for decorations
            hexFields = Arrays.asList(
                    boundTF1, boundTF2,
                    boundTF3, boundTF4,
                    boundTF5, boundTF6,
                    boundTF7, boundTF8
            );
            for (int i = 0; i < hexFields.size(); i++) {
                HexField field = hexFields.get(i);
                field.setPrefColumnCount(8 + 1);
                ValidationUtils.install(field, new JideSizeValidator(8), ValidationMode.ON_FLY);
                if (i % 2 == 0) { add(field); } else { add(field, "wrap"); }
            }

            resultTextField.textProperty().bind(Bindings.createStringBinding(
                    () -> hexFields.stream().map(HexField::getText).collect(Collectors.joining()),
                    boundTF1.textProperty(), boundTF2.textProperty(),
                    boundTF3.textProperty(), boundTF4.textProperty(),
                    boundTF5.textProperty(), boundTF6.textProperty(),
                    boundTF7.textProperty(), boundTF8.textProperty()
            ));

            ValidationUtils.install(resultTextField, new JideSizeValidator(64), ValidationMode.ON_FLY);
        }

        public void clearValidations() {
            boundTF1.fireEvent(new ValidationEvent(ValidationEvent.VALIDATION_UNKNOWN));
            boundTF2.fireEvent(new ValidationEvent(ValidationEvent.VALIDATION_UNKNOWN));
            boundTF3.fireEvent(new ValidationEvent(ValidationEvent.VALIDATION_UNKNOWN));
            boundTF4.fireEvent(new ValidationEvent(ValidationEvent.VALIDATION_UNKNOWN));
            boundTF5.fireEvent(new ValidationEvent(ValidationEvent.VALIDATION_UNKNOWN));
            boundTF6.fireEvent(new ValidationEvent(ValidationEvent.VALIDATION_UNKNOWN));
            boundTF7.fireEvent(new ValidationEvent(ValidationEvent.VALIDATION_UNKNOWN));
            boundTF8.fireEvent(new ValidationEvent(ValidationEvent.VALIDATION_UNKNOWN));
        }

        public void setDefault(String defaultValue) {
            for (HexField field : hexFields) {
                field.setText(defaultValue);
            }
            clearValidations();
        }
    }

    public void initialize() {
        /* layout */
        MigPane migPane = new MigPane();
        Accordion accordion = new Accordion();
        MigPane migPaneComp1 = new MigPane();
        MigPane migPaneComp2 = new MigPane();
        MigPane migPaneComp3 = new MigPane();
        Label title = new Label("Configurar Chave Avixy");
        title.setFont(new Font(18));
        migPane.add(title, "span, wrap");
        migPane.add(new Label("Identificação:"));
        migPane.add(idField, "wrap");
        Label compTitle = new Label("Componentes de Chave");
        compTitle.setFont(new Font("System Bold", 12));
        migPane.add(compTitle, "span, wrap");
        migPaneComp1.add(new Label("Componente 1:"));
        migPaneComp1.add(comp1, "wrap");
        migPaneComp2.add(new Label("Componente 2:"));
        migPaneComp2.add(comp2, "wrap");
        migPaneComp3.add(new Label("Componente 3:"));
        migPaneComp3.add(comp3, "wrap");
        accordion.getPanes().addAll(new TitledPane("Componente 1", migPaneComp1), new TitledPane("Componente 2", migPaneComp2), new TitledPane("Componente 3", migPaneComp3));
        accordion.setExpandedPane(accordion.getPanes().get(0));
        migPane.add(accordion, "span");
        Label derivTitle = new Label("Constantes de Derivação");
        derivTitle.setFont(new Font("System Bold", 12));
        migPane.add(derivTitle, "span");
        migPane.add(new Label("AES:"), "span");
        migPane.add(kAes1Field);
        migPane.add(kAes2Field, "wrap");
        migPane.add(new Label("HMAC:"), "wrap");
        migPane.add(kHmac1Field);
        migPane.add(kHmac2Field, "wrap");
        saveButton.setDefaultButton(true);
        migPane.add(saveButton, "span, wrap");

        containerPane.getChildren().add(migPane);
        containerPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            MainController.avxKeyConfigStage.setHeight(MainController.avxKeyConfigStage.getHeight() + (newValue.doubleValue() - oldValue.doubleValue()));
        });
        /* validations */
        ValidationUtils.install(idField, new JideSimpleValidator(), ValidationMode.ON_FLY);
        ValidationUtils.install(kAes1Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(kAes2Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(kHmac1Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        ValidationUtils.install(kHmac2Field, new JideSizeValidator(64), ValidationMode.ON_FLY);
        /* events */
        accordion.expandedPaneProperty().addListener((observable1, oldValue1, newValue1) -> {
            int expanded = accordion.getPanes().indexOf(accordion.getExpandedPane());
            switch (expanded) {
                case 0:
                    comp2.clearValidations();
                    comp3.clearValidations();
                    break;
                case 1:
                    comp1.clearValidations();
                    comp3.clearValidations();
                    break;
                case 2:
                    comp1.clearValidations();
                    comp2.clearValidations();
                    break;
                default:
                    comp1.clearValidations();
                    comp2.clearValidations();
                    comp3.clearValidations();
            }
        });

        CheckBox checkBox = new CheckBox("Ver chave");
        comp1.resultTextField.addEventHandler(ValidationEvent.ANY, event2 -> {
            comp1Ok = event2.getEventType() == ValidationEvent.VALIDATION_OK;
        });
        comp2.resultTextField.addEventHandler(ValidationEvent.ANY, event2 -> {
            comp2Ok = event2.getEventType() == ValidationEvent.VALIDATION_OK;
        });
        comp3.resultTextField.addEventHandler(ValidationEvent.ANY, event2 -> {
            comp3Ok = event2.getEventType() == ValidationEvent.VALIDATION_OK;
        });

        EventHandler<ValidationEvent> componentsOkEventHandler = new EventHandler<ValidationEvent>() {
            Label label = new Label();
            @Override
            public void handle(ValidationEvent event1) {
                if ( comp1Ok && comp2Ok && comp3Ok ) {
                    if (!migPane.getChildren().contains(checkBox)) {
                        migPane.add(migPane.getChildren().indexOf(derivTitle), checkBox, "span, wrap");
                        checkBox.setVisible(true);
                    }
                    StringBuilder checkValue = new StringBuilder(Hex.encodeHexString(AvixyKeyConfigurationController.this.calcCheckValue(getBaseDerivationKey())));
                    label.setText("Key Check Value: " + checkValue.insert(6, "  ").toString().toUpperCase());
                    label.setTextFill(Color.RED);
                    if (!migPane.getChildren().contains(label)) {
                        migPane.add(migPane.getChildren().indexOf(derivTitle), label, "span, wrap");
                    }
                } else {
                    checkBox.setVisible(false);
                    checkBox.setSelected(false);
                    migPane.remove(checkBox);
                    migPane.remove(label);
                }
            }
        };
        comp1.resultTextField.addEventHandler(ValidationEvent.ANY, componentsOkEventHandler);
        comp2.resultTextField.addEventHandler(ValidationEvent.ANY, componentsOkEventHandler);
        comp3.resultTextField.addEventHandler(ValidationEvent.ANY, componentsOkEventHandler);

        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            Label label = new Label();
            ScrollPane scrollPane = new ScrollPane(label);

            {
                checkBox.visibleProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (!newValue1) migPane.remove(label);
                });
            }

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (comp1Ok && comp2Ok && comp3Ok) {
                        label.setTextFill(Color.RED);
                        label.setText(Hex.encodeHexString(getBaseDerivationKey()));
                        scrollPane.setPrefViewportWidth(310);
                        migPane.remove(scrollPane);
                        migPane.add(migPane.getChildren().indexOf(checkBox) + 1, scrollPane, new CC().wrap().spanX());
                    }
                } else {
                    migPane.remove(scrollPane);
                }
            }
        });

        saveButton.setOnAction(event -> {
            if (ValidationUtils.validateOnDemand(idField.getParent().getParent())) {
                try {
                    AvixyKeyConfiguration avixyKeyConfiguration = new AvixyKeyConfiguration();
                    avixyKeyConfiguration.setName(idField.getText());
                    byte[] kC1 = Hex.decodeHex((comp1.resultTextField.getText()).toCharArray());
                    byte[] kC2 = Hex.decodeHex((comp2.resultTextField.getText()).toCharArray());
                    byte[] kC3 = Hex.decodeHex((comp3.resultTextField.getText()).toCharArray());
                    avixyKeyConfiguration.setKeyComponents(kC1, kC2, kC3);
                    avixyKeyConfiguration.setAesConstants(kAes1Field.getValue(), kAes2Field.getValue());
                    avixyKeyConfiguration.setHmacConstants(kHmac1Field.getValue(), kHmac2Field.getValue());

                    AvixyKeyConfiguration.getConfigList().add(avixyKeyConfiguration);
                    AvixyKeyConfiguration.persist();
                    if (AvixyKeyConfiguration.getConfigList().size() == 1)
                        perfisTable.getSelectionModel().select(avixyKeyConfiguration);
                    List<TextField> form = Arrays.asList(
                            idField, kAes1Field, kAes2Field, kHmac1Field, kHmac2Field,
                            comp1.boundTF1, comp1.boundTF2, comp1.boundTF3, comp1.boundTF4, comp1.boundTF5, comp1.boundTF6, comp1.boundTF7, comp1.boundTF8,
                            comp2.boundTF1, comp2.boundTF2, comp2.boundTF3, comp2.boundTF4, comp2.boundTF5, comp2.boundTF6, comp2.boundTF7, comp2.boundTF8,
                            comp3.boundTF1, comp3.boundTF2, comp3.boundTF3, comp3.boundTF4, comp3.boundTF5, comp3.boundTF6, comp3.boundTF7, comp3.boundTF8
                    );
                    for (TextField textField : form) {
                        textField.setText("");
                        textField.fireEvent(new ValidationEvent(ValidationEvent.VALIDATION_UNKNOWN));
                    }
                    comp1.clearValidations();
                    comp2.clearValidations();
                    comp3.clearValidations();
                } catch (DecoderException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        /* setup table */
        ChavesTableUtil.setupTable(perfisTable);
        perfisTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            AvixyKeyConfiguration.select(newValue);
        });

        Label labelK1 = new Label("Check Value:");
        class Kc1EventHandler implements EventHandler<ValidationEvent> {
            {
                labelK1.setTextFill(Color.RED);
            }
            @Override
            public void handle(ValidationEvent event) {
                if (event.getEventType() == ValidationEvent.VALIDATION_OK) {
                    if (comp1Ok) {
                        try {
                            byte[] kC1 = Hex.decodeHex((comp1.resultTextField.getText()).toCharArray());
                            StringBuilder checkValue = new StringBuilder(Hex.encodeHexString(calcCheckValue(kC1)));
                            labelK1.setText("Check Value: " + checkValue.insert(6, "  ").toString().toUpperCase());
                            migPaneComp1.remove(labelK1);
                            migPaneComp1.add(migPaneComp1.getChildren().indexOf(comp1) + 1, labelK1, new CC().wrap().spanX());
                        } catch (DecoderException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    migPane.getChildren().remove(labelK1);
                }
            }
        }
        Label labelK2 = new Label("Check Value:");
        class Kc2EventHandler implements EventHandler<ValidationEvent> {
            {
                labelK2.setTextFill(Color.RED);
            }
            @Override
            public void handle(ValidationEvent event) {
                if (event.getEventType() == ValidationEvent.VALIDATION_OK) {
                    if (comp2Ok) {
                        try {
                            byte[] kC2 =  Hex.decodeHex((comp2.resultTextField.getText()).toCharArray());
                            StringBuilder checkValue = new StringBuilder(Hex.encodeHexString(calcCheckValue(kC2)));
                            labelK2.setText("Check Value: " + checkValue.insert(6, "  ").toString().toUpperCase());
                            migPaneComp2.remove(labelK2);
                            migPaneComp2.add(migPaneComp2.getChildren().indexOf(comp2) + 1, labelK2, new CC().wrap().spanX());
                        } catch (DecoderException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    migPane.getChildren().remove(labelK2);
                }
            }
        }
        Label labelK3 = new Label("Check Value:");
        class Kc3EventHandler implements EventHandler<ValidationEvent> {
            {
                labelK3.setTextFill(Color.RED);
            }
            @Override
            public void handle(ValidationEvent event) {
                if (event.getEventType() == ValidationEvent.VALIDATION_OK) {
                    if (comp3Ok) {
                        try {
                            byte[] kC3 =  Hex.decodeHex((comp3.resultTextField.getText()).toCharArray());
                            StringBuilder checkValue = new StringBuilder(Hex.encodeHexString(calcCheckValue(kC3)));
                            labelK3.setText("Check Value: " + checkValue.insert(6, "  ").toString().toUpperCase());
                            migPaneComp3.remove(labelK3);
                            migPaneComp3.add(migPaneComp3.getChildren().indexOf(comp3) + 1, labelK3, new CC().wrap().spanX());
                        } catch (DecoderException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    migPane.getChildren().remove(labelK3);
                }
            }
        }
        comp1.addEventHandler(ValidationEvent.ANY, new Kc1EventHandler());
        comp2.addEventHandler(ValidationEvent.ANY, new Kc2EventHandler());
        comp3.addEventHandler(ValidationEvent.ANY, new Kc3EventHandler());

        /* field set-up */
        comp3.setDefault("00000000");
    }

    private byte[] getBaseDerivationKey() {
        try {
            byte[] kC1 = Hex.decodeHex((comp1.resultTextField.getText()).toCharArray());
            byte[] kC2 =  Hex.decodeHex((comp2.resultTextField.getText()).toCharArray());
            byte[] kC3 =  Hex.decodeHex((comp3.resultTextField.getText()).toCharArray());
            byte[] baseDerivationKey = new byte[32];
            for (int i = 0; i < 32; i++) {
                baseDerivationKey[i] = (byte) (kC1[i] ^ kC2[i] ^ kC3[i]);
            }
            return baseDerivationKey;
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    static byte[] calcCheckValue(byte[] key) {
        try {
        AesKeyPolicy aesKeyPolicy = new AesKeyPolicy(new byte[16], true);
        aesKeyPolicy.setKey(key);
        byte[] message = new byte[16];
            return Arrays.copyOfRange(aesKeyPolicy.apply(message), 0, 6);
        } catch (CryptoException e) {
            throw new RuntimeException(e);
        }
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
            colunaSelect.setCellFactory(param1 -> new CheckBoxTableCell<>(param -> {
                return AvixyKeyConfiguration.getConfigList().get(param).selectedProperty();
            }));
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
