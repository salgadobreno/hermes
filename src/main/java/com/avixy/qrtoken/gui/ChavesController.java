package com.avixy.qrtoken.gui;

import com.avixy.qrtoken.core.HermesLogger;
import com.avixy.qrtoken.negocio.servico.crypto.KeyPolicy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 21/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ChavesController {

    @FXML private TableView<ChavesSingleton.Chave> chavesTable;
    @FXML private TextField idField;
    @FXML private TextField valorField;
    @FXML private ComboBox<String> algoComboBox;

    private List<String> algorythmList = new ArrayList<>();

    public void initialize(){

        for (KeyPolicy.KeyType keyType : KeyPolicy.KeyType.values()) {
            algorythmList.add(keyType.toString());
        }

        algoComboBox.setItems(FXCollections.observableList(algorythmList));

        ChavesTableUtil.setupTable(chavesTable);
    }

    public void save(){
        HermesLogger.get().info("idField.getText() = " + idField.getText());
        HermesLogger.get().info("algoComboBox.getValue() = " + algoComboBox.getValue());
        HermesLogger.get().info("valorField.getText() = " + valorField.getText());

        ChavesSingleton.addChave(new ChavesSingleton.Chave(idField.getText(), algoComboBox.getValue(), valorField.getText()));
    }
}
