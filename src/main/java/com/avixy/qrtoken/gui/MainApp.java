package com.avixy.qrtoken.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

        stage.setTitle("Hermes - Avixy QR Token");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.getIcons().add(new Image("/images/logo1.png"));
        stage.show();

        disableDividers(scene);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
            }
        });
    }

    // Hack para impedir que o usuário possa dar resize no splitpanel e quebre o layout
    private void disableDividers(final Scene container){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                container.lookup(".split-pane-divider").setMouseTransparent(true);
            }
        });
    }
}
