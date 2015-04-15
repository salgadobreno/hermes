package com.avixy.qrtoken.gui.servicos.components.template;

import com.avixy.qrtoken.core.extensions.components.*;
import com.avixy.qrtoken.core.extensions.components.PasswordField;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateColorPicker;
import com.avixy.qrtoken.core.extensions.components.templates.TemplateSelect;
import com.avixy.qrtoken.core.extensions.components.templates.TextAlignmentSelect;
import com.avixy.qrtoken.core.extensions.components.templates.TextSizeSelect;
import com.avixy.qrtoken.core.extensions.customControls.PopOver;
import com.avixy.qrtoken.gui.servicos.components.ServiceCategory;
import com.avixy.qrtoken.gui.servicos.components.ServiceComponent;
import com.avixy.qrtoken.negocio.Token;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.OUTROS)
@AcceptsKey(keyType = KeyType.HMAC)
public class TemplateServiceComponent extends ServiceComponent {
    private TemplateService service;
    private MigPane mainNode = new MigPane();

    /* component elements */
    private Label title = new Label();

    private Label templateLabel = new Label("Aplicação:");
    private TemplateSelect templateSelect = new TemplateSelect();

    private Label aesSelectLabel = new Label("AES Key:");
    private AesKeySelect aesKeySelect = new AesKeySelect();

    private Label hmacSelectLabel = new Label("HMAC Key:");
    private HmacKeySelect hmacKeySelect = new HmacKeySelect();

    private Label timestampLabel = new Label("Timestamp:");
    private TimestampField timestampField = new TimestampField();

    private Separator separator = new Separator();

    private Label passwordLabel = new Label("PIN:");
    private OptionalPasswordField optionalPasswordField = new OptionalPasswordField();

    private Label slotLabel = new Label("Slot:");
    private TemplateSlotSelect templateSlotSelect = new TemplateSlotSelect();

    private Label argsTitle = new Label("Parâmetros da aplicação");

    private TokenCanvas tokenCanvas = new TokenCanvas();
    private Pane canvasPane = new Pane();
    private PopOver canvasPopOver = new PopOver();
    /* \component elements */

    //setup tokenCanvas, pane & popover
    {
        tokenCanvas.setWidth(Token.DISPLAY_WIDTH);
        tokenCanvas.setHeight(Token.DISPLAY_HEIGHT);
        canvasPane.getChildren().add(tokenCanvas);
        canvasPane.setPrefWidth((Token.DISPLAY_WIDTH / 2) + 10);
        canvasPane.setPrefHeight((Token.DISPLAY_HEIGHT / 2) + 10);
        tokenCanvas.setScaleX(0.5);
        tokenCanvas.setScaleY(0.5);
        tokenCanvas.setTranslateY((-Token.DISPLAY_HEIGHT / 4) + 5);
        tokenCanvas.setTranslateX((-Token.DISPLAY_WIDTH / 4) + 5);
        canvasPopOver.setContentNode(canvasPane);
    }

    private List<Control> controlList;
    private MigPane argPane = new MigPane();
    private ScrollPane scrollPane = new ScrollPane();

    /**
     * @param service
     */
    @Inject
    protected TemplateServiceComponent(TemplateService service) {
        super(service);
        this.service = service;

        title.setText(service.getServiceName());
        title.setFont(new Font(18));
        mainNode.add(title, "span");
        mainNode.add(templateLabel);
        mainNode.add(templateSelect, "wrap");
        mainNode.add(slotLabel);
        mainNode.add(templateSlotSelect, "wrap");
        mainNode.add(aesSelectLabel);
        mainNode.add(aesKeySelect, "wrap");
        mainNode.add(hmacSelectLabel);
        mainNode.add(hmacKeySelect, "wrap");
        mainNode.add(timestampLabel);
        mainNode.add(timestampField, "wrap");
        mainNode.add(passwordLabel);
        mainNode.add(optionalPasswordField, "wrap");
        separator.setPrefWidth(280);
        mainNode.add(separator, "span");
        mainNode.add(argsTitle, "span, wrap");
        argsTitle.setFont(new Font(14));

        templateSelect.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Template>() {
            @Override
            public void changed(ObservableValue<? extends Template> observable, Template oldValue, Template newValue) {
                controlList = new ArrayList<>();
                mainNode.getChildren().retainAll(title, templateLabel, templateSelect, slotLabel, templateSlotSelect, aesSelectLabel, aesKeySelect, hmacSelectLabel, hmacKeySelect, timestampLabel, timestampField, passwordLabel, optionalPasswordField, separator, argsTitle, scrollPane, argPane);
                if (newValue != null) {
                    argPane.getChildren().clear();
                    for (TemplateObj templateObj : newValue.getTemplateObjs()) {
                        parseTemplateObj(templateObj);
                    }
                }
            }
        });
        templateSelect.getSelectionModel().select(null);
        templateSelect.getSelectionModel().select(0);

