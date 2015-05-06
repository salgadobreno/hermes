package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.core.HermesModule;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.lib.AvixyKeyDerivator;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrTokenCode;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jidefx.scene.control.decoration.DecorationPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Breno Salgado <breno.salgado@axivy.com>
 *
 * Created on 04/08/2014.
 */
public class MainController {
    static final Logger log = LoggerFactory.getLogger(MainController.class);

    Injector injector = Guice.createInjector(new HermesModule());
    ServiceComponent serviceController;

    Stage zoomStage;
    Stage chavesStage;
    Stage encoderStage;
    Stage templateStage;
    Stage keyConfigStage;

    private Version qrVersion;

    @FXML private VBox root;
    @FXML private VBox qrDisplayVBox;
    @FXML private ImageView qrView;
    @FXML private Slider correctionLevelSlider;
    @FXML private AnchorPane content;
    @FXML private Accordion servicesAccordion;
    @FXML private Label errorLabel;
//    @FXML private ComboBox<AvixyKeyConfiguration> profileSelector;

    /* Controles e indicadores de QR */
    private List<QrTokenCode> qrCodes = new ArrayList<>(); // lista dos codigos qr
    @FXML private Label qtdQrsLabel;

    private IntegerProperty currentQrCodeProperty = new SimpleIntegerProperty(0);
    private IntegerProperty qtdQrsProperty = new SimpleIntegerProperty();
    private StringExpression qtdQrsFormat = Bindings.format("%s/%s", currentQrCodeProperty, qtdQrsProperty);

    /* Mapa de categorias e lista de componentes */
    private Map<ServiceCategory, List<Class<? extends ServiceComponent>>> serviceCategoryMap = com.avixy.qrtoken.core.ServiceLoader.getServiceComponentMap();
    /* Mapa do nome do serviço e instância do componente */
    private Map<String, ServiceComponent> serviceNameMap = new HashMap<>();

    /* Manter a lista de ListViews p/ limpar seleções */
    List<ListView> listViewList = new ArrayList<>();
    ListView current;

