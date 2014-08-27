package com.avixy.qrtoken.gui;

import com.avixy.qrtoken.negocio.servico.crypto.KeyType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 21/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ChavesController {
    private static Logger logger = LoggerFactory.getLogger(ChavesController.class);

    @FXML private TableView<ChavesSingleton.Chave> chavesTable;
    @FXML private TextField idField;
    @FXML private TextField valorField;
    @FXML private ComboBox<KeyType> algoComboBox;

    private List<KeyType> algorythmList = new ArrayList<>();

    public void initialize(){

        for (KeyType keyType : KeyType.values()) {
            algorythmList.add(keyType);
        }

        algoComboBox.setItems(FXCollections.observableList(algorythmList));

        ChavesTableUtil.setupTable(chavesTable);
    }

    public void save(){
        logger.info("idField.getText() = " + idField.getText());
        logger.info("algoComboBox.getValue() = " + algoComboBox.getValue());
        logger.info("valorField.getText() = " + valorField.getText());

        ChavesSingleton.add(new ChavesSingleton.Chave(idField.getText(), algoComboBox.getValue(), valorField.getText()));
    }
}
