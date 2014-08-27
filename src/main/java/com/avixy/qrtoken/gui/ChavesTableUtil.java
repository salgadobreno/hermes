package com.avixy.qrtoken.gui;

import com.avixy.qrtoken.gui.ChavesSingleton.Chave;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 20/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ChavesTableUtil {

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
        //TODO

        tabela.itemsProperty().set(ChavesSingleton.getObservableChaves());
        tabela.getColumns().addAll(colunaId, colunaAlgo, colunaValor, colunaDelete);
    }
}

/** A table cell containing a button for adding a new person. */
class ChaveDeleteCell extends TableCell<Chave, Chave> {
    // a button for adding a new person.
    final Button addButton       = new Button("x");
    // pads and centers the add button in the cell.
    final StackPane paddedButton = new StackPane();

    /**
     * AddPersonCell constructor
     * @param stage the stage in which the table is placed.
     * @param table the table to which a new person can be added.
     */
    ChaveDeleteCell() {
        paddedButton.setPadding(new Insets(3));
        paddedButton.getChildren().add(addButton);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                ChavesSingleton.remove(getTableColumn().getCellData(getIndex()));
            }
        });
    }

    /** places an add button in the row only if the row is not empty. */
    @Override protected void updateItem(Chave item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(paddedButton);
        }
    }
}
