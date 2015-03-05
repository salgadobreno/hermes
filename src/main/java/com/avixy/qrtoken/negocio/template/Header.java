package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created on 12/02/2015
 *
 * @author I7
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
        this.text = new Text(VERTICAL_MARGIN, textColor, null, Text.Size.LARGE, Text.Alignment.CENTER, text);
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
    public String toBinary() {
        return TemplateFunction.TEMPLATE_FUNCTION_HEADER.toBinaryString() +
                bgColor.toBinaryString() +
                textColor.toBinaryString() +
                new HuffmanEncodedParam(textContent).toBinaryString();
    }
}
