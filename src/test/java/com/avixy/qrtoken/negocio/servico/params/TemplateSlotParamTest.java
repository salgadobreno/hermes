package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.negocio.servico.params.template.TemplateSlotParam;
import org.junit.Test;

import static org.junit.Assert.*;

public class TemplateSlotParamTest {
    TemplateSlotParam param;

    @Test(expected = IllegalArgumentException.class)
    public void testUpperLimit() throws Exception {
        param = new TemplateSlotParam((byte) 18);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowerLimit() throws Exception {
        param = new TemplateSlotParam((byte) -1);
    }

    @Test
    public void testBinaryString() throws Exception {
        param = new TemplateSlotParam((byte) 15);
        assertEquals("01111", param.toBinaryString());

        param = new TemplateSlotParam((byte) 0);
        assertEquals("00000", param.toBinaryString());

        param = new TemplateSlotParam((byte) 7);
        assertEquals("00111", param.toBinaryString());
    }
}