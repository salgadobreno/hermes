package com.avixy.qrtoken.negocio.template;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeaderTest {

    @Test
    public void testToBinary() throws Exception {
        String expectedBinary =  "0101" + // TEMPLATE_FUNCTION_HEADER
        "0110" + // Cor = Azul bandeira
        "0000" + // Cor do texto = Branco
        "00001011" + // Comprimento do texto = 11
        "0000010"; // Banco Avixy
        TemplateFunction templateFunction = TemplateFunction.TEMPLATE_FUNCTION_HEADER;
        TemplateColor bgColor = TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_FLAG_BLUE);
        TemplateColor textColor = TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_WHITE);
        String text = "Banco Avixy";

        Header header = new Header(bgColor, textColor, text);

        assertEquals(header.toBinary(), expectedBinary);
    }
}