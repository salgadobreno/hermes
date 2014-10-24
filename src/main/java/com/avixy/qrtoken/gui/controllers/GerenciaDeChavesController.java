package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.negocio.servico.chaves.Chave;
import com.avixy.qrtoken.negocio.servico.chaves.ChavesSingleton;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import jfxtras.labs.scene.control.BeanPathAdapter;
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
public class GerenciaDeChavesController {
    private static Logger logger = LoggerFactory.getLogger(GerenciaDeChavesController.class);

    @FXML private Label errorLabel;
    @FXML private TableView<Chave> chavesTable;
    @FXML private TextField idField;
    @FXML private TextField valorField;
    @FXML private ComboBox<KeyType> algoComboBox;
    @FXML private ComboBox<Integer> lengthComboBox;

    private Chave chave = new Chave();

    private List<KeyType> algorythmList = new ArrayList<>();

    Integer[] keyLengths = {64, 128, 160, 192, 224, 256, 320, 384, 512, 1024, 2048, 4096};

    public void initialize(){
        chave = new Chave();
        chave.setKeyType(KeyType.values()[0]);
        chave.setLength(keyLengths[0]);
        Collections.addAll(algorythmList, KeyType.values());

        /* basic validation */
        valorField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                chave.setValor(s2); //TODO NOTE: setando no listener senão dá problema
                if (!chave.isValid()) {
                    valorField.setStyle("-fx-background-color:red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
                } else {
                    valorField.setStyle("-fx-background-color:green,linear-gradient(to bottom, derive(green,60%) 5%,derive(green,90%) 40%);");
                }
            }
        });

        algoComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<KeyType>() {
            @Override
            public void changed(ObservableValue<? extends KeyType> observableValue, KeyType keyType, KeyType keyType2) {
                lengthComboBox.setItems(FXCollections.observableList(Arrays.asList(keyType2.getKeyLengths())));
                lengthComboBox.getSelectionModel().select(0);
            }
        });


        BeanPathAdapter<Chave> chavePA = new BeanPathAdapter<>(chave);
//        chavePA.bindBidirectional("valor", valorField.textProperty()); //TODO NOTE: isso aqui deu problema só depois de compilar
        chavePA.bindBidirectional("id", idField.textProperty());
        chavePA.bindBidirectional("length", lengthComboBox.valueProperty(), Integer.class);
        chavePA.bindBidirectional("keyType", algoComboBox.valueProperty(), KeyType.class);

        algoComboBox.setItems(FXCollections.observableList(algorythmList));
        algoComboBox.getSelectionModel().select(0);

        ChavesTableUtil.setupTable(chavesTable);
    }

    public void save(){
        logger.info("idField.getText() = " + idField.getText());
        logger.info("algoComboBox.getValue() = " + algoComboBox.getValue());
        logger.info("valorField.getText() = " + valorField.getText());
        logger.info("valorField.getLength() = " + valorField.getLength());

        boolean success = ChavesSingleton.getInstance().add(chave);
        if (success) {
            chave = new Chave();
        } else {
            errorLabel.setText(chave.getErrors());
        }
    }

    /**
     * Helper p/ criação da tabela de chaves
     * @author Breno Salgado <breno.salgado@avixy.com>
     *
     * Created on 20/08/2014
     */
    public static class ChavesTableUtil {
        private static ChavesSingleton chaves = ChavesSingleton.getInstance();

        public static void setupTable(final TableView<Chave> tabela) {

            /* colunas */
            TableColumn<Chave, String> colunaId = new TableColumn<>("Id");
            TableColumn<Chave, String> colunaAlgo = new TableColumn<>("Algoritmo");
            TableColumn<Chave, String> colunaValor = new TableColumn<>("Valor");
            TableColumn<Chave, Chave> colunaDelete = new TableColumn<>("");

            /* Value factories */
            colunaId.setCellValueFactory(new PropertyValueFactory<Chave, String>("Id"));
            colunaAlgo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Chave, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Chave, String> chaveStringCellDataFeatures) {
                    return new SimpleStringProperty(chaveStringCellDataFeatures.getValue().getAlgoritmo().toString());
                }
            });
            colunaValor.setCellValueFactory(new PropertyValueFactory<Chave, String>("Valor"));
            colunaDelete.setCellFactory(new Callback<TableColumn<Chave, Chave>, TableCell<Chave, Chave>>() {
                @Override
                public TableCell<Chave, Chave> call(TableColumn<Chave, Chave> chaveBooleanTableColumn) {
                    return new ChaveDeleteCell();
                }
            });
            colunaDelete.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Chave, Chave>, ObservableValue<Chave>>() {
                @Override
                public ObservableValue<Chave> call(TableColumn.CellDataFeatures<Chave, Chave> features) {
                    return new SimpleObjectProperty<Chave>(features.getValue());
                }
            });

            /* widths */
            colunaId.prefWidthProperty().bind(tabela.widthProperty().multiply(0.3));
            colunaAlgo.prefWidthProperty().bind(tabela.widthProperty().multiply(0.3));
            colunaValor.prefWidthProperty().bind(tabela.widthProperty().multiply(0.3));
            colunaDelete.prefWidthProperty().bind(tabela.widthProperty().multiply(0.1));

            tabela.itemsProperty().set(chaves.getObservableChaves());
            tabela.getColumns().addAll(colunaId, colunaAlgo, colunaValor, colunaDelete);
        }

        /** Célula com botão para deletar uma <code>Chave</code>. */
        public static class ChaveDeleteCell extends TableCell<Chave, Chave> {
            private static ChavesSingleton chaves = ChavesSingleton.getInstance();
            final Button addButton       = new Button("x");
            final StackPane paddedButton = new StackPane();

            ChaveDeleteCell() {
                paddedButton.setPadding(new Insets(3));
                paddedButton.getChildren().add(addButton);
                addButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {
                        chaves.remove(getTableColumn().getCellData(getIndex()));
                    }
                });
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
