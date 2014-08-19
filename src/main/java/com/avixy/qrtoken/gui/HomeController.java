package com.avixy.qrtoken.gui;

import com.avixy.qrtoken.gui.servicos.ServiceComponent;
import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created on 04/08/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class HomeController {
    /** TODO:
     * combo de version?
     * bind version e ec level na geracao
     */
    static final Logger log = LoggerFactory.getLogger(HomeController.class);

    ServiceComponent serviceController;

    QrCodePolicy policy = new QrCodePolicy();

    Stage zoomStage;
    Stage chavesStage;

    @FXML private VBox qrDisplayVBox;
    @FXML private ImageView qrView;
    @FXML private Slider correctionLevelSlider;
    @FXML private ComboBox<Integer> qrVersionField = new ComboBox<>();
    @FXML private AnchorPane content;
    @FXML private Accordion servicesAccordion;
    @FXML private Label errorLabel;

    // Mapa de categorias e lista de componentes
    private Map<ServiceComponent.Category, List<Class<? extends ServiceComponent>>> serviceCategoryMap = com.avixy.qrtoken.core.ServiceLoader.getServiceComponentMap();
    // Mapa do nome do serviço e instância do componente
    private Map<String, ServiceComponent> serviceNameMap = new HashMap<>();

    // Manter a lista de ListViews p/ limpar seleções
    List<ListView> listViewList = new ArrayList<>();
    ListView current;


    public void initialize(){
        // Carrega lista de serviços
        for (ServiceComponent.Category category : serviceCategoryMap.keySet()) {
            List<String> servicoForCategoryListNames = new ArrayList<>();
            // Add the list of services
            for (Class<? extends ServiceComponent> component : serviceCategoryMap.get(category)) {
                try {
                    // store <serviceName, component>
                    ServiceComponent serviceComponent = component.newInstance();
                    servicoForCategoryListNames.add(serviceComponent.getServiceName());
                    serviceNameMap.put(serviceComponent.getServiceName(), serviceComponent);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("Erro instanciando {}:", component.getClass(), e);
                }
            }
            // Cria ListViews de serviços
            AnchorPane anchorPane = new AnchorPane();
            ListView<String> list = new ListView<>();
            list.setId("listView");
            ObservableList<String> items = FXCollections.observableList(servicoForCategoryListNames);
            list.setItems(items);
            anchorPane.getChildren().add(list);
            // Adiciona o callback de seleção
            list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
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
            listViewList.add(list);

            // Adiciona a categoria
            TitledPane titledPane = new TitledPane(category.toString(), anchorPane);
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

    private void resetQrView() {
        qrView.setImage(null);
    }

    /**
     * runs service/updates qr
     */
    public void gerarQr() throws GeneralSecurityException {
        //TODO: implementação com lista
        // if getqrs > 1
        //   show setas
        //   show indicador 1/3
        try {
            QrCodePolicy.QrTokenCode qrCode = policy.getQrs(serviceController.getService(), getSetup()).get(0); //FIXME
            qrView.setImage(new Image(qrCode.image()));
            errorLabel.setText(null);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * mounts the composite object
     */
    public QrSetup getSetup(){
        return new QrSetup(getVersion(), getECLevel());
    }

    private void handleException(Exception e) {
        errorLabel.setText(e.getClass().toString());
        log.error("Error: ", e);
    }

    private Version getVersion() {
        //TODO: combo de version
        return Version.getVersionForNumber(6);
//        return Version.getVersionForNumber(qrVersionField.getValue());
    }

    private ErrorCorrectionLevel getECLevel(){
        Integer ecLevel = ((Double) correctionLevelSlider.getValue()).intValue();
        return ErrorCorrectionLevel.values()[ecLevel];
    }

    public void gerirChaves() {
        Parent root = qrView.getScene().getRoot();
        Window window = qrView.getScene().getWindow();
        if (chavesStage == null) {
            chavesStage = new Stage(StageStyle.UTILITY);
        }
        final VBox vBox = new VBox();
        TableView tableView = new TableView();
        chavesStage.setTitle("Gerência de chaves");
        chavesStage.setResizable(false);
        chavesStage.setScene(new Scene(vBox));

        TableColumn<Map, String> column = new TableColumn<>("ID");
        column.setcel





        chavesStage.show();
        chavesStage.toFront();
    }
}
