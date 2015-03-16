package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created on 12/02/2015
 *
 * @author I7
 */
public class Footer implements TemplateObj {
    private Stripe stripe;
    private Text text, text2; //TODO rename
    private TemplateColor bgColor, textColor;
    private int FOOTER_HEIGHT = 40;
    private int VERTICAL_MARGIN = Token.DISPLAY_HEIGHT - 40;
    private int FOOTER_Y = Token.DISPLAY_HEIGHT;
    private String textContent, textContent2;

    public Footer(TemplateColor bgColor, TemplateColor textColor, String text1, String text2) {
        this.stripe = new Stripe(FOOTER_Y - FOOTER_HEIGHT, FOOTER_HEIGHT, bgColor);
        this.text = new Text(VERTICAL_MARGIN + 3, textColor, null, Text.Size.SMALL, Text.Alignment.CENTER, text1); //TODO
        this.text2 = new Text(VERTICAL_MARGIN + Text.Size.SMALL.getHeight() + 3, textColor, null, Text.Size.SMALL, Text.Alignment.CENTER, text2); //TODO
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.textContent = text1;
        this.textContent2 = text2;
    }

    @Override
    public void render(GraphicsContext gc) {
        stripe.render(gc);
        text.render(gc);
        text2.render(gc);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle rectangle = new Rectangle(0, FOOTER_Y - FOOTER_HEIGHT, Token.DISPLAY_WIDTH, FOOTER_HEIGHT);
        return rectangle;
    }

    @Override
    public String toBinary() {
        return TemplateFunction.TEMPLATE_FUNCTION_FOOTER.toBinaryString() +
                bgColor.toBinaryString() +
                textColor.toBinaryString() +
                new HuffmanEncodedParam(textContent).toBinaryString() +
                new HuffmanEncodedParam(textContent2).toBinaryString();
    }

    @Override
    public String toString() {
        return "Footer{" +
               '\'' + (textContent + " " + textContent2) +
                '}';
    }

    public void setTextColor(TemplateColor color) {
        textColor = color;
        text.setColor(color);
    }

    public void setBgColor(TemplateColor color){
        bgColor = color;
        stripe.setColor(color);
    }

    public void setText(String text) {
        textContent = text;
        this.text.setText(text);
    }

    public void setText2(String text) {
        textContent2 = text;
        this.text2.setText(text);
    }

    public String getText2() {
        return textContent2;
    }

    public String getText() {
        return textContent;
    }

    public TemplateColor getTextColor() {
        return textColor;
    }

    public TemplateColor getBgColor() {
        return bgColor;
    }
}
