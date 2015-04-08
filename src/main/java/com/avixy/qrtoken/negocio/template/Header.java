package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.Token;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * Created on 12/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class Header implements TemplateObj {
    static final int HEADER_HEIGHT = 40;
    static final int VERTICAL_MARGIN = 10;
    private Stripe stripe;
    private Text text;
    private TemplateColor bgColor, textColor;
    private String textContent;

    public Header(TemplateColor bgColor, TemplateColor textColor, String text) {
        this.stripe = new Stripe(0, HEADER_HEIGHT, bgColor);
        this.text = new Text(VERTICAL_MARGIN, textColor, bgColor, Text.Size.LARGE, TemplateAlignment.get(TemplateAlignment.Preset.CENTER), text);
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.textContent = text;
    }

    @Override
    public void render(GraphicsContext gc) {
        stripe.render(gc);
        text.render(gc);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle rectangle = new Rectangle(0, 0 , Token.DISPLAY_WIDTH, HEADER_HEIGHT);
        return rectangle;
    }

    @Override
    public String toBinary() {
        return TemplateFunction.TEMPLATE_FUNCTION_HEADER.toBinaryString() +
                bgColor.toBinaryString() +
                textColor.toBinaryString() +
                new HuffmanEncodedParam(textContent).toBinaryString();
    }

    @Override
    public String toString() {
        return "Header{" +
               '\'' + textContent + '\'' +
                '}';
    }

    public void setTextColor(TemplateColor color) {
        this.textColor = color;
        text.setColor(color);
    }

    public void setBgColor(TemplateColor color) {
        this.bgColor = color;
        text.setBgColor(color);
        stripe.setColor(color);
    }

    public void setText(String text) {
        this.textContent = text;
        this.text.setText(text);
    }

    public String getText() {
        return textContent;
    }

    public TemplateColor getBgColor() {
        return bgColor;
    }

    public TemplateColor getTextColor() {
        return textColor;
    }
}
