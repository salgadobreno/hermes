package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.TokenHuffman;
import javafx.scene.canvas.GraphicsContext;
import org.apache.commons.csv.CSVFormat;

import java.util.ArrayList;
import java.util.LinkedList;
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

    private List<TemplateObj> templateObjs = new LinkedList<>();
    private String name = "";

    public void add(TemplateObj templateObj) {
        templateObjs.add(templateObj);
        dirty = true;
    }

    public String toBinary(){
        String bin = "";
        for (TemplateObj templateObj : templateObjs) {
            try {
                bin += templateObj.toBinary();
            } catch (NullPointerException ignored) {};
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

    public List<TemplateObj> getTemplateObjs() { //TODO paia?
        return templateObjs;
    }

    public void render(GraphicsContext gc) {
        templateObjs.stream().forEach(x -> {
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
        templateObjs = new TemplateParser(prevBin).getTemplateObjs();
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
    private static final int SIZE_LENGTH = 4;
    private static final int ALIGNMENT_LENGTH = 2;
    private static final int DIMENSION_LENGTH = 8;
    private static final int LENGTH_LENGTH = 8;

    private String bin;

    public TemplateParser(String bin) {
        this.bin = bin;
    }

    public Template parse() { //TODO
        Template template = new Template();
        while (bin.length() > 0){
            bin = getTemplateObj(template.getTemplateObjs());
        }

        return template;
    }

    public List<TemplateObj> getTemplateObjs() {
        List<TemplateObj> templateObjs = new ArrayList<>();
        while (bin.length() > 0){
            bin = getTemplateObj(templateObjs);
        }
        return templateObjs;
    }

    private String getTemplateObj(List<TemplateObj> templateObjs) { //TODO: templateObjs..
        TemplateObj templateObj = new TemplateObj() { //TODO: ineficiente
            @Override
            public void render(GraphicsContext gc) { }
            @Override
            public String toBinary() { return ""; }
        };
        TemplateFunction templateFunction = getFunction();

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
            //TemplateFunction.TEMPLATE_FUNCTION_EOM ignored, already added automatically
        }
        templateObjs.add(templateObj);

        return bin;
    }

    private TemplateFunction getFunction(){
        int templateFunctionCode = Integer.parseInt(bin.substring(0, FUNCTION_LENGTH), 2);
        bin = bin.substring(FUNCTION_LENGTH);
        return TemplateFunction.values()[templateFunctionCode];
    }

    private int getDimension() {
        int dimension = Integer.parseInt(bin.substring(0, DIMENSION_LENGTH), 2);
        bin = bin.substring(DIMENSION_LENGTH);
        return dimension;
    }

    private TemplateColor getColor() {
        TemplateColor templateColor = TemplateColor.get(TemplateColor.Preset.values()[Integer.parseInt(bin.substring(0, COLOR_LENGTH), 2)]);
        bin = bin.substring(COLOR_LENGTH);
        return templateColor;
    }

    private String getText(){
        TokenHuffman tokenHuffman = new TokenHuffman();
        int length = Integer.parseInt(bin.substring(0, LENGTH_LENGTH), 2);
        bin = bin.substring(LENGTH_LENGTH);
        String text = tokenHuffman.decode(bin, length);
        bin = bin.substring(tokenHuffman.getDecodedBinary().length());
        return text;
    }

    private Text.Size getSize(){
        Text.Size size = Text.Size.values()[Integer.parseInt(bin.substring(0, SIZE_LENGTH), 2)];
        bin = bin.substring(SIZE_LENGTH);
        return size;
    }

    private Text.Alignment getAlignment(){
        Text.Alignment alignment = Text.Alignment.values()[Integer.parseInt(bin.substring(0, ALIGNMENT_LENGTH), 2)];
        bin = bin.substring(ALIGNMENT_LENGTH);
        return alignment;
    }
}
