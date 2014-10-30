package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.core.extensions.components.QrVersionSelect;
import com.avixy.qrtoken.negocio.qrcode.MultipleQrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrTokenCode;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.UpdateFirmwareService;
import com.google.inject.Inject;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.bouncycastle.crypto.CryptoException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

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

    /* fields */
    @FXML private QrVersionSelect qrVersionField = new QrVersionSelect();
    @FXML private TextField tTimerField;
    @FXML private TextField tPrimeiroQrField = new TextField();
    @FXML private TextField moduleOffsetField = new TextField();
    @FXML private TextField challengeField = new TextField();
    @FXML private TextField interruptionStuffField = new TextField();
    @FXML private Button loadFileButton;

    private Slider correctionLevelSlider; //grab from controller

    /* labels */
    @FXML private Label capacidadeLabel;
    @FXML private Label bytesPraDadosLabel;
    @FXML private Label bytesPraEcLabel;

    /* Custom properties */
    private IntegerProperty capacidadeProperty = new SimpleIntegerProperty();
    private IntegerProperty bytesPraEcProperty = new SimpleIntegerProperty();
    private IntegerProperty bytesPraDadosProperty = new SimpleIntegerProperty();
    private IntegerProperty quantidadeDeQrsProperty = new SimpleIntegerProperty();
    private IntegerProperty contentLengthProperty = new SimpleIntegerProperty(0);

    /* formato mostrado no app */
    private StringExpression capacidadeFormat = Bindings.format("Capacidade: %s", capacidadeProperty);
    private StringExpression bytesPraEcFormat = Bindings.format("Bytes p/ EC: %s", bytesPraEcProperty);
    private StringExpression bytesPraDadosFormat = Bindings.format("Bytes p/ Dados: %s", bytesPraDadosProperty);

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

                this.correctionLevelSlider = controller.getCorrectionLevelSlider();

                /* Binds formatos de exibicao */
                capacidadeLabel.textProperty().bind(capacidadeFormat);
                bytesPraDadosLabel.textProperty().bind(bytesPraDadosFormat);
                bytesPraEcLabel.textProperty().bind(bytesPraEcFormat);

                /* Binds dos custom properties */
                capacidadeProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return getSetup().getTotalBytes();
                    }
                }, qrVersionField.valueProperty()));

                bytesPraEcProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return getSetup().getEcBytes();
                    }
                }, qrVersionField.valueProperty(), correctionLevelSlider.valueProperty()));

                bytesPraDadosProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return getSetup().getAvailableBytes();
                    }
                }, qrVersionField.valueProperty(), correctionLevelSlider.valueProperty()));

                quantidadeDeQrsProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return ((Double)getSetup().getQrQtyFor(contentLengthProperty.getValue())).intValue();
                    }
                }, contentLengthProperty, qrVersionField.valueProperty(), correctionLevelSlider.valueProperty()));

                qrVersionField.valueProperty().addListener(new ChangeListener<Version>() {
                    @Override
                    public void changed(ObservableValue<? extends Version> observableValue, Version version, Version version2) {
                        controller.setVersion(version2);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                node = new VBox();
            }
        }
        return node;
    }

    @Override
    public Service getService() {
        service.setQrSetup(getSetup());
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

    @Override
    public List<QrTokenCode> getQrs(QrSetup setup) throws GeneralSecurityException, CryptoException {
        getService(); // para que os parâmetros sejam preenchidos
        List<QrTokenCode> qrTokenCodes = new ArrayList<>();
        qrTokenCodes.add(new QrTokenCode(service.getInitialQr(), setup));
        qrTokenCodes.addAll(qrCodePolicy.getQrs(service, setup));
        return qrTokenCodes;
    }

    @FXML
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

    @FXML
    public void rewind(){
        controller.rewindQrView();
    }

    @FXML
    public void loadFile() throws IOException {
        Window window = controller.getQrDisplayVBox().getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Importar conteúdo de arquivo");
        fileToLoad = fileChooser.showOpenDialog(window);
        loadFileButton.setText(fileToLoad.getName());
    }

    public QrSetup getSetup(){
        Version version = qrVersionField.getValue();
        ErrorCorrectionLevel ecLevel = ErrorCorrectionLevel.values()[((Double) correctionLevelSlider.getValue()).intValue()];
        return new QrSetup(version, ecLevel);
    }
}
