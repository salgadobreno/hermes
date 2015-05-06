package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 21/08/2014
 */
public class ChavesController {
    private static Logger logger = LoggerFactory.getLogger(ChavesController.class);

    @FXML private Label errorLabel;
    @FXML private TableView<Chave> chavesTable;
    @FXML private TextField idField;
    @FXML private TextField valorField;
    @FXML private ComboBox<KeyType> tipoComboBox;
    @FXML private ComboBox<Integer> lengthComboBox;

    private Chave chave;
    private List<KeyType> keyTypeList = new ArrayList<>();

    private void initChave(){
        chave = new Chave();
        valorField.clear();
        chave.setKeyType(tipoComboBox.getValue());
        chave.setLength(lengthComboBox.getValue());
    }

    private void updateChave(){
        chave.setId(idField.getText());
        chave.setValor(valorField.getText());
        chave.setLength(lengthComboBox.getValue());
        chave.setKeyType(tipoComboBox.getValue());
    }

    public void initialize(){
        tipoComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, keyType, keyType2) -> {
            lengthComboBox.setItems(FXCollections.observableList(Arrays.asList(keyType2.getKeyLengths())));
            lengthComboBox.getSelectionModel().select(0);
        });
        Collections.addAll(keyTypeList, KeyType.values());
        tipoComboBox.setItems(FXCollections.observableList(keyTypeList));
        tipoComboBox.getSelectionModel().select(0);

        /* basic validation */
        valorField.textProperty().addListener((observableValue, s, s2) -> {
            updateChave();
            if (valorField.getText().isEmpty()) {
                valorField.styleProperty().setValue("");
            } else {
                if (!chave.isValid()) {
                    valorField.setStyle("-fx-background-color:red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                } else {
                    valorField.setStyle("-fx-background-color:green,linear-gradient(to bottom, derive(green,60%) 5%,derive(green,90%) 40%);");
                }
            }
        });

        initChave();
        ChavesTableUtil.setupTable(chavesTable);
    }

    public void save(){
        logger.info("idField.getText() = " + idField.getText());
        logger.info("tipoComboBox.getValue() = " + tipoComboBox.getValue());
        logger.info("valorField.getText() = " + valorField.getText());
        logger.info("valorField.getLength() = " + valorField.getLength());

        boolean success = ChavesSingleton.getInstance().add(chave);
        if (success) {
            initChave();
        } else {
            errorLabel.setText(chave.getErrors());
        }
    }

    /**
     * Util for setting up the {@link com.avixy.qrtoken.negocio.servico.chaves.Chave} table
     *
     * @author Breno Salgado <breno.salgado@avixy.com>
     *
     * Created on 20/08/2014
     */
    public static class ChavesTableUtil {
        private static ChavesSingleton chaves = ChavesSingleton.getInstance();

        public static void setupTable(final TableView<Chave> tabela) {

            /* colunas */
            TableColumn<Chave, String> colunaId = new TableColumn<>("Id");
            TableColumn<Chave, String> colunaTipo = new TableColumn<>("Tipo");
            TableColumn<Chave, String> colunaValor = new TableColumn<>("Valor");
            TableColumn<Chave, Chave> colunaDelete = new TableColumn<>("");

            /* Value factories */
            colunaId.setCellValueFactory(new PropertyValueFactory<Chave, String>("Id"));
            colunaTipo.setCellValueFactory(chaveStringCellDataFeatures -> new SimpleStringProperty(chaveStringCellDataFeatures.getValue().getDisplayName()));
            colunaValor.setCellValueFactory(new PropertyValueFactory<Chave, String>("Valor"));
            colunaDelete.setCellFactory(chaveBooleanTableColumn -> new ChaveDeleteCell());
            colunaDelete.setCellValueFactory(features -> new SimpleObjectProperty<Chave>(features.getValue()));

            /* widths */
            colunaId.prefWidthProperty().bind(tabela.widthProperty().multiply(0.3));
            colunaTipo.prefWidthProperty().bind(tabela.widthProperty().multiply(0.3));
            colunaValor.prefWidthProperty().bind(tabela.widthProperty().multiply(0.3));
            colunaDelete.prefWidthProperty().bind(tabela.widthProperty().multiply(0.1));

            tabela.itemsProperty().set(chaves.getObservableChaves());
            tabela.getColumns().addAll(colunaId, colunaTipo, colunaValor, colunaDelete);
        }

        /** Célula com botão para deletar uma <code>Chave</code>. */
        public static class ChaveDeleteCell extends TableCell<Chave, Chave> {
            private static ChavesSingleton chaves = ChavesSingleton.getInstance();
            final Button addButton       = new Button("x");
            final StackPane paddedButton = new StackPane();

            ChaveDeleteCell() {
                paddedButton.setPadding(new Insets(3));
                paddedButton.getChildren().add(addButton);
                addButton.setOnAction(actionEvent -> chaves.remove(getTableColumn().getCellData(getIndex())));
            }

            /** places a button in the row only if the row is not empty. */
            @Override
            protected void updateItem(Chave item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(paddedButton);
                }
            }
        }
    }
}
