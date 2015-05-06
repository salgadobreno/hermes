package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.core.extensions.components.HexField;
import com.avixy.qrtoken.core.extensions.components.validators.JideSimpleValidator;
import com.avixy.qrtoken.core.extensions.components.validators.JideSizeValidator;
import com.avixy.qrtoken.negocio.servico.chaves.AvixyKeyConfiguration;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.StackPane;
import jidefx.scene.control.validation.ValidationMode;
import jidefx.scene.control.validation.ValidationUtils;
import org.controlsfx.validation.ValidationSupport;
import org.tbee.javafx.scene.layout.MigPane;

/**
 * Created on 27/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AvixyKeyConfigurationController extends MigPane {
    private ValidationSupport validationSupport = new ValidationSupport();

    @FXML
    private TextField idField;
    @FXML
    private HexField component1Field;
    @FXML
    private HexField component2Field;
    @FXML
    private HexField component3Field;

    @FXML
    private HexField kAes1Field;
    @FXML
    private HexField kAes2Field;
    @FXML
    private HexField kHmac1Field;
    @FXML
    private HexField kHmac2Field;

    @FXML
    private Button saveButton = new Button("Salvar");
    @FXML
    private TableView<AvixyKeyConfiguration> perfisTable;

    public void initialize() {
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
                if (AvixyKeyConfiguration.getConfigList().size() == 1) AvixyKeyConfiguration.select(avixyKeyConfiguration);
            }
        });
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
