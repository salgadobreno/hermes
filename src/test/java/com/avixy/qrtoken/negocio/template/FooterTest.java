package com.avixy.qrtoken.negocio.template;

import org.junit.Test;

import static org.junit.Assert.*;

public class FooterTest {

    @Test
    public void testToBinary() throws Exception {
        String expectedBinary ="0110" + // TEMPLATE_FUNCTION_FOOTER
        "1101" +            // Cor = Amarelo bandeira
        "0000" +            // Cor do texto = Branco

        "00010001" +        // Comprimento 17
        "0001100" +         // Pressione o botão

        "00010101" +        // Comprimento 1
        "1000110" +         //p
        "1000011" +         //a
        "0100111" +         //r
        "1000011" +         //a
        "1110001011" +      //
        "0100111" +         //r
        "1000000" +         //e
        "1011100" +         //c
        "1000000" +         //e
        "1010000" +         //b
        "1000000" +         //e
        "0100111" +         //r
        "1110001011" +      //
        "0111110" +         //o
        "1110001011" +      //
        "1011100" +         //c
        "01001101" +        //ó
        "0011111" +         //d
        "1001111" +         //i
        "0101000" +         //g
        "0111110";          //o
        TemplateColor bgColor = TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_YELLOW);
        TemplateColor textColor = TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_WHITE);
        String text = "Pressione o botão";
        String text2 = "para receber o código";

        Footer footer = new Footer(bgColor, textColor, text, text2);

        assertEquals(footer.toBinary(), expectedBinary);
    }
}