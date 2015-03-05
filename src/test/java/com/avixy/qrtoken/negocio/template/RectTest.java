package com.avixy.qrtoken.negocio.template;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RectTest {

    @Test
    public void testToBinary() throws Exception {
        String expectedBinary = "0100" + // TEMPLATE_FUNCTION_RECTANGLE
        "00001010" +                     // Posicao X = 10
        "01100000" +                     // Largura = 96
        "00010100" +                     // Posicao Y = 20 => 40
        "00011001" +                     // Altura = 25 => 50
        "0101";                          // Cor = Roxa

        TemplateFunction templateFunction = TemplateFunction.TEMPLATE_FUNCTION_RECTANGLE;
        int x = 10;
        int w = 96;
        int h = 50;
        int y = 40;
        TemplateColor color = TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_PURPLE);

        Rect rect = new Rect(x, w, y, h, color);

        assertEquals(rect.toBinary(), expectedBinary);
    }
}