package com.avixy.qrtoken.gui;

import com.avixy.qrtoken.gui.ChavesSingleton.Chave;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created on 20/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ChavesTableUtil {
    private static String FXML_PATH = "/fxml/chaves.fxml";

    public static void setupTable(TableView<Chave> tabela) {
        TableColumn<Chave, String> colunaId = new TableColumn<>("Id");
        TableColumn<Chave, String> colunaAlgo = new TableColumn<>("Algoritmo");
        TableColumn<Chave, String> colunaValor = new TableColumn<>("Valor");
        colunaId.setCellValueFactory(new PropertyValueFactory<Chave, String>("Id"));
        colunaAlgo.setCellValueFactory(new PropertyValueFactory<Chave, String>("Algoritmo"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<Chave, String>("Valor"));

        tabela.itemsProperty().set(ChavesSingleton.getObservableChaves());
        tabela.getColumns().addAll(colunaId, colunaAlgo, colunaValor);
    }

}
