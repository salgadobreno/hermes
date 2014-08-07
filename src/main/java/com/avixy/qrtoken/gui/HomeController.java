package com.avixy.qrtoken.gui;

import com.avixy.qrtoken.gui.services.RtcServiceController;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.RtcService;
import com.avixy.qrtoken.negocio.servico.Service;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created on 04/08/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class HomeController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    Object serviceController;

    Service service = new RtcService();

    QrCodePolicy policy = new QrCodePolicy();

    // Common fields
    @FXML private VBox qrDisplayVBox;
    @FXML private ImageView qrView;
    @FXML private Slider correctionLevelSlider;
    @FXML private ComboBox<Integer> qrVersionField = new ComboBox<>();
    @FXML private ComboBox<String> selectServiceField = new ComboBox<>();

    // Tab pane and services
    @FXML private ComboBox<String> serviceComboBox;
    @FXML private TabPane serviceTabPane;

    List<String> tabs = new ArrayList<>();
    Map<String, String> servicesMap = new HashMap<>(); // TODO: service component

    // Properties

    /**
     * initializes common fields
     */
    public void initialize(){
        tabs.add("RTC");
        tabs.add("Chaves");
        for (String tab : tabs) {
            serviceTabPane.getTabs().add(new Tab(tab));
        }
        servicesMap.put("Atualizar RTC - Avixy com HMAC", RtcServiceController.getFxmlPath());

        List<String> keyList = new ArrayList<>(servicesMap.keySet());
        serviceComboBox.setItems(FXCollections.observableList(keyList));
        // bind serviceComboBox to service
        serviceComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                System.out.println(newValue);
                initService(servicesMap.get(newValue));
            }
        });

        // Initialize services
    }

    /**
     * initializes a selected service
     */
    private void initService(String fxml){
        // triggered when service combo is changed
        // recebe o serviço, carrega o 'partial' do serviço e seta o subcontroller
        try {
            Node node = (Node) fxmlLoader.load(this.getClass().getResource(fxml).openStream());
            Pane parent = new Pane();
            parent.setTranslateY(30); // 30px p/ baixo por causa do combobox de serviço
            parent.setTranslateX(5);
            parent.getChildren().add(node);

            serviceTabPane.getTabs().get(0).setContent(parent);
            serviceController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * runs service/updates qr
     */
    public void gerarQr(){
        Service service1 = ((RtcServiceController)serviceController).getService();
        InputStream qrCode = policy.getQr(service, getSetup());
        qrView.setImage(new Image(qrCode));
    }

    /**
     * mounts the composite object
     */
    public QrSetup getSetup(){
        return new QrSetup(getVersion(), getECLevel());
    }

    private Version getVersion() {
        return Version.getVersionForNumber(qrVersionField.getValue());
    }

    private ErrorCorrectionLevel getECLevel(){
        Integer ecLevel = ((Double) correctionLevelSlider.getValue()).intValue();
        return ErrorCorrectionLevel.values()[ecLevel];
    }

}