        scrollPane.setPrefWidth(300);
        scrollPane.setPrefHeight(200);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFocusTraversable(false);
        scrollPane.setContent(argPane);
        scrollPane.setStyle("-fx-background-color:transparent;");
        mainNode.add(scrollPane, "span");
    }

    private void parseTemplateObj(TemplateObj templateObj) {
        if (templateObj instanceof Text) {
            Text text = (Text) templateObj;
            if (text.getColor().isArg()) addColorArg(templateObj);
            if (text.getBgColor().isArg()) addColorArg(templateObj);
            if (text.getSize().isArg()) addSizeArg(templateObj);
            if (text.getAlignment().isArg()) addAlignmentArg(templateObj);
            if (text.isArg()) addTextArg(templateObj);
            return;
        }

        if (templateObj instanceof Header) {
            Header header = (Header) templateObj;
            if (header.getBgColor().isArg()) addColorArg(templateObj);
            if (header.getTextColor().isArg()) addColorArg(templateObj);
            if (header.getText().equals(Text.TEXT_FROM_ARGUMENT)) addTextArg(templateObj);
            return;
        }

        if (templateObj instanceof Footer) {
            Footer footer = (Footer) templateObj;
            if (footer.getBgColor().isArg()) addColorArg(templateObj);
            if (footer.getTextColor().isArg()) addColorArg(templateObj);
            if (footer.getText().equals(Text.TEXT_FROM_ARGUMENT)) addTextArg(templateObj);
            if (footer.getText2().equals(Text.TEXT_FROM_ARGUMENT)) addTextArg(templateObj);
            return;
        }

        if (templateObj instanceof Stripe) {
            Stripe stripe = (Stripe) templateObj;
            if (stripe.getColor().isArg()) addColorArg(templateObj);
            return;
        }

        if (templateObj instanceof Rect) {
            Rect rect = (Rect) templateObj;
            if (rect.getColor().isArg()) addColorArg(templateObj);
        }
    }

    private void addTextArg(TemplateObj templateObj) {
        TextField textField = new TextField();
        controlList.add(textField);
        argPane.add(new Label("Text Arg:"));
        argPane.add(textField, "wrap");
        attachCanvasPopOverTo(textField, templateObj);
    }

    private void addColorArg(TemplateObj templateObj) {
        TemplateColorPicker colorPicker = new TemplateColorPicker(false);
        controlList.add(colorPicker);
        argPane.add(new Label("Color Arg:"));
        argPane.add(colorPicker, "wrap");
        attachCanvasPopOverTo(colorPicker, templateObj);
    }

    private void addSizeArg(TemplateObj templateObj) {
        TextSizeSelect sizeSelect = new TextSizeSelect();
        controlList.add(sizeSelect);
        argPane.add(new Label("Size Arg:"));
        argPane.add(sizeSelect, "wrap");

        attachCanvasPopOverTo(sizeSelect, templateObj);
    }

    private void addAlignmentArg(TemplateObj templateObj) {
        TextAlignmentSelect alignmentSelect = new TextAlignmentSelect();
        controlList.add(alignmentSelect);
        argPane.add(new Label("Alignment Arg:"));
        argPane.add(alignmentSelect, "wrap");

        attachCanvasPopOverTo(alignmentSelect, templateObj);
    }

    private void attachCanvasPopOverTo(Control control, TemplateObj templateObj) {
        control.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && control.isVisible()) {
                    canvasPopOver = new PopOver(canvasPane);
                    tokenCanvas.setTemplate(templateSelect.getValue());
                    tokenCanvas.highlight(templateObj);
                    canvasPopOver.show(control);
                    scrollPane.vvalueProperty().addListener((observable1, oldValue1, newValue1) -> {
                        canvasPopOver.hide();
                    });
                } else {
                    canvasPopOver.hide();
                }
            }
        });
    }

    @Override
    public Service getService() throws Exception {
        service.setTemplateSlot(templateSlotSelect.getValue());
        service.setAesKey(aesKeySelect.getValue().getHexValue());
        service.setHmacKey(hmacKeySelect.getValue().getHexValue());
        service.setTimestamp(timestampField.getValue());
        service.togglePasswordOptional(optionalPasswordField.isOptional());
        service.setPin(optionalPasswordField.getText());
        service.setParams(new ArrayList<>());
        for (Control control : controlList) {
            if (control instanceof TextField) {
                service.getParams().add(new HuffmanEncodedParam(((TextField)control).getText()));
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
