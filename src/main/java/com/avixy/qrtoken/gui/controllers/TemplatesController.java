package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.core.extensions.components.NumberField;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateColorPicker;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateTextTextArea;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateTextTextField;
import com.avixy.qrtoken.core.extensions.customControls.PopOver;
import com.avixy.qrtoken.negocio.template.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.commons.lang.StringUtils;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @FXML private Button waitButton;
    @FXML private Button clearButton;
    @FXML private Button saveButton;

    @FXML private TokenCanvas canvas;

    @FXML private Label xyLabel;

    @FXML private ListView<Template> templateListView;
    @FXML private ListView<TemplateObj> templateObjListView;

    @FXML private Label screenQty;
    private IntegerProperty currScreenProperty = new SimpleIntegerProperty(1);
    private IntegerProperty screenQtyProperty = new SimpleIntegerProperty(1);
    private StringExpression screensFormat = Bindings.format("%s/%s", currScreenProperty, screenQtyProperty);

    TemplatesSingleton templatesSingleton = TemplatesSingleton.getInstance();

    private PopOver popOver;
    
    private HeaderForm headerForm = new HeaderForm();
    private FooterForm footerForm = new FooterForm();
    private RectForm rectForm = new RectForm();
    private TextForm textForm = new TextForm();
    private StripeForm stripeForm = new StripeForm();
    private WaitForButtonForm waitForButtonForm = new WaitForButtonForm();

    private ChangeListener<Template> templateSelectedEvent = (observable, oldValue, newValue) -> {
        currScreenProperty.set(1);
        templateObjListView.setItems(newValue.subTemplate(currScreenProperty.get()).getTemplateObjs());
        screenQtyProperty.bind(newValue.screenQtyProperty);

//        if (oldValue != null && oldValue.isDirty()) {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Descartar alterações feitas no template?");
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK) {
//                oldValue.restoreState();
//                templateListView.setItems(null);
//                templateListView.setItems(templatesSingleton.getObservableTemplates());
//            } else {
//                templateListView.getSelectionModel().select(oldValue);
//                return;
//            }
//        }
        canvas.setTemplate(newValue, currScreenProperty.get());
    };
    private ChangeListener<TemplateObj> templateObjSelectedEvent = (observable, oldValue, newValue) -> {
        canvas.highlight(newValue);
    };

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

    PopOver popOver1;
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
        attachPopOver(waitButton, waitForButtonForm);

        clearButton.setOnAction(event -> {
            Template template = templateListView.getSelectionModel().getSelectedItem();
            template.storeState();
            template.clear();
            template.setDirty(true);
            canvas.redraw(currScreenProperty.get());
        });

        templateListView.setItems(templatesSingleton.getObservableTemplates());
        templateListView.setEditable(true);
        templateListView.setCellFactory(param -> new IndexedTemplateCell());
        popOver1 = new PopOver();
        templateObjListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && popOver1 != null) popOver1.hide();
            }
        });
        templateObjListView.setCellFactory(new Callback<ListView<TemplateObj>, ListCell<TemplateObj>>() {
            @Override
            public ListCell<TemplateObj> call(ListView<TemplateObj> param) {
                ListCell<TemplateObj> listCell = new ListCell<TemplateObj>() {
                    @Override
                    public void updateSelected(boolean selected) {
                        super.updateSelected(selected);
                        if (selected) {
                            Button delete = new Button("delete");
                            Button edit = new Button("edit");
                            MigPane migPane = new MigPane();
                            migPane.add(edit);
                            migPane.add(delete);
                            delete.setOnAction(e -> {
                                templateObjListView.getSelectionModel().clearSelection();
                                popOver1.hide();
                                canvas.remove(getItem(), currScreenProperty.get());
                            });
                            edit.setOnAction(e -> {
                                popOver1.hide();
                                handleEdit(getItem());
                            });
                            popOver1.setContentNode(migPane);
                            popOver1.show(this);
                        }
                    }

                    @Override
                    protected void updateItem(TemplateObj item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty)  {
                            setGraphic(new Label(StringUtils.abbreviate(item.toString(), 25)));
                        } else {
                            setGraphic(null);
                        }
                    }
                };
                return listCell;
            }
        });

        templateListView.getSelectionModel().selectedItemProperty().addListener(templateSelectedEvent);
        templateObjListView.getSelectionModel().selectedItemProperty().addListener(templateObjSelectedEvent);
        templateListView.getSelectionModel().select(0);

        screenQty.textProperty().bind(screensFormat);
    }

    private void handleEdit(TemplateObj item) {
        if (item instanceof Stripe) {
            Stripe k = (Stripe)item;
            stripeForm.edit(k);
            return;
        }
        if (item instanceof Rect) {
            Rect k = (Rect)item;
            rectForm.edit(k);
            return;
        }
        if (item instanceof Header) {
            Header k = (Header) item;
            headerForm.edit(k);
            return;
        }
        if (item instanceof Footer) {
            Footer f = (Footer) item;
            footerForm.edit(f);
            return;
        }
        if (item instanceof Text) {
            Text t = (Text)item;
            textForm.edit(t);
            return;
        }
        if (item instanceof WaitForButton) {
            WaitForButton w = (WaitForButton)item;
            waitForButtonForm.edit(w);
            return;
        }
    }

    public void toggleGrid(){
        canvas.setGridOn(showGrid.isSelected(), currScreenProperty.get());
    }

    private void attachPopOver(Button button, Parent parent){
        button.setOnAction(event -> {
            if (popOver != null && popOver.isShowing()) {
                popOver.hide();
            }
            popOver = new PopOver(parent);
            popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
            popOver.setAutoHide(true);
            popOver.show(button);
        });
    }

    public void saveSelected() {
        Template selected = templateListView.getSelectionModel().getSelectedItem();

        try {
            templatesSingleton.persist(selected);
        } catch (TemplatesSingleton.TemplateOverflowException e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
    }

    class HeaderForm extends MigPane {
        TemplateColorPicker bgColorPicker = new TemplateColorPicker();
        TemplateColorPicker textColorPicker = new TemplateColorPicker(TemplateColorPicker.TEXT_COLOR);
        TemplateTextTextField textField = new TemplateTextTextField();
        Button okButton = new Button("OK");
        public HeaderForm() {
            add(new Label("BG COLOR:"));
            add(bgColorPicker, "wrap");
            add(new Label("TEXT COLOR:"));
            add(textColorPicker, "wrap");
            add(new Label("TEXT:"));
            add(textField, "wrap");
            okButton.setOnAction(saveEvent);
            add(okButton, "skip 1, right");
            attachSubmitEvent(this, okButton);
        }

        public void edit(Header h) {
            EventHandler<ActionEvent> editEvent = e -> {
                h.setTextColor(textColorPicker.getValue());
                h.setBgColor(bgColorPicker.getValue());
                h.setText(textField.getValue());
                canvas.redraw(currScreenProperty.get());
                popOver.hide();
            };
            textField.setValue(h.getText());
            okButton.setOnAction(editEvent);
            textColorPicker.setTemplateColor(h.getTextColor());
            bgColorPicker.setTemplateColor(h.getBgColor());
            textField.setValue(h.getText());
            header.getOnAction().handle(new ActionEvent());
            popOver.setOnHidden(e -> {
                popOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
            });
        }
        EventHandler<ActionEvent> saveEvent = e -> {
            String text1;
            TemplateColor bgColor, textColor;
            text1 = textField.getValue();
            bgColor = bgColorPicker.getValue();
            textColor = textColorPicker.getValue();
            canvas.add(new Header(bgColor, textColor, text1), currScreenProperty.get());
            popOver.hide();
        };
    }
    class FooterForm extends MigPane {
        TemplateColorPicker bgColorPicker = new TemplateColorPicker();
        TemplateColorPicker textColorPicker = new TemplateColorPicker(TemplateColorPicker.TEXT_COLOR);
        TemplateTextTextField templateTextField = new TemplateTextTextField();
        TemplateTextTextField templateTextField2 = new TemplateTextTextField();
        Button okButton = new Button("OK");
        public FooterForm() {
            add(new Label("BG COLOR:"));
            add(bgColorPicker, "wrap");
            add(new Label("TEXT COLOR:"));
            add(textColorPicker, "wrap");
            add(new Label("TEXT:"));
            add(templateTextField, "wrap");
            add(templateTextField2, "skip 1, wrap");
            okButton.setOnAction(saveEvent);
            add(okButton, "skip 1, right");
            attachSubmitEvent(this, okButton);
        }
        public void edit(Footer f) {
            okButton.setOnAction(e -> {
                f.setTextColor(textColorPicker.getValue());
                f.setBgColor(bgColorPicker.getValue());
                f.setText(templateTextField.getValue());
                f.setText2(templateTextField2.getValue());
                canvas.redraw(currScreenProperty.get());
                popOver.hide();
            });
            textColorPicker.setTemplateColor(f.getTextColor());
            bgColorPicker.setTemplateColor(f.getBgColor());
            templateTextField.setValue(f.getText());
            templateTextField2.setValue(f.getText2());
            footer.getOnAction().handle(new ActionEvent());
            popOver.setOnHidden(e -> {
                popOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
            });
        }
        EventHandler<ActionEvent> saveEvent = e -> {
            String text1, text2;
            TemplateColor bgColor, textColor;
            text1 = templateTextField.getValue();
            text2 = templateTextField2.getValue();
            bgColor = bgColorPicker.getValue();
            textColor = textColorPicker.getValue();
            canvas.add(new Footer(bgColor, textColor, text1, text2), currScreenProperty.get());
            popOver.hide();
        };
    }
    class TextForm extends MigPane {
        TemplateColorPicker textColorPicker = new TemplateColorPicker(TemplateColorPicker.TEXT_COLOR);
        TemplateColorPicker bgColorPicker = new TemplateColorPicker(TemplateColorPicker.BG_TEXT_COLOR);
        TemplateTextTextArea templateTextArea = new TemplateTextTextArea(2, 10);
        NumberField yField = new NumberField();
        ComboBox<Text.Size> sizeComboBox = new ComboBox<>();
        ComboBox<Text.Alignment> alignmentComboBox = new ComboBox<>();
        Button okButton = new Button("OK");
        public TextForm() {
            // não mostrar todos os sizes pois alguns ainda não são válidos
            sizeComboBox.setItems(FXCollections.observableArrayList(Text.Size.SMALL, Text.Size.LARGE, Text.Size.HUGE, Text.Size.ARGUMENT));
            sizeComboBox.getSelectionModel().select(0);
            alignmentComboBox.setItems(FXCollections.observableArrayList(Arrays.asList(Text.Alignment.values())));
            alignmentComboBox.getSelectionModel().select(0);
            add(new Label("TEXT COLOR:"));
            add(textColorPicker, "wrap");
            add(new Label("BG COLOR:"));
            add(bgColorPicker, "wrap");
            add(new Label("Y:"));
            add(yField, "wrap");
            add(new Label("TEXT:"));
            add(templateTextArea, "wrap");
            add(new Label("SIZE:"));
            add(sizeComboBox, "wrap");
            add(new Label("ALIGNMENT:"));
            add(alignmentComboBox, "wrap");
            add(okButton, "skip 1, right");
            okButton.setOnAction(saveEvent);

            attachSubmitEvent(this, okButton);
        }

        public void edit(Text t) {
            EventHandler<ActionEvent> editEvent = e -> {
                t.setY(Integer.parseInt(yField.getText()));
                t.setText(templateTextArea.getValue());
                t.setColor(textColorPicker.getValue());
                t.setBgColor(bgColorPicker.getValue());
                t.setAlignment(alignmentComboBox.getValue());
                t.setSize(sizeComboBox.getValue());
                canvas.redraw(currScreenProperty.get());
                popOver.hide();
            };
            okButton.setOnAction(editEvent);
            yField.setText(String.valueOf(t.getY()));
            templateTextArea.setValue(t.getText());
            textColorPicker.setTemplateColor(t.getColor());
            bgColorPicker.setTemplateColor(t.getBgColor());
            sizeComboBox.setValue(t.getSize());
            alignmentComboBox.setValue(t.getAlignment());

            text.getOnAction().handle(new ActionEvent());
            popOver.setOnHidden(e -> {
                popOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
            });
        }
        EventHandler<ActionEvent> saveEvent = e -> {
            int y;
            String text1;
            TemplateColor textColor;
            TemplateColor bgColor;
            y = Integer.parseInt(yField.getText());
            text1 = templateTextArea.getValue();
            Text.Size size = sizeComboBox.getValue();
            Text.Alignment alignment = alignmentComboBox.getValue();
            textColor = textColorPicker.getValue();
            bgColor = bgColorPicker.getValue();
            canvas.add(new Text(y, textColor, bgColor, size, alignment, text1), currScreenProperty.get());
            popOver.hide();
        };
    }
    class RectForm extends MigPane {
        TemplateColorPicker colorPicker = new TemplateColorPicker();
        NumberField xField = new NumberField();
        NumberField yField = new NumberField();
        NumberField wField = new NumberField();
        NumberField hField = new NumberField();
        Button okButton = new Button("OK");
        public RectForm() {
            add(new Label("COLOR:"));
            add(colorPicker, "wrap");
            add(new Label("Y:"));
            add(yField, "wrap");
            add(new Label("X:"));
            add(xField, "wrap");
            add(new Label("W:"));
            add(wField, "wrap");
            add(new Label("H:"));
            add(hField, "wrap");
            add(okButton, "skip 1, right");

            okButton.setOnAction(saveEvent);
            attachSubmitEvent(this, okButton);
        }
        public void edit(Rect r) {
            EventHandler<ActionEvent> editEvent = e -> {
                r.setY(Integer.parseInt(yField.getText()));
                r.setX(Integer.parseInt(xField.getText()));
                r.setHeight(Integer.parseInt(hField.getText()));
                r.setWidth(Integer.parseInt(wField.getText()));
                r.setColor(colorPicker.getValue());
                canvas.redraw(currScreenProperty.get());
                popOver.hide();
            };
            okButton.setOnAction(editEvent);
            colorPicker.setTemplateColor(r.getColor());
            yField.setText(String.valueOf(r.getY().intValue()));
            xField.setText(String.valueOf(r.getX().intValue()));
            hField.setText(String.valueOf(r.getHeight().intValue()));
            wField.setText(String.valueOf(r.getWidth().intValue()));
            rect.getOnAction().handle(new ActionEvent());
            popOver.setOnHidden(e -> {
                popOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
            });
        }

        EventHandler<ActionEvent> saveEvent = e -> {
            canvas.add(new Rect(
                    Integer.valueOf(xField.getText()),
                    Integer.valueOf(wField.getText()),
                    Integer.valueOf(yField.getText()),
                    Integer.valueOf(hField.getText()),
                    colorPicker.getValue()
            ), currScreenProperty.get());
            popOver.hide();
        };
    }
    class StripeForm extends MigPane {
        TemplateColorPicker colorPicker = new TemplateColorPicker();
        NumberField yField = new NumberField();
        NumberField hField = new NumberField();
        Button okButton = new Button("OK");
        public StripeForm() {
            add(new Label("COLOR:"));
            add(colorPicker, "wrap");
            add(new Label("Y:"));
            add(yField, "wrap");
            add(new Label("HEIGHT:"));
            add(hField, "wrap");
            add(okButton, "skip 1, right");

            okButton.setOnAction(saveEvent);
            attachSubmitEvent(this, okButton);
        }
        public void edit(Stripe s){
            EventHandler<ActionEvent> editEvent = e -> {
                s.setY(Integer.parseInt(yField.getText()));
                s.setHeight(Integer.parseInt(hField.getText()));
                s.setColor(colorPicker.getValue());
                canvas.redraw(currScreenProperty.get());
                popOver.hide();
            };
            okButton.setOnAction(editEvent);
            colorPicker.setTemplateColor(s.getColor());
            yField.setText(String.valueOf(s.getY().intValue()));
            hField.setText(String.valueOf(s.getHeight().intValue()));
            stripe.getOnAction().handle(new ActionEvent());
            popOver.setOnHidden(e -> {
                popOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
            });
        }

        EventHandler<ActionEvent> saveEvent = e -> {
            canvas.add(new Stripe(
                    Integer.parseInt(yField.getText()),
                    Integer.parseInt(hField.getText()),
                    colorPicker.getValue()), currScreenProperty.get());
            popOver.hide();
        };
    }
    class WaitForButtonForm extends MigPane {
        ComboBox<Integer> waitComboBox = new ComboBox<>();
        ComboBox<WaitForButton.NextAction> nextActionComboBox = new ComboBox<>();
        Button okButton = new Button("OK");

        public WaitForButtonForm() {
            List<Integer> list = new ArrayList<>();
            for (int i = 1; i <= 30; i++) { list.add(i); }
            waitComboBox.setItems(FXCollections.observableArrayList(list));
            waitComboBox.getSelectionModel().select(0);
            nextActionComboBox.setItems(FXCollections.observableArrayList(WaitForButton.NextAction.POWER_OFF, WaitForButton.NextAction.NEW_SCREEN));
            nextActionComboBox.getSelectionModel().select(0);
            okButton.setOnAction(saveEvent);
            add(new Label("WAIT:"));
            add(waitComboBox, "wrap");
            add(new Label("NEXT ACTION:"));
            add(nextActionComboBox, "wrap");
            add(okButton, "skip 1, right");
            attachSubmitEvent(this, okButton);
        }
        public void edit(WaitForButton waitForButton) {
            nextActionComboBox.setValue(waitForButton.getNextAction());
            waitComboBox.setValue(waitForButton.getWaitSeconds());
            waitButton.getOnAction().handle(new ActionEvent());
            okButton.setOnAction(e -> {
                waitForButton.setNextAction(nextActionComboBox.getValue());
                waitForButton.setWaitSeconds(waitComboBox.getValue());
                canvas.redraw(currScreenProperty.get());
                popOver.hide();
            });
            popOver.setOnHidden(e -> {
                popOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
            });
        }
        EventHandler<ActionEvent> saveEvent = e -> {
            canvas.add(new WaitForButton(waitComboBox.getValue(), nextActionComboBox.getValue()), currScreenProperty.get());
            popOver.hide();
        };
    }

    private void attachSubmitEvent(MigPane form, Button okButton) {
        EventHandler<KeyEvent> submitEvent = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) okButton.getOnAction().handle(new ActionEvent());
            }
        };
        form.setOnKeyPressed(submitEvent);
    }

    private void forceRefresh(ListView listView) {
        Object selected = listView.getSelectionModel().getSelectedItem();
        ObservableList<Object> items = listView.getItems();
        listView.setItems(null);
        listView.setItems(items);
        listView.getSelectionModel().select(selected);
    }

    @FXML
    private void nextScreen() {
        if (currScreenProperty.get() < screenQtyProperty.get()) {
            currScreenProperty.set(currScreenProperty.getValue() + 1);
        }
        templateObjListView.getSelectionModel().select(null);
        canvas.redraw(currScreenProperty.get());
        templateObjListView.setItems(templateListView.getSelectionModel().getSelectedItem().subTemplate(currScreenProperty.get()).getTemplateObjs());
        popOver1.hide();
    }

    @FXML
    private void previousScreen(){
        if (currScreenProperty.get() > 1) {
            currScreenProperty.set(currScreenProperty.getValue() - 1);
        }
        templateObjListView.getSelectionModel().select(null);
        canvas.redraw(currScreenProperty.get());
        templateObjListView.setItems(templateListView.getSelectionModel().getSelectedItem().subTemplate(currScreenProperty.get()).getTemplateObjs());
        popOver1.hide();
    }
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
                    return (getIndex()) + "- " + object.toString();
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
