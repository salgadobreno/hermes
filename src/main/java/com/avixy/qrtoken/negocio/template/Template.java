package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.TokenHuffman;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.commons.csv.CSVFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Instructions to render one or more QR Token screens
 * <code>Template</code> is made of {@link com.avixy.qrtoken.negocio.template.Template.SubTemplate}s and it will always have at least one,
 * the {@link com.avixy.qrtoken.negocio.template.Template.SubTemplate} holds the {@link com.avixy.qrtoken.negocio.template.TemplateObj}s which
 * both {@link com.avixy.qrtoken.negocio.template.TokenCanvas} or QR Token can render.
 *
 * Created on 24/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class Template {
    private ObservableList<SubTemplate> subTemplates = FXCollections.observableArrayList();
    private String name = "";

    public IntegerProperty screenQtyProperty = new SimpleIntegerProperty(1);

    public Template() {
        screenQtyProperty.bind(Bindings.createIntegerBinding(subTemplates::size, subTemplates));
    }

    public void add(TemplateObj templateObj) { //TODO
        if (subTemplates.size() == 0) {
            subTemplates.add(new SubTemplate(FXCollections.observableArrayList()));
        }
        subTemplates.get(subTemplates.size() - 1).add(templateObj);
    }

    public void clear() {
        for (SubTemplate subTemplate : subTemplates) {
            subTemplate.getTemplateObjs().clear();
        }
        subTemplates.retainAll(subTemplate(1));
    }

    public int subTemplateOf(TemplateObj templateObj) {
        for (SubTemplate subTemplate : subTemplates) {
            if (subTemplate.getTemplateObjs().contains(templateObj)) return subTemplates.indexOf(subTemplate) + 1;
        }
        return 1;
    }

    public List<TemplateObj> getTemplateObjs() {
        List<TemplateObj> templateObjs = new ArrayList<>();
        for (SubTemplate subTemplate : subTemplates) {
            templateObjs.addAll(subTemplate.getTemplateObjs());
        }
        return templateObjs;
    }

    public SubTemplate subTemplate(int subTemplateIndex) {
        if (subTemplates.isEmpty()) subTemplates.add(new SubTemplate(FXCollections.observableArrayList()));
        return subTemplates.get(subTemplateIndex - 1);
    }

    public String toBinary(){
        String bin = "";
        for (TemplateObj templateObj : getTemplateObjs()) {
            try {
                bin += templateObj.toBinary();
            } catch (NullPointerException e) {
            //Ignored: EOM retorna null
            };
        };

        return bin + TemplateFunction.TEMPLATE_FUNCTION_EOM.toBinaryString();
    }

    public static Template fromBinary(String bin){ //TODO overload palha
        Template template = new TemplateParser(bin).parse();
        return template;
    }

    public static Template fromBinary(String name, String bin){
        Template template = new TemplateParser(bin).parse();
        template.setName(name);

        return template;
    }

    public List<SubTemplate> getSubTemplates() {
        return subTemplates;
    }

    public void render(GraphicsContext gc) {
        getTemplateObjs().stream().forEach(x -> {
            x.render(gc);
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!Objects.equals(this.name, name)) {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String toCSV() {
        CSVFormat format = CSVFormat.DEFAULT;
        String[] arr = {getName(), toBinary()};

        return format.format((Object[])arr) + System.lineSeparator();
    }

    public class SubTemplate extends Template {
        ObservableList<TemplateObj> templateObjs;
        WaitForButton terminator;
        BooleanProperty terminatedProperty = new SimpleBooleanProperty(false);

        public SubTemplate(ObservableList<TemplateObj> templateObjs) {
            this.templateObjs = templateObjs;
            checkTerminator();
        }

        public void add(TemplateObj templateObj) {
            if (terminator != null) {
                templateObjs.add(templateObjs.indexOf(terminator), templateObj);
            } else {
                templateObjs.add(templateObj);
            }
            if (templateObj instanceof WaitForButton && ((WaitForButton)templateObj).getNextAction() == WaitForButton.NextAction.NEW_SCREEN){
                terminator = (WaitForButton) templateObj;
                Template.this.getSubTemplates().add(new SubTemplate(FXCollections.observableArrayList()));
            }
            checkTerminator();
        }

        private void checkTerminator() {
            int newScreens = 0;
            for (TemplateObj templateObj : templateObjs) {
                if (templateObj instanceof WaitForButton) {
                    terminatedProperty.set(true);
                    terminator = (WaitForButton) templateObj;
                    return;
                }
            }
            terminatedProperty.set(false);
            terminator = null;
        }

        public void changed() {
            checkTerminator();
            Template.this.refresh();
        }

        @Override
        public void render(GraphicsContext gc) {
            templateObjs.stream().forEach(x -> {
                x.render(gc);
            });
        }

        public void remove(TemplateObj templateObj) {
            templateObjs.remove(templateObj);
            if (templateObj instanceof WaitForButton && ((WaitForButton)templateObj).getNextAction() == WaitForButton.NextAction.NEW_SCREEN) {
                Template.this.subTemplates.remove(Template.this.subTemplates.indexOf(this) + 1);
            }
            checkTerminator();
        }

        @Override
        public ObservableList<TemplateObj> getTemplateObjs() {
            return templateObjs;
        }

        public BooleanProperty terminatedProperty() {
            return terminatedProperty;
        }
    }

    private void refresh() {
        //PS.: hangover code
        List<SubTemplate> keep = new ArrayList<>();
        int newScreenCount = 0;
        for (SubTemplate subTemplate : subTemplates) {
            boolean theEnd = false;
            for (TemplateObj templateObj : subTemplate.getTemplateObjs()) {
                if (templateObj instanceof WaitForButton && ((WaitForButton) templateObj).getNextAction() == WaitForButton.NextAction.NEW_SCREEN) {
                    newScreenCount++;
                }
                if (templateObj instanceof WaitForButton && ((WaitForButton) templateObj).getNextAction() == WaitForButton.NextAction.POWER_OFF) {
                    theEnd = true;
                    break;
                }
            }
            if (!theEnd) {
                keep.add(subTemplate);
            } else {
                keep.add(subTemplate);
                break;
            }
        }
        subTemplates.retainAll(keep);
        for (int i = subTemplates.size() - 1; i < newScreenCount; i++) {
            subTemplates.add(new SubTemplate(FXCollections.observableArrayList()));
        }
    }

}

/**
 * A parser capable of parsing binary <code>String</code>s and building a {@link com.avixy.qrtoken.negocio.template.Template} out of it
 */
class TemplateParser {
    private static final int FUNCTION_LENGTH = 4;
    private static final int COLOR_LENGTH = 4;
    private static final int RGB_COLOR_LENGTH = 16;
    private static final int SIZE_LENGTH = 4;
    private static final int ALIGNMENT_LENGTH = 2;
    private static final int DIMENSION_LENGTH = 8;
    private static final int LENGTH_LENGTH = 8;

    private StringBuilder bin;

    public TemplateParser(String bin) {
        this.bin = new StringBuilder(bin);
    }

    public Template parse() { //TODO
        Template template = new Template();
        ObservableList<TemplateObj> templateObjs = FXCollections.observableArrayList();
        while (bin.length() > 0){
            TemplateObj templateObj = getTemplateObj();
            if (templateObj == null) continue;
            if (templateObj instanceof WaitForButton && ((WaitForButton)templateObj).getNextAction() == WaitForButton.NextAction.NEW_SCREEN){
                templateObjs.add(templateObj);
                template.getSubTemplates().add(template.new SubTemplate(templateObjs));
                templateObjs = FXCollections.observableArrayList();
            } else {
                templateObjs.add(templateObj);
            }
        }
        template.getSubTemplates().add(template.new SubTemplate(templateObjs));

        return template;
    }

    public List<Template.SubTemplate> getSubTemplates(Template template) {
        ObservableList<Template.SubTemplate> subTemplates = FXCollections.observableArrayList();
        ObservableList<TemplateObj> templateObjs = FXCollections.observableArrayList();
        while (bin.length() > 0){
            TemplateObj templateObj = getTemplateObj();
            if (templateObj == null) continue;
            if (templateObj instanceof WaitForButton && ((WaitForButton)templateObj).getNextAction() == WaitForButton.NextAction.NEW_SCREEN){
                templateObjs.add(templateObj);
                subTemplates.add(template.new SubTemplate(templateObjs));
                templateObjs = FXCollections.observableArrayList();
            } else {
                templateObjs.add(templateObj);
            }
        }
        subTemplates.add(template.new SubTemplate(templateObjs));

        return subTemplates;
    }

    private TemplateObj getTemplateObj() { //TODO: templateObjs..
        TemplateFunction templateFunction = getFunction();
        TemplateObj templateObj;

        switch (templateFunction) {
            case TEMPLATE_FUNCTION_STRIPE:
                templateObj = new Stripe(getDimension() * 2, getDimension() * 2, getColor());
                break;
            case TEMPLATE_FUNCTION_RECTANGLE:
                templateObj = new Rect(getDimension(), getDimension(), getDimension() * 2, getDimension() * 2, getColor());
                break;
            case TEMPLATE_FUNCTION_HEADER:
                templateObj = new Header(getColor(), getColor(), getText());
                break;
            case TEMPLATE_FUNCTION_FOOTER:
                templateObj = new Footer(getColor(), getColor(), getText(), getText());
                break;
            case TEMPLATE_FUNCTION_TEXT:
                templateObj = new Text(getDimension() * 2, getColor(), getColor(), getSize(), getAlignment(), getText());
                break;
            case TEMPLATE_FUNCTION_WAIT_FOR_BUTTON:
                templateObj = new WaitForButton(getDimension(5), getNextAction());
                break;
            case TEMPLATE_FUNCTION_EOM:
                templateObj = null;
//                TemplateFunction.TEMPLATE_FUNCTION_EOM ignored
                break;
            default:
                assert false;
                throw new RuntimeException("Err");
        }

        return templateObj;
    }

    private TemplateFunction getFunction(){
        int templateFunctionCode = Integer.parseInt(bin.substring(0, FUNCTION_LENGTH), 2);
        bin.delete(0, FUNCTION_LENGTH);
        return TemplateFunction.values()[templateFunctionCode];
    }

    private int getDimension() {
        //default: 8, param: n
        int dimension = Integer.parseInt(bin.substring(0, DIMENSION_LENGTH), 2);
        bin.delete(0, DIMENSION_LENGTH);
        return dimension;
    }

    private int getDimension(int n) {
        int dimension = Integer.parseInt(bin.substring(0, n), 2);
        bin.delete(0, n);
        return dimension;
    }

    private TemplateColor getColor() {
        TemplateColor templateColor = TemplateColor.get(TemplateColor.Preset.values()[Integer.parseInt(bin.substring(0, COLOR_LENGTH), 2)]);
        bin.delete(0, COLOR_LENGTH);
        if (templateColor.getPreset() == TemplateColor.Preset.TEMPLATE_COLOR_RGB) {
            Color color = getRGBColor();
            templateColor = new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_RGB, color.getRed() * 255, color.getGreen() * 255, color.getBlue() * 255);
        }
        return templateColor;
    }

    private Color getRGBColor() {
        int r,g,b;
        String rgbBin = bin.substring(0, RGB_COLOR_LENGTH);
        bin.delete(0, RGB_COLOR_LENGTH);
        r = 250 * (Integer.parseInt(rgbBin.substring(0,5), 2))/((1 << 5) - 1);
        g = 250 * (Integer.parseInt(rgbBin.substring(6,11), 2))/((1 << 6) - 1);
        b = 250 * (Integer.parseInt(rgbBin.substring(11,16), 2))/((1 << 5) - 1);

        return Color.rgb(r,g,b);
    }

    private String getText(){
        TokenHuffman tokenHuffman = new TokenHuffman();
        int length = Integer.parseInt(bin.substring(0, LENGTH_LENGTH), 2);
        bin.delete(0, LENGTH_LENGTH);
        String text = tokenHuffman.decode(bin.toString(), length);
        bin.delete(0, tokenHuffman.getDecodedBinary().length());
        return text;
    }

    private Text.Size getSize(){
        Text.Size size = Text.Size.values()[Integer.parseInt(bin.substring(0, SIZE_LENGTH), 2)];
        bin.delete(0, SIZE_LENGTH);
        return size;
    }

    private TemplateAlignment getAlignment(){
        TemplateAlignment alignment = TemplateAlignment.get(TemplateAlignment.Preset.values()[Integer.parseInt(bin.substring(0, ALIGNMENT_LENGTH), 2)]);
        bin.delete(0, ALIGNMENT_LENGTH);
        if (alignment.getPreset() == TemplateAlignment.Preset.CUSTOM) {
            int xVal = getDimension();
            alignment.setxPosition(xVal);
        }
        return alignment;
    }

    private WaitForButton.NextAction getNextAction(){
        return WaitForButton.NextAction.values()[getDimension(3)];
    }
}
