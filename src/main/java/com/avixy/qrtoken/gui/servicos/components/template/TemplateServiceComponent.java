package com.avixy.qrtoken.gui.servicos.components.template;

import com.avixy.qrtoken.core.extensions.components.AesSelect;
import com.avixy.qrtoken.core.extensions.components.HmacSelect;
import com.avixy.qrtoken.core.extensions.components.PasswordField;
import com.avixy.qrtoken.core.extensions.components.TimestampField;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateColorPicker;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateSelect;
import com.avixy.qrtoken.core.extensions.components.templates.TextAlignmentSelect;
import com.avixy.qrtoken.core.extensions.components.templates.TextSizeSelect;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.params.template.TemplateColorParam;
import com.avixy.qrtoken.negocio.servico.params.template.TextAlignmentParam;
import com.avixy.qrtoken.negocio.servico.params.template.TextSizeParam;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.servicos.template.TemplateService;
import com.avixy.qrtoken.negocio.template.*;
import com.google.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/03/2015
 *
 * @author I7
 */
@ServiceComponent.Category(category = ServiceCategory.OUTROS)
@AcceptsKey(keyType = KeyType.HMAC)
public class TemplateServiceComponent extends ServiceComponent {
    private TemplateService service;
    private MigPane mainNode = new MigPane();
    private Label title = new Label();
    private TemplateSelect templateSelect = new TemplateSelect();
    private AesSelect aesSelect = new AesSelect();
    private HmacSelect hmacSelect = new HmacSelect();
    private TimestampField timestampField = new TimestampField();
    private Separator separator = new Separator();
    private Label hmacSelectLabel = new Label("HMAC Key:");
    private Label aesSelectLabel = new Label("AES Key:");
    private Label timestampLabel = new Label("Timestamp:");
    private PasswordField passwordField = new PasswordField();
    private Label passwordLabel = new Label("PIN:");
    private ComboBox<Integer> slotCombobox = new ComboBox<>();
    private Label slotLabel = new Label("Slot:");
    private Label templateLabel = new Label("Template:");
    private Label paramsTitle = new Label("Parâmetros do template");

    private List<Control> controlList;

