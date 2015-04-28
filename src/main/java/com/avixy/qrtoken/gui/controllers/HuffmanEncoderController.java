package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.negocio.lib.TokenHuffman;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Created on 23/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HuffmanEncoderController {
    @FXML private TextArea textoField;
    @FXML private TextArea huffmanField;
    @FXML private Button encodeButton;
    @FXML private AnchorPane root;

    public void initialize(){
        final KeyCombination shortcut = new KeyCodeCombination(KeyCode.ENTER, KeyCodeCombination.CONTROL_ANY);
        root.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (shortcut.match(keyEvent)){
                    encodeButton.fire();
                }
            }
        });
    }

    public void encode(){
        huffmanField.setText(new TokenHuffman(TokenHuffman.Mode.SPLIT).encode(textoField.getText()));
    }
}
