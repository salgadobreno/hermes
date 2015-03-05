package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.core.extensions.components.NumberField;
import com.avixy.qrtoken.core.extensions.components.TemplateColorPicker;
import com.avixy.qrtoken.negocio.template.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.PopOver;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created on 12/02/2015
 *
 * @author I7
 */
public class TemplatesController extends Application {
    @FXML public CheckBox showGrid;
    @FXML private Button rect;
    @FXML private Button footer;
    @FXML private Button stripe;
    @FXML private Button header;
    @FXML private Button text;
    @FXML private Button clearButton;
    @FXML private Button saveButton;

    @FXML private TokenCanvas canvas;

    @FXML private Label xyLabel;

    @FXML private ListView<Template> listView;

    TemplatesSingleton templatesSingleton = TemplatesSingleton.getInstance();

    private PopOver popOver;
    
    private MigPane headerForm;
    private MigPane footerForm;
    private MigPane rectForm;
    private MigPane textForm;
    private MigPane stripeForm;

    //headerForm
    {
        headerForm = new MigPane();
        TemplateColorPicker bgColorPicker = new TemplateColorPicker();
        TemplateColorPicker textColorPicker = new TemplateColorPicker();
        TextField textField = new TextField();
        Button okButton = new Button("OK");
        headerForm.add(new Label("BG COLOR:"));
        headerForm.add(bgColorPicker, "wrap");
        headerForm.add(new Label("TEXT COLOR:"));
        headerForm.add(textColorPicker, "wrap");
        headerForm.add(new Label("TEXT:"));
        headerForm.add(textField, "wrap");
        okButton.setOnAction(event1 -> {
            String text1;
            TemplateColor bgColor, textColor;
            text1 = textField.getText();
            bgColor = bgColorPicker.getValue();
            textColor = textColorPicker.getValue();
            canvas.add(new Header(bgColor, textColor, text1));
            popOver.hide();
        });
        headerForm.add(okButton, "skip 1, right");
    }
    //footerForm
    {
        footerForm = new MigPane();
        TemplateColorPicker bgColorPicker = new TemplateColorPicker();
        TemplateColorPicker textColorPicker = new TemplateColorPicker();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        Button okButton = new Button("OK");
        footerForm.add(new Label("BG COLOR:"));
        footerForm.add(bgColorPicker, "wrap");
        footerForm.add(new Label("TEXT COLOR:"));
        footerForm.add(textColorPicker, "wrap");
        footerForm.add(new Label("TEXT:"));
        footerForm.add(textField, "wrap");
        footerForm.add(textField2, "skip 1, wrap");
        okButton.setOnAction(event1 -> {
            String text1, text2;
            TemplateColor bgColor, textColor;
            text1 = textField.getText();
            text2 = textField2.getText();
            bgColor = bgColorPicker.getValue();
            textColor = textColorPicker.getValue();
            canvas.add(new Footer(bgColor, textColor, text1, text2));
            popOver.hide();
        });
        footerForm.add(okButton, "skip 1, right");
    }
    //textForm
    {
        textForm = new MigPane();
        TemplateColorPicker textColorPicker = new TemplateColorPicker();
        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(2);
        textArea.setPrefColumnCount(10);
        NumberField yField = new NumberField();
        ComboBox<Text.Size> sizeComboBox = new ComboBox<>();
//        sizeComboBox.setItems(FXCollections.observableArrayList(Arrays.asList(Text.Size.values())));
        // não mostrar todos os sizes pois alguns ainda não são válidos
        sizeComboBox.setItems(FXCollections.observableArrayList(Text.Size.SMALL, Text.Size.LARGE, Text.Size.HUGE, Text.Size.ARGUMENT));
        sizeComboBox.getSelectionModel().select(0);
        ComboBox<Text.Alignment> alignmentComboBox = new ComboBox<>();
        alignmentComboBox.setItems(FXCollections.observableArrayList(Arrays.asList(Text.Alignment.values())));
        alignmentComboBox.getSelectionModel().select(0);
        Button okButton = new Button("OK");
        textForm.add(new Label("TEXT COLOR:"));
        textForm.add(textColorPicker, "wrap");
        textForm.add(new Label("Y:"));
        textForm.add(yField, "wrap");
        textForm.add(new Label("TEXT:"));
        textForm.add(textArea, "wrap");
        textForm.add(new Label("SIZE:"));
        textForm.add(sizeComboBox, "wrap");
        textForm.add(new Label("ALIGNMENT:"));
        textForm.add(alignmentComboBox, "wrap");
        textForm.add(okButton, "skip 1, right");
        okButton.setOnAction(event1 -> {
            int y;
            String text1;
            TemplateColor textColor;
            y = Integer.parseInt(yField.getText());
            text1 = textArea.getText();
            Text.Size size = sizeComboBox.getValue();
            Text.Alignment alignment = alignmentComboBox.getValue();
            textColor = textColorPicker.getValue();
            canvas.add(new Text(y, textColor, null, size, alignment, text1));
            popOver.hide();
        });
    }
    //rectForm
    {
        rectForm = new MigPane();
        TemplateColorPicker colorPicker = new TemplateColorPicker();
        NumberField xField = new NumberField();
        NumberField yField = new NumberField();
        NumberField wField = new NumberField();
        NumberField hField = new NumberField();
        Button okButton = new Button("OK");
        rectForm.add(new Label("COLOR:"));
        rectForm.add(colorPicker, "wrap");
        rectForm.add(new Label("Y:"));
        rectForm.add(yField, "wrap");
        rectForm.add(new Label("X:"));
        rectForm.add(xField, "wrap");
        rectForm.add(new Label("W:"));
        rectForm.add(wField, "wrap");
        rectForm.add(new Label("H:"));
        rectForm.add(hField, "wrap");
        rectForm.add(okButton, "skip 1, right");

        okButton.setOnAction(event1 -> {
            canvas.add(new Rect(Integer.valueOf(xField.getText()),
                    Integer.valueOf(wField.getText()),
                    Integer.valueOf(yField.getText()),
                    Integer.valueOf(hField.getText()),
                    colorPicker.getValue()
            ));
            popOver.hide();
        });
    }
    //stripeForm
    {
        stripeForm = new MigPane();
        TemplateColorPicker colorPicker = new TemplateColorPicker();
        NumberField yField = new NumberField();
        NumberField hField = new NumberField();
        Button okButton = new Button("OK");
        stripeForm.add(new Label("COLOR:"));
        stripeForm.add(colorPicker, "wrap");
        stripeForm.add(new Label("Y:"));
        stripeForm.add(yField, "wrap");
        stripeForm.add(new Label("HEIGHT:"));
        stripeForm.add(hField, "wrap");
        stripeForm.add(okButton, "skip 1, right");

        okButton.setOnAction(event1 -> { //TODO
            canvas.add(new Stripe(
                    Integer.parseInt(yField.getText()),
                    Integer.parseInt(hField.getText()),
                    colorPicker.getValue()
            ));
            popOver.hide();
        });
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/templates.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));

        Scene scene = new Scene(root);

        stage.setTitle("Templates");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void initialize() {
        canvas.setOnMouseMoved(event -> {
            double y = event.getY();
            double x = event.getX();
            xyLabel.setText("X/Y: " + x + "/" + y);
        });

        attachPopOver(header, headerForm);
        attachPopOver(footer, footerForm);
        attachPopOver(rect, rectForm);
        attachPopOver(text, textForm);
        attachPopOver(stripe, stripeForm);

        clearButton.setOnAction(event -> {
            Template template = listView.getSelectionModel().getSelectedItem();
            template.storeState();
            template.getTemplateObjs().clear();
            template.setDirty(true);
            canvas.redraw();
        });

        listView.getSelectionModel().selectedItemProperty().addListener(templateChangeListener);
        listView.setItems(templatesSingleton.getObservableTemplates());
        listView.setEditable(true);
        listView.setCellFactory(param -> new IndexedTemplateCell());
    }

    public void toggleGrid(){
        canvas.setGridOn(showGrid.isSelected());
    }

    private void attachPopOver(Button button, Parent parent){
        button.setOnAction(event -> {
            if (popOver != null && popOver.isShowing()) {
                popOver.hide();
            }
            popOver = new PopOver(parent);
            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
            popOver.setAutoHide(true);
            popOver.setDetachable(false);
            popOver.show(button);
        });
    }

    public void saveSelected() {
        Template selected = listView.getSelectionModel().getSelectedItem();

        templatesSingleton.persist(selected);
    }

    ChangeListener<Template> templateChangeListener = new ChangeListener<Template>() {
        @Override
        public void changed(ObservableValue<? extends Template> observable, Template oldValue, Template newValue) {
            if (oldValue != null && oldValue.isDirty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Descartar alterações feitas no template?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    oldValue.restoreState();
                    listView.setItems(null);
                    listView.setItems(templatesSingleton.getObservableTemplates());
                } else {
                    listView.getSelectionModel().select(oldValue);
                    return;
                }
            }
            canvas.setTemplate(newValue);
        }
    };
}

class IndexedTemplateCell extends TextFieldListCell<Template> {
    public IndexedTemplateCell() {
        editableProperty().set(true);
        setConverter(new StringConverter<Template>() {
            @Override
            public String toString(Template object) {
                if (editingProperty().get()) {
                    return object.toString();
                } else {
                    return (getIndex() + 1) + "- " + object.toString();
                }
            }
            @Override
            public Template fromString(String string) {
                getItem().setName(string);
                return getItem();
            }
        });
    }
}
