package com.avixy.qrtoken.gui;

import com.avixy.qrtoken.gui.services.RtcServiceComponent;
import com.avixy.qrtoken.gui.services.ServiceComponent;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.Service;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

    ServiceComponent serviceController;

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

    // Map of the tabs and services
    Map<String, Map<String, ServiceComponent>> servicesMap = new HashMap<>();
    List<String> servicesNames = new ArrayList<>();

    /**
     * initializes common fields
     */
    public void initialize(){
        Map<String, ServiceComponent> rtcServicesMap = new HashMap<>();
        rtcServicesMap.put(new RtcServiceComponent().getServiceName(), new RtcServiceComponent()); //TODO: improve this

        final String[] tabs = {"RTC", "Chaves"};

        servicesMap.put(tabs[0], rtcServicesMap);
        for (String tab : tabs) {
            serviceTabPane.getTabs().add(new Tab(tab));
        }

        serviceComboBox.setItems(FXCollections.observableList(new ArrayList<String>(rtcServicesMap.keySet())));
        // bind serviceComboBox to service
        serviceComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                System.out.println(newValue);
                ServiceComponent component = null;
                for (Map<String, ServiceComponent> serviceComponentMap : servicesMap.values()) {
                    component = serviceComponentMap.get(newValue);
                    if (component != null) { break; }
                }
                initService(component);
            }
        });
    }

    /**
     * initializes a selected service
     */
    private void initService(ServiceComponent serviceComponent){
        // triggered when service combo is changed
        // recebe o serviço, carrega o 'partial' do serviço e seta o subcontroller
        try {
            // seta o controller p/ component
            fxmlLoader.setController(serviceComponent); //TODO: nomes ambiguos

            // carrega o node e adiciona na tab
            Node node = (Node) fxmlLoader.load(this.getClass().getResource(serviceComponent.getFxmlPath()).openStream());
            Pane parent = new Pane();
            parent.setTranslateY(30); // 30px p/ baixo por causa do combobox de serviço TODO: fix
            parent.setTranslateX(5);
            parent.getChildren().add(node);
            serviceTabPane.getTabs().get(0).setContent(parent);

            //sub controller eh o component
            serviceController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * runs service/updates qr
     */
    public void gerarQr(){
        //TODO: implementação com lista
        QrCodePolicy.QrTokenCode qrCode = policy.getQrs(serviceController.getService(), getSetup()).get(0);
        qrView.setImage(new Image(qrCode.image()));
    }

    /**
     * mounts the composite object
     */
    public QrSetup getSetup(){
        return new QrSetup(getVersion(), getECLevel());
    }

    private Version getVersion() {
        return Version.getVersionForNumber(6);
//        return Version.getVersionForNumber(qrVersionField.getValue());
    }

    private ErrorCorrectionLevel getECLevel(){
        Integer ecLevel = ((Double) correctionLevelSlider.getValue()).intValue();
        return ErrorCorrectionLevel.values()[ecLevel];
    }

}
