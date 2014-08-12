package com.avixy.qrtoken.gui;

import com.avixy.qrtoken.core.ServicoLoader;
import com.avixy.qrtoken.gui.servicos.ServicoComponent;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

/**
 * Created on 04/08/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class HomeController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    ServicoComponent serviceController;

    QrCodePolicy policy = new QrCodePolicy();

    // Common fields
    @FXML private VBox qrDisplayVBox;
    @FXML private ImageView qrView;
    @FXML private Slider correctionLevelSlider;
    @FXML private ComboBox<Integer> qrVersionField = new ComboBox<>();
    @FXML private ComboBox<String> selectServiceField = new ComboBox<>();

    // Tab pane and servicos
//    @FXML private ComboBox<String> serviceComboBox;
    @FXML private Accordion servicesAccordion = new Accordion();

    // Map of the tabs and servicos
    Map<ServicoComponent.Category, List<Class<? extends ServicoComponent>>> serviceCategoryMap = ServicoLoader.getListServicos();
    Map<String, ServicoComponent> serviceNameMap = new HashMap<>();

    /**
     * initializes common fields
     */
    public void initialize(){
        for (ServicoComponent.Category category : serviceCategoryMap.keySet()) {
            // Add the tabs
            Tab tab = new Tab(category.toString());
            ListView<String> list = new ListView<>();
            ObservableList<String> items = FXCollections.observableArrayList("1", "2", "3", "4");
            list.setItems(items);
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(list);

            TitledPane titledPane = new TitledPane(category.toString(), anchorPane);
            servicesAccordion.getPanes().add(titledPane);
//            servicesAccordion.getTabs().add(tab);
        }
        List<String> rtcs = Lists.transform(serviceCategoryMap.get(ServicoComponent.Category.RTC), new Function<Class<? extends ServicoComponent>, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Class<? extends ServicoComponent> aClass) {
                try {
                    ServicoComponent servicoComponent = aClass.newInstance();
                    serviceNameMap.put(servicoComponent.getServiceName(), servicoComponent);
                    return servicoComponent.getServiceName();
                } catch (Exception e) { //FIXME
                    e.printStackTrace();
                    return "Erro";
                }
            }
        });
//        serviceComboBox.setItems(FXCollections.observableList(rtcs));

        // bind serviceComboBox to service
//        serviceComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
//                System.out.println(newValue);
//                ServicoComponent component = serviceNameMap.get(newValue);
//                initService(component);
//            }
//        });
    }

    /**
     * initializes a selected service
     */
    private void initService(ServicoComponent servicoComponent){
        // triggered when service combo is changed
        // recebe o serviço, carrega o 'partial' do serviço e seta o subcontroller

        try {
            // seta o controller p/ component
            fxmlLoader.setController(servicoComponent); //TODO: nomes ambiguos

            // carrega o node e adiciona na tab
            Node node = (Node) fxmlLoader.load(this.getClass().getResource(servicoComponent.getFxmlPath()).openStream());
            Pane parent = new Pane();
            parent.setTranslateY(30); // 30px p/ baixo por causa do combobox de serviço TODO: fix
            parent.setTranslateX(5);
            parent.getChildren().add(node);
//            servicesAccordion.getTabs().get(0).setContent(parent);

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
