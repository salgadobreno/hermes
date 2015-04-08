package com.avixy.qrtoken.negocio.template;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextTest {

    @Test
    public void testToBinary() throws Exception {
        String expectedBinary =  "0001" +   // TEMPLATE_FUNCTION_TEXT
        "00011110" +                        // Coord Y = 30 -> 60
        "0100" +                            // Cor = Preta
        "0010" +                            // Fundo = Cinza médio
        "0010" +                            // Fonte = Small
        "00" +                              // Alinhamento a esquerda
        "00001000" +                        // Comprimento = 8
        "1010111" +                         // D
        "1001010" +                         // E
        "1000001" +                         // B
        "1001101" +                         // I
        "0110010" +                         // T
        "1110001010" +                      // A
        "1010111" +                         // D
        "0101100";                          // O
        int y = 60;
        TemplateColor textColor = new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_BLACK);
        TemplateColor bgColor = new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_GRAY);
        Text.Size size = Text.Size.SMALL;
        String textArg = "DEBITADO";
        TemplateAlignment alignment = TemplateAlignment.get(TemplateAlignment.Preset.LEFT);

        Text text = new Text(y, textColor, bgColor, size, alignment, textArg);

        assertEquals(text.toBinary(), expectedBinary);
    }

    @Test
    public void testBinaryWithCustomAlignment() throws Exception {
        String expectedBinary =  "0001" +   // TEMPLATE_FUNCTION_TEXT
                "00011110" +                        // Coord Y = 30 -> 60
                "0100" +                            // Cor = Preta
                "0010" +                            // Fundo = Cinza médio
                "0010" +                            // Fonte = Small
                "00" +                              // Alinhamento a esquerda
                "00001000" +                        // Comprimento = 8
                "1010111" +                         // D
                "1001010" +                         // E
                "1000001" +                         // B
                "1001101" +                         // I
                "0110010" +                         // T
                "1110001010" +                      // A
                "1010111" +                         // D
                "0101100";                          // O
        int y = 60;
        TemplateColor textColor = new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_BLACK);
        TemplateColor bgColor = new TemplateColor(TemplateColor.Preset.TEMPLATE_COLOR_GRAY);
        Text.Size size = Text.Size.SMALL;
        String textArg = "DEBITADO";
        TemplateAlignment alignment = TemplateAlignment.get(TemplateAlignment.Preset.LEFT);

        Text text = new Text(y, textColor, bgColor, size, alignment, textArg);

        assertEquals(text.toBinary(), expectedBinary);
    }
}