package com.avixy.qrtoken.negocio.template;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TemplateTest {
    String expectedBinary;
    Template template;

    @Before
    public void setUp() throws Exception {
        expectedBinary = "0101" + // TEMPLATE_FUNCTION_HEADER
                "0110" + // Cor = Azul bandeira
                "0000" + // Cor do texto = Branco
                "00001011" + // Comprimento do texto = 11
                "0000010" + // Banco Avixy
                "0001" + // TEMPLATE_FUNCTION_TEXT
                "00011110" + // Coord Y = 30 -> 60
                "0100" + // Cor = Preta
                "0000" + // Fundo = Branco
                "0010" + // Fonte = Small
                "00" + // Alinhado a esquerda
                "00110000" + // Comprimento = 48
                "0101001" + // U
                "0110000" + // t
                "1001111" + // i
                "0111100" + // l
                "1001111" + // i
                "0111011" + // z
                "1000000" + // e
                "1110001011" + //
                "1000000" + // e
                "0111101" + // s
                "0110000" + // t
                "1000000" + // e
                "1110001011" + //
                "1011100" + // c
                "01001101" + // ó
                "0011111" +  // d
                "1001111" +  // i
                "0101000" +  // g
                "0111110" +  // o
                "1100011010" + // \n
                "1000110" +  // p
                "1000011" +  // a
                "0100111" +  // r
                "1000011" +  // a
                "1110001011" + //
                "1000011" + // A
                "0011110" + // u
                "0110000" + // t
                "0111110" + // o
                "0100111" + // r
                "1001111" + // i
                "0111011" + // z
                "1000011" + // a
                "0100111" + // r
                "1110001011" + //
                "0111101" + // s
                "0011110" + // u
                "1000011" + // a
                "1100011010" + // \n
                "0001001" + // Transação

                "0000" + // TEMPLATE_FUNCTION_STRIPE
                "01000110" + // Coord Y = 70 -> 140
                "00011101" + // altura H = 29
                "0111" + // Cor = Azul claro

                "0001" + // TEMPLATE_FUNCTION_TEXT
                "01001101" + // Coord Y = 77 -> 154
                "0100" + // Cor = Preta
                "0111" + // Fundo = Azul claro
                "0101" + // Fonte = Huge
                "01" + // Alinhado ao centro
                "00001000" +                        // Comprimento = 8
                "1010111" +                         // D
                "1001010" +                         // E
                "1000001" +                         // B
                "1001101" +                         // I
                "0110010" +                         // T
                "1110001010" +                      // A
                "1010111" +                         // D
                "0101100" +                         // O
                "0110" + // TEMPLATE_FUNCTION_FOOTER
                "1101" + // Cor = Amarelo bandeira
                "0100" + // Cor do texto = Branco
                "00010001" + // Comprimento 17
                "0001100" + // Pressione o botão
                "00010101" + // Comprimento 21
                "1000110" + // p
                "1000011" + // a
                "0100111" + // r
                "1000011" + // a
                "1110001011" + //
                "0011111" + // d
                "1000000" + // e
                "0111101" + // s
                "0111100" + // l
                "1001111" + // i
                "0101000" + // g
                "1000011" + // a
                "0100111" + // r
                "1110001011" + //
                "0111110" +  // o
                "1110001011" + //
                "11111000" + // Token
                "0100" + // TEMPLATE_FUNCTION_RECTANGLE
                "00001010" +                     // Posicao X = 10
                "01100000" +                     // Largura = 96
                "00010100" +                     // Posicao Y = 20 => 40
                "00011001" +                     // Altura = 25 => 50
                "0101" + //cor roxa
                "1111"; //EOM
//        "11111111" + // Comprimento = Por argumento
//        "0011010010"; // Texto por argumento
        Header header = new Header(
                TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_FLAG_BLUE),
                TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_WHITE),
                "Banco Avixy"
        );
        Text text = new Text(60,
                TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_BLACK),
                TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_WHITE),
                Text.Size.SMALL,
                Text.Alignment.LEFT,
                "Utilize este código\npara autorizar sua\nTransação"
        );
        Stripe stripe = new Stripe(
                140,
                29 * 2,
                TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_LIGHT_BLUE)
        );
        Text text2 = new Text(154,
                TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_BLACK),
                TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_LIGHT_BLUE),
                Text.Size.HUGE,
                Text.Alignment.CENTER,
                "DEBITADO"
        );
        Footer footer = new Footer(
                TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_YELLOW),
                TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_BLACK),
                "Pressione o botão",
                "para desligar o Token"
        );
        Rect rect = new Rect(
                10, 96, 40, 50, TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_PURPLE)
        );

        template = new Template();
        template.add(header);
        template.add(text);
        template.add(stripe);
        template.add(text2);
        template.add(footer);
        template.add(rect);
    }

    @Test
    public void testToBinary() throws Exception {
        assertEquals(expectedBinary, template.toBinary());
    }

    @Test
    public void testFromBinary() throws Exception {
        Template template1 = Template.fromBinary(expectedBinary);
        List<TemplateObj> templateObjs = template1.getTemplateObjs();
        assertTrue(templateObjs.get(0) instanceof Header);
        assertTrue(templateObjs.get(1) instanceof Text);
        assertTrue(templateObjs.get(2) instanceof Stripe);
        assertTrue(templateObjs.get(3) instanceof Text);
        assertTrue(templateObjs.get(4) instanceof Footer);
        assertTrue(templateObjs.get(5) instanceof Rect);
        assertEquals(expectedBinary, Template.fromBinary(expectedBinary).toBinary());
    }
}