    public void initialize(){
        /* Controles e indicadores de QRs */
        {
            qtdQrsLabel.setStyle("-fx-alignment:CENTER");
            qtdQrsLabel.textProperty().bind(qtdQrsFormat);
            currentQrCodeProperty.addListener(new ChangeListener<Number>() { // extrair?
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number value2) {
                    Integer newValue = (Integer) value2;
                    if (newValue > 0) {
                        qrView.setImage(new Image(qrCodes.get(newValue - 1).image()));
                    } else {
                        qrView.setImage(null);
                    }
                }
            });
        }
        // Carrega lista de serviços
        for (ServiceCategory category : serviceCategoryMap.keySet()) {
            List<String> servicoForCategoryListNames = new ArrayList<>();
            // Add the list of services
            for (Class<? extends ServiceComponent> component : serviceCategoryMap.get(category)) {
                //, th store <serviceName, component>
                ServiceComponent serviceComponent = injector.getInstance(component);
                serviceComponent.setController(this); // Correto?
                servicoForCategoryListNames.add(serviceComponent.getServiceName());
                serviceNameMap.put(serviceComponent.getServiceName(), serviceComponent);
            }
            // Cria ListViews de serviços
            VBox vBox = new VBox(0);
            vBox.setPadding(Insets.EMPTY);
            final ListView<String> listView = new ListView<>();
            listView.setId("listView");
            ObservableList<String> items = FXCollections.observableList(servicoForCategoryListNames);
            listView.setItems(items);
            vBox.getChildren().add(listView);
            // Adiciona o callback de seleção
            listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if (newValue != null) {
                        ServiceComponent component = serviceNameMap.get(newValue);
                        serviceController = component;
                        log.info("component = " + component);
                        initService(component);
                    }
                }
            });
            listViewList.add(listView);

            // Adiciona a categoria
            TitledPane titledPane = new TitledPane(category.toString(), vBox);
            servicesAccordion.getPanes().add(titledPane);
        }
    }

    /**
     * Triggered when a service is selected
     * Loads the selected service
     */
    private void initService(ServiceComponent serviceComponent){
        resetQrView();
        content.getChildren().clear();
        content.getChildren().add(serviceComponent.getNode());

        //limpar seleção das outras listas
        current = (ListView) servicesAccordion.getExpandedPane().lookup("#listView");

        for (ListView listView : listViewList) {
            if (current != listView) {
                listView.getSelectionModel().clearSelection();
            }
        }
    }

    public void resetQrView() {
        currentQrCodeProperty.setValue(0);
        qtdQrsProperty.set(0);
        qrCodes.clear();
    }

    public void rewindQrView(){
        currentQrCodeProperty.setValue(1);
    }

    /**
     * runs service/updates qr
     */
    public void gerarQr() {
        try {
            resetQrView();

            qrCodes = serviceController.getQrs(getSetup());

            qtdQrsProperty.setValue(qrCodes.size());
            currentQrCodeProperty.setValue(1);
            errorLabel.setText(null);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * mounts the composite object
     */
    public QrSetup getSetup(){
        return new QrSetup(qrVersion, getECLevel());
    }

    public void setVersion(Version version){
        qrVersion = version;
    }

    public ErrorCorrectionLevel getECLevel(){
        Integer ecLevel = ((Double) correctionLevelSlider.getValue()).intValue();
        return ErrorCorrectionLevel.values()[ecLevel];
    }

    /**
     * Tratamento de erros custom e inesperados
     */
    private void handleException(Exception e) {
        if (e instanceof AvixyKeyDerivator.AvixyKeyNotConfigured) {
            try {
                keyConfiguration();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Chave Avixy não foi configurada");
            alert.showAndWait();
            return;
        }

        log.error("Error: ", e);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
    }

    public void nextQrCode() throws GeneralSecurityException {
        if (currentQrCodeProperty.get() < qtdQrsProperty.get()) {
            currentQrCodeProperty.set(currentQrCodeProperty.get() + 1);
        }
    }

    public void previousQrCode() throws GeneralSecurityException {
        if (currentQrCodeProperty.get() > 1) {
            currentQrCodeProperty.set(currentQrCodeProperty.get() - 1);
        }
    }

    public VBox getQrDisplayVBox() { return qrDisplayVBox; }

    public Slider getCorrectionLevelSlider() { return correctionLevelSlider; }

    public Boolean isLastQr(){
        return currentQrCodeProperty.get() == qtdQrsProperty.get();
    }

    @FXML
    public void handleKeyInput(final KeyEvent keyInput) throws GeneralSecurityException {
        switch (keyInput.getCode()) {
            case LEFT:
                previousQrCode();
                break;
            case RIGHT:
                nextQrCode();
                break;
        }
    }

    @FXML
    public void gerenciaDeChaves() throws IOException {
        // se chavesStage for nulo, pega o stage de ChavesStage
        // -> mostra o chavesStage

        if (chavesStage == null) {
            chavesStage = new Stage(StageStyle.DECORATED);
        }

        //carrega o fxml
        try {
            String fxmlFile = "/fxml/chaves.fxml";
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
            chavesStage.setResizable(false);
            chavesStage.setScene(new Scene(parent));

            chavesStage.show();
            chavesStage.toFront();
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
    }

    @FXML
    public void templateEditor() throws IOException {
        if (templateStage == null) {
            templateStage = new Stage(StageStyle.DECORATED);
        }

        String fxmlFile = "/fxml/templates.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));

        Scene scene = new Scene(root);

        templateStage.setTitle("Hermes - Avixy QR Token");
        templateStage.setScene(scene);
        templateStage.setResizable(false);
        templateStage.centerOnScreen();
        templateStage.show();
    }

    @FXML
    public void keyConfiguration() throws IOException {
        if (keyConfigStage == null) {
            keyConfigStage = new Stage();
            keyConfigStage.setResizable(false);
            String fxmlFile = "/fxml/kAvixyConfig.fxml";
            keyConfigStage.setScene(new Scene(new DecorationPane(FXMLLoader.load(getClass().getResource(fxmlFile)))));
        }
        keyConfigStage.show();
        keyConfigStage.toFront();
//        AvixyKeyConfiguration.getSelectedProfileProperty().addListener(new ChangeListener<AvixyKeyConfiguration>() {
//            @Override
//            public void changed(ObservableValue<? extends AvixyKeyConfiguration> observable, AvixyKeyConfiguration oldValue, AvixyKeyConfiguration newValue) {
//                profileSelector.getSelectionModel().select(newValue);
//            }
//        });
    }

    @FXML
    public void zoomQR() throws IOException {
        if (zoomStage == null) {
            zoomStage = new Stage(StageStyle.UTILITY);
            zoomStage.setResizable(false);
            String fxmlFile = "/fxml/qrcodezoom.fxml";
            zoomStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxmlFile))));
            final ImageView qrZoomImageView = (ImageView) zoomStage.getScene().lookup("#qrZoomImageView");
            qrZoomImageView.imageProperty().bind(Bindings.createObjectBinding(() -> qrView.getImage(), qrView.imageProperty()));
            final VBox vBox = (VBox) zoomStage.getScene().lookup("#vbox");
            vBox.styleProperty().bind(qrDisplayVBox.styleProperty());
        }
        zoomStage.show();
        zoomStage.toFront();
    }

    @FXML
    public void huffmanEncoder() throws IOException {
        if (encoderStage == null) {
            encoderStage = new Stage(StageStyle.DECORATED);
            String fxmlFile = "/fxml/huffmanEncoder.fxml";
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(parent);
            encoderStage.setResizable(false);
            encoderStage.setScene(scene);
        }
        encoderStage.show();
        encoderStage.toFront();
    }
}
