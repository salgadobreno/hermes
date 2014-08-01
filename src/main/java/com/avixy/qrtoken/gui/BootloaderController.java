package com.avixy.qrtoken.gui;

import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.qrcode.QrSlice;
import com.avixy.qrtoken.core.QrUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created on 08/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class BootloaderController {
    private Charset CHARSET = Charset.forName("ISO-8859-1");

    private QrCodePolicy policy = new QrCodePolicy();

    @FXML
    private VBox qrDisplayVBox;
    @FXML
    private ImageView qrView;
    @FXML
    private TextArea contentField;
    @FXML
    private Slider correctionLevelSlider;
    @FXML
    private ComboBox<Integer> qrVersionField = new ComboBox<Integer>();
    @FXML
    private TextField tTimerField;
    @FXML
    private TextField tPrimeiroQrField;
    @FXML
    private TitledPane manualInputTitledPane;

    @FXML
    private Label quantidadeQrsLabel;
    @FXML
    private Label capacidadeLabel;
    @FXML
    private Label bytesPorDadosLabel;
    @FXML
    private Label bytesPorEcLabel;

    // Custom properties
    private IntegerProperty capacidadeProperty = new SimpleIntegerProperty();
    private IntegerProperty bytesPorEcProperty = new SimpleIntegerProperty();
    private IntegerProperty bytesPorDadosProperty = new SimpleIntegerProperty();
    private IntegerProperty bytesPHeaderProperty = new SimpleIntegerProperty();
    private IntegerProperty quantidadeQrsProperty = new SimpleIntegerProperty();

    private IntegerProperty currentQrCodeProperty = new SimpleIntegerProperty(1);

    // Formato mostrado no app
    StringExpression capacidadeFormat = Bindings.format("Capacidade: %s", capacidadeProperty);
    StringExpression bytesPorEcFormat = Bindings.format("Bytes p/ EC: %s", bytesPorEcProperty);
    StringExpression bytesPorDadosFormat = Bindings.format("Bytes p/ Dados(header): (%s) + %s", bytesPHeaderProperty, bytesPorDadosProperty);
    StringExpression quantidadeQrsFormat = Bindings.format("%s/%s", currentQrCodeProperty, quantidadeQrsProperty);

    Timer timer;
    Boolean timerRunning = false;
    File fileToLoad;

    Stage zoomStage;

    public void initialize() throws InterruptedException {
        System.out.println("Initialize");

        // set default versao de qr p/ 1
        qrVersionField.setValue(1);

        // Bindar os labels ao formato de exibicao
        capacidadeLabel.textProperty().bind(capacidadeFormat);
        bytesPorDadosLabel.textProperty().bind(bytesPorDadosFormat);
        bytesPorEcLabel.textProperty().bind(bytesPorEcFormat);
        quantidadeQrsLabel.textProperty().bind(quantidadeQrsFormat);

        manualInputTitledPane.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean previousValue, Boolean newValue) {
                if (newValue) {
                    fileToLoad = null;
                    resetQrDisplay();
                }
            }
        });
        capacidadeProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return getSetup().getTotalBytes();
            }
        }, qrVersionField.valueProperty()));

        bytesPorEcProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return getSetup().getEcBytes();
            }
        }, qrVersionField.valueProperty(), correctionLevelSlider.valueProperty()));

        bytesPorDadosProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return getSetup().getUsableBytes();
            }
        }, qrVersionField.valueProperty(), correctionLevelSlider.valueProperty()));

        bytesPHeaderProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return policy.getHeaderSize();
            }
        }));

        quantidadeQrsProperty.bind(Bindings.createIntegerBinding(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return getSetup().getQrQuantity();
            }
        }, contentField.textProperty(), qrVersionField.valueProperty(), correctionLevelSlider.valueProperty()));

        // Setar os niveis de QR no combo
        List<Integer> levels = new ArrayList<Integer>();
        for (int i = 1; i <= 40; i++) {
            levels.add(i);
        }
        ObservableList<Integer> list = FXCollections.observableList(levels);
        qrVersionField.setItems(list);

        correctionLevelSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                if (qrView.getImage() != null && newValue.floatValue() % 1 == 0) {
                    resetQrDisplay();
                }
            }
        });

        qrVersionField.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer integer2) {
                resetQrDisplay();
            }
        });

        // Formatação do slider
        // correctionLevelSlider.setLabelFormatter(new StringConverter<Double>() { }); Bug no label formatter ->  https://javafx-jira.kenai.com/browse/RT-18448
    }

    public void nextQrCode() {
        if (currentQrCodeProperty.get() < quantidadeQrsProperty.get()) {
            currentQrCodeProperty.set(currentQrCodeProperty.get() + 1);
            gerarQr();
        }
    }

    public void previousQrCode() {
        if (currentQrCodeProperty.get() > 1) {
            currentQrCodeProperty.set(currentQrCodeProperty.get() - 1);
            gerarQr();
        }
    }

    public void gerarQr() {
        if (getContent().length < 1){ qrView.setImage(null); return; }
        byte[] content = getContent();
        QrSlice[] qrs = policy.getQrsFor(getSetup());

        QrSlice currQr = qrs[currentQrCodeProperty.get() - 1];
//        System.out.println("-> " + currQr.getDados());
//        System.out.println("--- length: " + currQr.getDados().length());
        Image image = new Image(QrUtils.generate(currQr.getDados(), getECLevel()));

        qrView.setImage(image);
    }

    public void resetQrDisplay() {
        currentQrCodeProperty.set(1);
        if (timer != null) {
            timer.cancel();
        }
        timerRunning = false;
        gerarQr();
    }

    public void sendFullMessage() {
        int timeBetweenTransitions = Integer.parseInt(tTimerField.getText());
        int firstDisplayTime = Integer.parseInt(tPrimeiroQrField.getText());
        if (timerRunning) {
            timer.cancel();
            timerRunning = false;
        } else {
            timer = new Timer();
            qrDisplayVBox.setStyle("-fx-border-color:'red'");
            gerarQr();
            timerRunning = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            qrDisplayVBox.setStyle("-fx-border-color:'gray'");
                            if (currentQrCodeProperty.get() < quantidadeQrsProperty.get()) {
                                currentQrCodeProperty.set(currentQrCodeProperty.get() + 1);
                                gerarQr();
                            } else {
                                timerRunning = false;
                                timer.cancel();
                                qrDisplayVBox.setStyle("-fx-border-color:'gray';-fx-border-style:dotted");
                                System.out.println("done.");
                            }
                        }
                    });
                }
            }, firstDisplayTime, timeBetweenTransitions);
        }
    }

    public Version getVersion(){
        return Version.getVersionForNumber(qrVersionField.getValue());
    }

    public byte[] getContent() {
        if (fileToLoad != null) {
            try {
                byte[] x =  Files.readAllBytes(fileToLoad.toPath());
                return Files.readAllBytes(fileToLoad.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return "".getBytes();
            }
        } else {
            String text = contentField.getText() == null ? "" : contentField.getText();
            return text.getBytes(CHARSET);
        }
    }

    public ErrorCorrectionLevel getECLevel() {
        Integer errorCorrectionLevel = ((Double) correctionLevelSlider.getValue()).intValue();
        return ErrorCorrectionLevel.values()[errorCorrectionLevel];
    }

    public void zoomInAndPlayQrs() {
        Parent root = qrView.getScene().getRoot();
        Window window = qrView.getScene().getWindow();
        if (zoomStage == null) {
            zoomStage = new Stage(StageStyle.UTILITY);
        }

        try {
            String fxmlFile = "/fxml/qrcodezoom.fxml";
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
            zoomStage.setX(window.getX() + window.getWidth());
            zoomStage.setY(window.getY());
            zoomStage.setResizable(false);
            zoomStage.setScene(new Scene(parent));
            zoomStage.show();
            zoomStage.toFront();
            final ImageView qrZoomImageView = (ImageView) parent.lookup("#qrZoomImageView");
            qrZoomImageView.imageProperty().bind(Bindings.createObjectBinding(new Callable<Image>() {
                @Override
                public Image call() throws Exception {
                    return qrView.getImage();
                }
            }, qrView.imageProperty()));
            final VBox vBox = (VBox) parent.lookup("#vbox");
            vBox.styleProperty().bind(qrDisplayVBox.styleProperty());
            sendFullMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() throws IOException {
        Window window = qrView.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        manualInputTitledPane.setExpanded(false);

        fileChooser.setTitle("Importar conteúdo de arquivo");
        fileToLoad = fileChooser.showOpenDialog(window);

        gerarQr();
    }

    public QrSetup getSetup(){
        return new QrSetup(policy, getVersion(), getECLevel(), getContent());
    }
}
