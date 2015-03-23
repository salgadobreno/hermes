package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.TokenHuffman;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
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
 * Created on 24/02/2015
 *
 * @author I7
 */
public class Template {
    private boolean dirty = false;
    private String prevBin = "";
    private String prevName = "";

    private ObservableList<SubTemplate> subTemplates = FXCollections.observableArrayList();

    private String name = "";

    //TODO
    public IntegerProperty screenQtyProperty = new SimpleIntegerProperty(1);
    {
        screenQtyProperty.bind(Bindings.createIntegerBinding(subTemplates::size, subTemplates));
    }

    public void add(TemplateObj templateObj) { //TODO
        subTemplates.get(subTemplates.size()).add(templateObj);
        dirty = true;
    }

    public void clear() {
        storeState();
        for (SubTemplate subTemplate : subTemplates) {
            subTemplate.getTemplateObjs().clear();
        }
        subTemplates.add(new SubTemplate(FXCollections.observableArrayList()));
    }

    public int subTemplateOf(TemplateObj templateObj) {
        for (SubTemplate subTemplate : subTemplates) {
            if (subTemplate.getTemplateObjs().contains(templateObj)) return subTemplates.indexOf(subTemplate) + 1;
        }
        return 1;
    }

    public class SubTemplate extends Template {
        ObservableList<TemplateObj> templateObjs;
        WaitForButton endOfSubTemplate;

        public SubTemplate(ObservableList<TemplateObj> templateObjs) {
            this.templateObjs = templateObjs;
            refreshEndOfSubTemplate();
        }

        private void refreshEndOfSubTemplate() {
            for (TemplateObj templateObj : templateObjs) {
                if (templateObj instanceof WaitForButton && ((WaitForButton)templateObj).getNextAction() == WaitForButton.NextAction.NEW_SCREEN) {
                    endOfSubTemplate = (WaitForButton) templateObj;
                    return;
                }
            }
            endOfSubTemplate = null;
        }

        public void add(TemplateObj templateObj) {
            if (endOfSubTemplate != null) {
                templateObjs.add(templateObjs.indexOf(endOfSubTemplate), templateObj);
            } else {
                templateObjs.add(templateObj);
            }
            if (templateObj instanceof WaitForButton && ((WaitForButton)templateObj).getNextAction() == WaitForButton.NextAction.NEW_SCREEN){
                endOfSubTemplate = (WaitForButton) templateObj;
                Template.this.getSubTemplates().add(new SubTemplate(FXCollections.observableArrayList()));
            }
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
            dirty = true;
            refreshEndOfSubTemplate();
        }

        @Override
        public ObservableList<TemplateObj> getTemplateObjs() {
            return templateObjs;
        }
    }

    public List<TemplateObj> getTemplateObjs() {
        List<TemplateObj> templateObjs = new ArrayList<>();
        for (SubTemplate subTemplate : subTemplates) {
            templateObjs.addAll(subTemplate.getTemplateObjs());
        }
        return templateObjs;
    }

    {
//        templateObjs.addListener(new ListChangeListener<TemplateObj>() {
//            @Override
//            public void onChanged(Change<? extends TemplateObj> c) {
//                subTemplates = new ArrayList<>();
//                ObservableList<TemplateObj> subTemplateObjs = FXCollections.observableArrayList();
//                int i;
//                for (i = 0; i < templateObjs.size(); i++) {
//                    TemplateObj templateObj = templateObjs.get(i);
//                    if (templateObj instanceof WaitForButton && ((WaitForButton) templateObj).getNextAction() == WaitForButton.NextAction.NEW_SCREEN) {
//                        subTemplateObjs.add(templateObj);
//                        subTemplates.add(new SubTemplate(subTemplateObjs, i));
//                        subTemplateObjs = FXCollections.observableArrayList();
//                    } else {
//                        subTemplateObjs.add(templateObj);
//                    }
//                }
//                subTemplates.add(new SubTemplate(subTemplateObjs, i));
//            }
//        });
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
            //Ignored TODO: comentar rs
            };
        };

        return bin + TemplateFunction.TEMPLATE_FUNCTION_EOM.toBinaryString();
    }

    public static Template fromBinary(String bin){ //TODO overload palha
        Template template = new TemplateParser(bin).parse();
        template.storeState();
        template.setDirty(false);
        return template;
    }

    public static Template fromBinary(String name, String bin){
        Template template = new TemplateParser(bin).parse();
        template.setName(name);

        template.storeState();
        template.setDirty(false);
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
            dirty = true;
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

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void restoreState(){ //todo
//        subTemplates = FXCollections.observableArrayList(new TemplateParser(prevBin).getSubTemplates(this));
        subTemplates.setAll(FXCollections.observableArrayList(new TemplateParser(prevBin).getSubTemplates(this)));
        this.name = prevName;
        dirty = false;
    }

    public void storeState(){
        prevName = name;
        prevBin = toBinary();
    }
}

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

    private Text.Alignment getAlignment(){
        Text.Alignment alignment = Text.Alignment.values()[Integer.parseInt(bin.substring(0, ALIGNMENT_LENGTH), 2)];
        bin.delete(0, ALIGNMENT_LENGTH);
        return alignment;
    }

    private WaitForButton.NextAction getNextAction(){
        return WaitForButton.NextAction.values()[getDimension(3)];
    }
}
