package com.avixy.qrtoken.gui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 20/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ChavesTableUtil {
    private static String FXML_PATH = "/fxml/chaves.fxml";

    public static Stage getStage() throws IOException {
//        Parent parent = FXMLLoader.load(ChavesStage.class.getClass().getResource(FXML_PATH));
//        TableView<Chave> chavesTable = (TableView<Chave>) parent.lookup("chavesTable");
//        setupTable(chavesTable);
//        stage.setScene(new Scene(parent));
//        stage.setResizable(false);
        VBox parent = new VBox();
        Stage stage = new Stage(StageStyle.UTILITY);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setResizable(false);

        TableView<Chave> chaveTableView = new TableView<>();
        setupTable(chaveTableView);

        parent.getChildren().add(chaveTableView);
        //include add part

        return stage;
    }

    public static void setupTable(TableView<Chave> tabela) {
        List<Chave> chaves = new ArrayList<>();
        Chave chave = new Chave("avixy hmac", "SHA-1", "8888888888888888");
        chaves.add(chave);

//        TableView<Chave> tabela = new TableView<>();
        TableColumn<Chave, String> colunaId = new TableColumn<>("Id");
        TableColumn<Chave, String> colunaAlgo = new TableColumn<>("Algoritmo");
        TableColumn<Chave, String> colunaValor = new TableColumn<>("Valor");
        colunaId.setCellValueFactory(new PropertyValueFactory<Chave, String>("Id"));
        colunaAlgo.setCellValueFactory(new PropertyValueFactory<Chave, String>("Algoritmo"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<Chave, String>("Valor"));

        tabela.setItems(FXCollections.observableArrayList(chaves));
        tabela.getColumns().addAll(colunaId, colunaAlgo, colunaValor);
    }

    public static class Chave {

        private String id;
        private String algoritmo;
        private String valor;

        public Chave(String id, String algoritmo, String valor) {
            this.id = id;
            this.algoritmo = algoritmo;
            this.valor = valor;
        }

        public String getId() {
            return id;
        }

        public void setId(String nome) {
            this.id = nome;
        }

        public String getAlgoritmo() {
            return algoritmo;
        }

        public void setAlgoritmo(String idade) {
            this.algoritmo = idade;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String email) {
            this.valor = email;
        }

    }

}
