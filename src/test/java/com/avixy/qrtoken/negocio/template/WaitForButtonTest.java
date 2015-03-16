package com.avixy.qrtoken.negocio.template;

import org.junit.Test;

import static org.junit.Assert.*;

public class WaitForButtonTest {
    @Test
    public void testToBinaryString() throws Exception {
        String expectedBinary = "0011" + // TEMPLATE_FUNCTION_WAIT_FOR_BUTTON
                "00101" +                // 5 Segundos de tempo mínimo
                "001";                   // Troca de tela
        WaitForButton waitForButton = new WaitForButton(5, WaitForButton.NextAction.NEW_SCREEN);

        assertEquals(expectedBinary, waitForButton.toBinary());
    }
}