    /**
     * @param service
     */
    @Inject
    protected TemplateServiceComponent(TemplateService service) {
        super(service);
        this.service = service;

        for (int i = 0; i <= 10; i++) {
            slotCombobox.getItems().add(i);
        }
        slotCombobox.getSelectionModel().select(0);

        title.setText(service.getServiceName());
        title.setFont(new Font(18));
        mainNode.add(title, "span");
        mainNode.add(templateLabel);
        mainNode.add(templateSelect, "wrap");
        mainNode.add(slotLabel);
        mainNode.add(slotCombobox, "wrap");
        mainNode.add(aesSelectLabel);
        mainNode.add(aesSelect, "wrap");
        mainNode.add(hmacSelectLabel);
        mainNode.add(hmacSelect, "wrap");
        mainNode.add(timestampLabel);
        mainNode.add(timestampField, "wrap");
        mainNode.add(passwordLabel);
        mainNode.add(passwordField, "wrap");
        separator.setPrefWidth(280);
        mainNode.add(separator, "span");
        paramsTitle.setFont(new Font(14));
        mainNode.add(paramsTitle, "span");

        templateSelect.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                controlList = new ArrayList<>();
                mainNode.getChildren().retainAll(title, templateLabel, templateSelect, slotLabel, slotCombobox, aesSelectLabel, aesSelect, hmacSelectLabel, hmacSelect, timestampLabel, timestampField, passwordLabel, passwordField, separator, paramsTitle);
                if (newValue != null) {
                    Template template = TemplatesSingleton.getInstance().getObservableTemplates().get(newValue);
                    for (TemplateObj templateObj : template.getTemplateObjs()) {
                        parseTemplateObj(templateObj);
                    }
                }
            }
        });
        templateSelect.getSelectionModel().select(null);
        templateSelect.getSelectionModel().select(0);
    }

    private void parseTemplateObj(TemplateObj templateObj) {
        if (templateObj instanceof Text) {
            Text text = (Text) templateObj;
            if (text.getColor().isArg()) addColorArg();
            if (text.getBgColor().isArg()) addColorArg();
            if (text.getSize().isArg()) addSizeArg();
            if (text.getAlignment().isArg()) addAlignmentArg();
            if (text.isArg()) addTextArg();
            return;
        }

        if (templateObj instanceof Header) {
            Header header = (Header) templateObj;
            if (header.getBgColor().isArg()) addColorArg();
            if (header.getTextColor().isArg()) addColorArg();
            if (header.getText().equals(Text.TEXT_FROM_ARGUMENT)) addTextArg();
            return;
        }

        if (templateObj instanceof Footer) {
            Footer footer = (Footer) templateObj;
            if (footer.getBgColor().isArg()) addColorArg();
            if (footer.getTextColor().isArg()) addColorArg();
            if (footer.getText().equals(Text.TEXT_FROM_ARGUMENT)) addTextArg();
            if (footer.getText2().equals(Text.TEXT_FROM_ARGUMENT)) addTextArg();
            return;
        }

        if (templateObj instanceof Stripe) {
            Stripe stripe = (Stripe) templateObj;
            if (stripe.getColor().isArg()) addColorArg();
            return;
        }

        if (templateObj instanceof Rect) {
            Rect rect = (Rect) templateObj;
            if (rect.getColor().isArg()) addColorArg();
            return;
        }
    }

    private void addTextArg() {
        TextField textField = new TextField();
//        TextArea textField = new TextArea();
//        textField.setPrefColumnCount(10);
//        textField.setPrefRowCount(3);
        controlList.add(textField);
        mainNode.add(new Label("Text Arg:"));
        mainNode.add(textField, "wrap");
    }

    private void addColorArg() {
        TemplateColorPicker colorPicker = new TemplateColorPicker(false);
        controlList.add(colorPicker);
        mainNode.add(new Label("Color Arg:"));
        mainNode.add(colorPicker, "wrap");
    }

    private void addSizeArg() {
        TextSizeSelect sizeSelect = new TextSizeSelect();
        controlList.add(sizeSelect);
        mainNode.add(new Label("Size Arg:"));
        mainNode.add(sizeSelect, "wrap");
    }

    private void addAlignmentArg() {
        TextAlignmentSelect alignmentSelect = new TextAlignmentSelect();
        controlList.add(alignmentSelect);
        mainNode.add(new Label("Alignment Arg:"));
        mainNode.add(alignmentSelect, "wrap");
    }

    @Override
    public Service getService() throws Exception {
        service.setTemplateSlot(slotCombobox.getValue());
        service.setAesKey(aesSelect.getValue().getHexValue());
        service.setHmacKey(hmacSelect.getValue().getHexValue());
        service.setTimestamp(timestampField.getValue());
        service.setPin(passwordField.getText());
        service.setParams(new ArrayList<>());
        for (Control control : controlList) {
            if (control instanceof TextField) {
//            if (control instanceof TextArea) {
                service.getParams().add(new HuffmanEncodedParam(((TextField)control).getText()));
//                service.getParams().add(new HuffmanEncodedParam(((TextArea)control).getText()));
            } else if (control instanceof TemplateColorPicker) {
                service.getParams().add(new TemplateColorParam(((TemplateColorPicker) control).getValue()));
            } else if (control instanceof TextAlignmentSelect) {
                service.getParams().add(new TextAlignmentParam(((TextAlignmentSelect)control).getValue()));
            } else if (control instanceof TextSizeSelect) {
                service.getParams().add(new TextSizeParam(((TextSizeSelect)control).getValue()));
            }
        }

        return service;
    }

    @Override
    public Node getNode() {
        return mainNode;
    }
}
