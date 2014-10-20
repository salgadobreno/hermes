package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.qrcode.MultipleQrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.UpdateFirmwareService;
import com.google.inject.Inject;
import javafx.application.Platform;
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
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created on 14/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category
public class UpdateFirmwareServiceComponent extends ServiceComponent {
    private static String FXML_PATH = "/fxml/fwUpdateComponent.fxml";
    private UpdateFirmwareService service;

    private Node node;

    private Timer timer;
    private Boolean timerRunning = false;
    private File fileToLoad;

    //fields
    @FXML private ComboBox<Integer> qrVersionField = new ComboBox<Integer>();
    @FXML private TextField tTimerField;
    @FXML private TextField tPrimeiroQrField = new TextField();
    @FXML private TextField moduleOffsetField = new TextField();
    @FXML private TextField challengeField = new TextField();
    @FXML private TextField interruptionStuffField = new TextField();

    //labels
//    @FXML private Label capacidadeLabel;
//    @FXML private Label bytesPraDadosLabel;
//    @FXML private Label bytesPraEcLabel;

    // Custom properties
//    private IntegerProperty capacidadeProperty = new SimpleIntegerProperty();
//    private IntegerProperty bytesPraEcProperty = new SimpleIntegerProperty();
//    private IntegerProperty bytesPraDadosProperty = new SimpleIntegerProperty();
//    private IntegerProperty bytesPraHeaderProperty = new SimpleIntegerProperty();
    private IntegerProperty quantidadeDeQrsProperty = new SimpleIntegerProperty();
    private IntegerProperty currentQrCodeProperty = new SimpleIntegerProperty(1);

    //formatação
//    private StringExpression capacidadeFormat = Bindings.format("Capacidade: %s", capacidadeProperty);
//    private StringExpression bytesPraEcFormat = Bindings.format("Bytes p/ EC: %s", bytesPraEcProperty);
//    private StringExpression bytesPraDadosFormat = Bindings.format("Bytes p/ Dados(header: (%s) + %s", bytesPraHeaderProperty, bytesPraDadosProperty);
    private StringExpression quantidadeQrsFormat = Bindings.format("%s/%s", currentQrCodeProperty, quantidadeDeQrsProperty);


    @Inject
    protected UpdateFirmwareServiceComponent(final UpdateFirmwareService service, MultipleQrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
        this.service = service;
    }

    @Override
    public Node getNode() {
        if (node == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
            loader.setController(this);
            try {
                node = (Node) loader.load();
                // Bindar os labels ao formato de exibicao
//                capacidadeLabel.textProperty().bind(capacidadeFormat);
//                bytesPraDadosLabel.textProperty().bind(bytesPraDadosFormat);
//                bytesPraEcLabel.textProperty().bind(bytesPraEcFormat);

//                capacidadeProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return setup.getTotalBytes();
//                    }
//                }, qrVersionField.valueProperty()));
//                bytesPraEcProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return setup.getEcBytes();
//                    }
//                }, qrVersionField.valueProperty()));
//                bytesPraDadosProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return null;
//                    }
//                }, qrVersionField.valueProperty()));

//                bytesPraHeaderProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return new QrtHeaderPolicy().getHeader(service).length; // crap
//                    }
//                }));

//                quantidadeDeQrsProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return null; // crap
//                    }
//                }));

                // Setar os niveis de QR no combo
                List<Integer> levels = new ArrayList<Integer>();
                for (int i = 2; i <= 40; i++) {
                    levels.add(i);
                }
                ObservableList<Integer> list = FXCollections.observableList(levels);
                qrVersionField.setItems(list);
                qrVersionField.valueProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer integer2) {
                        controller.setVersion(integer2);
                    }
                });
                qrVersionField.getSelectionModel().select(0);
            } catch (IOException e) {
                e.printStackTrace();
                node = new VBox();
            }
        }
        return node;
    }

    @Override
    public Service getService() {
        QrSetup setup = controller.getSetup();

        service.setQrSetup(setup);
        service.setModuleOffset((byte) Integer.parseInt(moduleOffsetField.getText()));
        service.setChallenge(challengeField.getText());
        service.setInterruptionStuff((byte) Integer.parseInt(interruptionStuffField.getText()));
        try {
            service.setContent(Files.readAllBytes(fileToLoad.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return service;
    }

    public void zoomAndPlayQrs() {
        controller.zoomQR();

        final VBox qrDisplayVBox = controller.getQrDisplayVBox();
        int timeBetweenTransitions = Integer.parseInt(tTimerField.getText());
        int firstDisplayTime = Integer.parseInt(tPrimeiroQrField.getText());
        if (timerRunning) {
            timer.cancel();
            timerRunning = false;
        } else {
            timer = new Timer();
            qrDisplayVBox.setStyle("-fx-border-color:'red'");
            controller.gerarQr();
            timerRunning = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            qrDisplayVBox.setStyle("-fx-border-color:'gray'");
                            if (controller.isLastQr()) {
                                timerRunning = false;
                                timer.cancel();
                                qrDisplayVBox.setStyle("-fx-border-color:'gray';-fx-border-style:dotted");
                            } else {
                                try {
                                    controller.nextQrCode();
                                } catch (GeneralSecurityException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }, firstDisplayTime, timeBetweenTransitions);
        }
    }

    public void rewind(){
        controller.rewindQrView();
    }

    public void loadFile() throws IOException {
        Window window = controller.getQrDisplayVBox().getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Importar conteúdo de arquivo");
        fileToLoad = fileChooser.showOpenDialog(window);
    }
}
