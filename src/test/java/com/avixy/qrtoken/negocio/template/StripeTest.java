package com.avixy.qrtoken.negocio.template;

import org.junit.Test;

import static org.junit.Assert.*;

public class StripeTest {

    @Test
    public void testToBinary() throws Exception {
        String expectedBinary =  "0000" + // TEMPLATE_FUNCTION_STRIPE
        "00111100" +                      // Coord Y =  60 -> 120
        "00101000" +                      // altura H = 40 -> 80
        "0111";                           // Cor = Azul Claro
        TemplateColor bgColor = TemplateColor.get(TemplateColor.Preset.TEMPLATE_COLOR_LIGHT_BLUE);
        int y = 120;
        int h = 80;

        Stripe stripe = new Stripe(y, h, bgColor);

        assertEquals(stripe.toBinary(), expectedBinary);
    }
}