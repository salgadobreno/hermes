package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created on 12/02/2015
 *
 * @author I7
 */
public class Footer implements TemplateObj {
    private Stripe stripe;
    private Text text, text2; //TODO rename essa merda
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
    public String toBinary() {
        return TemplateFunction.TEMPLATE_FUNCTION_FOOTER.toBinaryString() +
                bgColor.toBinaryString() +
                textColor.toBinaryString() +
                new HuffmanEncodedParam(textContent).toBinaryString() +
                new HuffmanEncodedParam(textContent2).toBinaryString();
    }
}
