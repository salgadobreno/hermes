package com.avixy.qrtoken.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Breno Salgado <breno.salgado@axivy.com>
 *
 * Created on 08/07/2014.
 */
public class MainApp extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/index.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));

        Scene scene = new Scene(root);

        stage.setTitle("Hermes - Avixy QR Player");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.getIcons().add(new Image("/images/logo1.png"));
        stage.show();
    }
}
