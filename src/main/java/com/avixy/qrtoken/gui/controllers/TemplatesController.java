package com.avixy.qrtoken.gui.controllers;

import com.avixy.qrtoken.gui.components.NumberField;
import com.avixy.qrtoken.gui.components.templates.TemplateColorPicker;
import com.avixy.qrtoken.gui.components.templates.TemplateTextTextArea;
import com.avixy.qrtoken.gui.components.templates.TextAlignmentSelect;
import com.avixy.qrtoken.gui.components.templates.TextSizeSelect;
import com.avixy.qrtoken.gui.components.validators.JideMaxValueValidator;
import com.avixy.qrtoken.gui.components.customControls.PopOver;
import com.avixy.qrtoken.negocio.Token;
import com.avixy.qrtoken.negocio.template.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import jidefx.scene.control.decoration.DecorationPane;
import jidefx.scene.control.validation.ValidationUtils;
import org.apache.commons.lang.StringUtils;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created on 12/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplatesController {
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
    private IntegerProperty screenQtyProperty = new SimpleIntegerProperty(1);

    TemplatesSingleton templatesSingleton = TemplatesSingleton.getInstance();

    private PopOver formsPopOver;
    private PopOver editTemplateObjPopOver;

    private HeaderForm headerForm = new HeaderForm();
    private FooterForm footerForm = new FooterForm();
    private RectForm rectForm = new RectForm();
    private TextForm textForm = new TextForm();
    private StripeForm stripeForm = new StripeForm();
    private WaitForButtonForm waitForButtonForm = new WaitForButtonForm();

    private ChangeListener<Template> templateSelectedEvent = (observable, oldValue, newValue) -> {
        if (newValue == null) {
            canvas.setTemplate(null);
            return;
        }
        if (oldValue != null && oldValue.hasChanged()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Há alterações não salvas na aplicação atual.\nDeseja salvar as alterações?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                try {
                    oldValue.persist();
                } catch (TemplatesSingleton.TemplateOverflowException e) {
                    handleException(e);
                    templateListView.getSelectionModel().clearSelection();
                    templateListView.getSelectionModel().select(oldValue);
                    return;
                }
            } else {
                oldValue.discardChanges();
            }
        }
        canvas.setTemplate(newValue);
        templateObjListView.setItems(newValue.templateScreen(canvas.currScreenProperty().get()).getTemplateObjs());
        screenQtyProperty.bind(newValue.screenQtyProperty());
        waitButton.disableProperty().bind(newValue.templateScreen(canvas.currScreenProperty().get()).terminatedProperty());
    };
    private ChangeListener<TemplateObj> templateObjSelectedEvent = (observable, oldValue, newValue) -> canvas.highlight(newValue);

    public void initialize() {
        Stage stage = MainController.templateStage;
        stage.setOnCloseRequest(event1 -> {
            Template selectedTemplate = templateListView.getSelectionModel().getSelectedItem();
            if (selectedTemplate.hasChanged()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Há alterações não salvas na aplicação atual.\nDeseja salvar as alterações?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES) {
                    try {
                        selectedTemplate.persist();
                    } catch (TemplatesSingleton.TemplateOverflowException e) {
                        event1.consume();
                        handleException(e);
                    }
                } else {
                    selectedTemplate.discardChanges();
                }
            } else {
                stage.close();
            }
        });

        canvas.setOnMouseMoved(event -> {
            double y = event.getY() + 1;
            double x = event.getX() + 1;
            xyLabel.setText("X/Y: " + x + "/" + y);
        });

        attachPopOver(header, new DecorationPane(headerForm));
        attachPopOver(footer, new DecorationPane(footerForm));
        attachPopOver(rect, new DecorationPane(rectForm));
        attachPopOver(text, new DecorationPane(textForm));
        attachPopOver(stripe, new DecorationPane(stripeForm));
        attachPopOver(waitButton, new DecorationPane(waitForButtonForm));

        clearButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja apagar esta aplicação?\nEssa operação não pode ser desfeita.", ButtonType.YES, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Template template = templateListView.getSelectionModel().getSelectedItem();
                template.clear();
                canvas.redraw();
                try {
                    template.persist();
                } catch (TemplatesSingleton.TemplateOverflowException e) {
                    handleException(e);
                    template.discardChanges();
                }
            }
        });

        editTemplateObjPopOver = new PopOver();
        templateListView.setItems(templatesSingleton.getObservableTemplates());
        templateListView.setEditable(true);
        templateListView.setCellFactory(param -> new IndexedTemplateCell());
        templateObjListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && editTemplateObjPopOver != null) editTemplateObjPopOver.hide();
            }
        });
        templateObjListView.setCellFactory(new Callback<ListView<TemplateObj>, ListCell<TemplateObj>>() {
            @Override
            public ListCell<TemplateObj> call(ListView<TemplateObj> param) {
                return new ListCell<TemplateObj>() {
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
                                editTemplateObjPopOver.hide();
                                canvas.remove(getItem());
                            });
                            edit.setOnAction(e -> {
                                editTemplateObjPopOver.hide();
                                handleEdit(getItem());
                            });
                            editTemplateObjPopOver.setContentNode(migPane);
                            editTemplateObjPopOver.show(this);
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
            }
        });

        templateListView.getSelectionModel().selectedItemProperty().addListener(templateSelectedEvent);
        templateObjListView.getSelectionModel().selectedItemProperty().addListener(templateObjSelectedEvent);
        templateListView.getSelectionModel().select(0);

        screenQty.textProperty().bind(Bindings.format("%s/%s", canvas.currScreenProperty(), screenQtyProperty));
    }

    private void handleEdit(TemplateObj item) {
        if (item instanceof Stripe) {
            stripeForm.edit((Stripe)item);
            return;
        }
        if (item instanceof Rect) {
            rectForm.edit((Rect)item);
            return;
        }
        if (item instanceof Header) {
            headerForm.edit((Header)item);
            return;
        }
        if (item instanceof Footer) {
            footerForm.edit((Footer)item);
            return;
        }
        if (item instanceof Text) {
            textForm.edit((Text)item);
            return;
        }
        if (item instanceof WaitForButton) {
            waitForButtonForm.edit((WaitForButton)item);
        }
    }

    public void toggleGrid(){
        canvas.setGridOn(showGrid.isSelected());
    }

    /* attaches <code>button</code> action to PopOver with <code>node</code> */
    private void attachPopOver(Button button, Node node){
        button.setOnAction(event -> {
            if (formsPopOver != null && formsPopOver.isShowing()) {
                formsPopOver.hide();
            }
            formsPopOver = new PopOver(node);
            formsPopOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
            formsPopOver.setAutoHide(true);
            formsPopOver.show(button);
        });
    }

    public void saveSelected() {
        Template selected = templateListView.getSelectionModel().getSelectedItem();

        try {
            selected.persist();
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

    private void attachSubmitEvent(MigPane form, Button okButton) {
        EventHandler<KeyEvent> submitEvent = event -> {
            if (event.getCode() == KeyCode.ENTER) okButton.getOnAction().handle(new ActionEvent());
        };
        form.setOnKeyPressed(submitEvent);
    }

    /* workaround to get the TemplateObj display name in the list to update */
    private void forceRefresh(ListView<TemplateObj> listView) {
        TemplateObj selected = listView.getSelectionModel().getSelectedItem();
        ObservableList<TemplateObj> items = listView.getItems();
        listView.setItems(null);
        listView.setItems(items);
        listView.getSelectionModel().select(selected);
    }

    @FXML
    private void nextScreen() {
        //NOTE: essa ordem de execução é relevante, com ordem diferente aparentemente estava caindo em uma race condition
        //com o TokenCanvas currScreenProperty
        editTemplateObjPopOver.hide();
        templateObjListView.getSelectionModel().select(null);
        if (canvas.currScreenProperty().get() < screenQtyProperty.get()) {
            canvas.currScreenProperty().setValue(canvas.currScreenProperty().getValue() + 1);
        }
        templateObjListView.setItems(templateListView.getSelectionModel().getSelectedItem().templateScreen(canvas.currScreenProperty().get()).getTemplateObjs());
        waitButton.disableProperty().bind(templateListView.getSelectionModel().getSelectedItem().templateScreen(canvas.currScreenProperty().get()).terminatedProperty());
    }

    @FXML
    private void previousScreen(){
        editTemplateObjPopOver.hide();
        templateObjListView.getSelectionModel().select(null);
        if (canvas.currScreenProperty().get() > 1) {
            canvas.currScreenProperty().setValue(canvas.currScreenProperty().getValue() - 1);
        }
        templateObjListView.setItems(templateListView.getSelectionModel().getSelectedItem().templateScreen(canvas.currScreenProperty().get()).getTemplateObjs());
        waitButton.disableProperty().bind(templateListView.getSelectionModel().getSelectedItem().templateScreen(canvas.currScreenProperty().get()).terminatedProperty());
    }

    abstract class ElemForm extends MigPane {

        public void resetFields() {
            for (Node node : getChildren()) {
                if (node instanceof TextField) ((TextField) node).setText("");
                if (node instanceof NumberField) ((TextField) node).setText("0");
                if (node instanceof ComboBox) ((ComboBox) node).getSelectionModel().select(0);
            }
        }

    }

    class HeaderForm extends ElemForm {
        final TemplateColorPicker bgColorPicker = new TemplateColorPicker(TemplateColorPicker.BG_TEXT_COLOR);
        final TemplateColorPicker textColorPicker = new TemplateColorPicker(TemplateColorPicker.TEXT_COLOR);
        final TextField textField = new TextField();
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
                h.setText(textField.getText());
                canvas.redraw();
                formsPopOver.hide();
            };
            textField.setText(h.getText());
            okButton.setOnAction(editEvent);
            textColorPicker.setTemplateColor(h.getTextColor());
            bgColorPicker.setTemplateColor(h.getBgColor());
            textField.setText(h.getText());
            header.getOnAction().handle(new ActionEvent());
            formsPopOver.setOnHidden(e -> {
                formsPopOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
                resetFields();
            });
        }
        EventHandler<ActionEvent> saveEvent = e -> {
            String text1;
            TemplateColor bgColor, textColor;
            text1 = textField.getText();
            bgColor = bgColorPicker.getValue();
            textColor = textColorPicker.getValue();
            canvas.add(new Header(bgColor, textColor, text1));
            formsPopOver.hide();
            resetFields();
        };
        @Override
        public void resetFields() {
            super.resetFields();
            bgColorPicker.getSelectionModel().select(TemplateColorPicker.BG_TEXT_COLOR);
            textColorPicker.getSelectionModel().select(TemplateColorPicker.TEXT_COLOR);
        }
    }
    class FooterForm extends ElemForm {
        TemplateColorPicker bgColorPicker = new TemplateColorPicker();
        TemplateColorPicker textColorPicker = new TemplateColorPicker(TemplateColorPicker.TEXT_COLOR);
        TextField templateTextField = new TextField();
        TextField templateTextField2 = new TextField();
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
                f.setText(templateTextField.getText());
                f.setText2(templateTextField2.getText());
                canvas.redraw();
                formsPopOver.hide();
            });
            textColorPicker.setTemplateColor(f.getTextColor());
            bgColorPicker.setTemplateColor(f.getBgColor());
            templateTextField.setText(f.getText());
            templateTextField2.setText(f.getText2());
            footer.getOnAction().handle(new ActionEvent());
            formsPopOver.setOnHidden(e -> {
                formsPopOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
                resetFields();
            });
        }
        EventHandler<ActionEvent> saveEvent = e -> {
            String text1, text2;
            TemplateColor bgColor, textColor;
            text1 = templateTextField.getText();
            text2 = templateTextField2.getText();
            bgColor = bgColorPicker.getValue();
            textColor = textColorPicker.getValue();
            canvas.add(new Footer(bgColor, textColor, text1, text2));
            formsPopOver.hide();
            resetFields();
        };
        @Override
        public void resetFields(){
            super.resetFields();
            bgColorPicker.getSelectionModel().select(TemplateColorPicker.BG_TEXT_COLOR);
            bgColorPicker.getSelectionModel().select(TemplateColorPicker.TEXT_COLOR);
        }
    }
    class TextForm extends ElemForm {
        TemplateColorPicker textColorPicker = new TemplateColorPicker(TemplateColorPicker.TEXT_COLOR);
        TemplateColorPicker bgColorPicker = new TemplateColorPicker(TemplateColorPicker.BG_TEXT_COLOR);
        TemplateTextTextArea templateTextArea = new TemplateTextTextArea(2, 10);
        NumberField yField = new NumberField();
        TextSizeSelect sizeComboBox = new TextSizeSelect();
        TextAlignmentSelect alignmentSelect = new TextAlignmentSelect();
        Button okButton = new Button("OK");
        public TextForm() {
            ValidationUtils.install(yField, new JideMaxValueValidator(Token.DISPLAY_HEIGHT));
            // não mostrar todos os sizes pois alguns ainda não são válidos
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
            add(alignmentSelect, "wrap");
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
                t.setAlignment(alignmentSelect.getValue());
                t.setSize(sizeComboBox.getValue());
                canvas.redraw();
                formsPopOver.hide();
            };
            okButton.setOnAction(editEvent);
            yField.setText(String.valueOf(t.getY()));
            templateTextArea.setValue(t.getText());
            textColorPicker.setTemplateColor(t.getColor());
            bgColorPicker.setTemplateColor(t.getBgColor());
            sizeComboBox.setValue(t.getSize());
            alignmentSelect.setValue(t.getAlignment());

            text.getOnAction().handle(new ActionEvent());
            formsPopOver.setOnHidden(e -> {
                formsPopOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
                resetFields();
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
            Text.Alignment alignment = alignmentSelect.getValue();
            textColor = textColorPicker.getValue();
            bgColor = bgColorPicker.getValue();
            canvas.add(new Text(y, textColor, bgColor, size, alignment, text1));
            formsPopOver.hide();
            resetFields();
        };

        @Override
        public void resetFields() {
            super.resetFields();
            templateTextArea.setValue("");
            bgColorPicker.getSelectionModel().select(TemplateColorPicker.BG_TEXT_COLOR);
        }
    }
    class RectForm extends ElemForm {
        TemplateColorPicker colorPicker = new TemplateColorPicker();
        NumberField xField = new NumberField();
        NumberField yField = new NumberField();
        NumberField wField = new NumberField();
        NumberField hField = new NumberField();
        Button okButton = new Button("OK");
        public RectForm() {
            ValidationUtils.install(xField, new JideMaxValueValidator(Token.DISPLAY_WIDTH));
            ValidationUtils.install(wField, new JideMaxValueValidator(Token.DISPLAY_WIDTH));
            ValidationUtils.install(hField, new JideMaxValueValidator(Token.DISPLAY_HEIGHT));
            ValidationUtils.install(yField, new JideMaxValueValidator(Token.DISPLAY_HEIGHT));
            add(new Label("COLOR:"));
            add(colorPicker, "wrap");
            add(new Label("X:"));
            add(xField, "wrap");
            add(new Label("Y:"));
            add(yField, "wrap");
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
                canvas.redraw();
                formsPopOver.hide();
            };
            okButton.setOnAction(editEvent);
            colorPicker.setTemplateColor(r.getColor());
            yField.setText(String.valueOf(r.getY().intValue()));
            xField.setText(String.valueOf(r.getX().intValue()));
            hField.setText(String.valueOf(r.getHeight().intValue()));
            wField.setText(String.valueOf(r.getWidth().intValue()));
            rect.getOnAction().handle(new ActionEvent());
            formsPopOver.setOnHidden(e -> {
                formsPopOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
                resetFields();
            });
        }

        EventHandler<ActionEvent> saveEvent = e -> {
            canvas.add(new Rect(
                    Integer.valueOf(xField.getText()),
                    Integer.valueOf(wField.getText()),
                    Integer.valueOf(yField.getText()),
                    Integer.valueOf(hField.getText()),
                    colorPicker.getValue()
            ));
            formsPopOver.hide();
            resetFields();
        };
    }
    class StripeForm extends ElemForm {
        TemplateColorPicker colorPicker = new TemplateColorPicker();
        NumberField yField = new NumberField();
        NumberField hField = new NumberField();
        Button okButton = new Button("OK");
        public StripeForm() {
            ValidationUtils.install(yField, new JideMaxValueValidator(Token.DISPLAY_HEIGHT));
            ValidationUtils.install(hField, new JideMaxValueValidator(Token.DISPLAY_HEIGHT));
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
                canvas.redraw();
                formsPopOver.hide();
            };
            okButton.setOnAction(editEvent);
            colorPicker.setTemplateColor(s.getColor());
            yField.setText(String.valueOf(s.getY().intValue()));
            hField.setText(String.valueOf(s.getHeight().intValue()));
            stripe.getOnAction().handle(new ActionEvent());
            formsPopOver.setOnHidden(e -> {
                formsPopOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
                resetFields();
            });
        }

        EventHandler<ActionEvent> saveEvent = e -> {
            canvas.add(new Stripe(
                    Integer.parseInt(yField.getText()),
                    Integer.parseInt(hField.getText()),
                    colorPicker.getValue()));
            formsPopOver.hide();
            resetFields();
        };
    }
    class WaitForButtonForm extends ElemForm {
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
                canvas.redraw();
                formsPopOver.hide();
                templateListView.getSelectionModel().getSelectedItem().templateScreen(canvas.currScreenProperty().get()).refresh();
            });
            formsPopOver.setOnHidden(e -> {
                formsPopOver.setOnHidden(null);
                okButton.setOnAction(saveEvent);
                forceRefresh(templateObjListView);
                resetFields();
            });
        }
        EventHandler<ActionEvent> saveEvent = e -> {
            canvas.add(new WaitForButton(waitComboBox.getValue(), nextActionComboBox.getValue()));
            formsPopOver.hide();

            if (nextActionComboBox.getValue() == WaitForButton.NextAction.NEW_SCREEN)
            {
                editTemplateObjPopOver.hide();
                templateObjListView.getSelectionModel().select(null);
                if (canvas.currScreenProperty().get() < screenQtyProperty.get()) {
                    canvas.currScreenProperty().setValue(canvas.currScreenProperty().getValue() + 1);
                }
                templateObjListView.setItems(templateListView.getSelectionModel().getSelectedItem().templateScreen(canvas.currScreenProperty().get()).getTemplateObjs());
                waitButton.disableProperty().bind(templateListView.getSelectionModel().getSelectedItem().templateScreen(canvas.currScreenProperty().get()).terminatedProperty());
            }
            resetFields();
        };
    }

    /* Editable ListCell that displays the index */
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
}
