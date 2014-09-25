package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class TemplateParamTest {
    TemplateParam param;

    @Test(expected = IllegalArgumentException.class)
    public void testUpperLimit() throws Exception {
        param = new TemplateParam((byte) 32);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowerLimit() throws Exception {
        param = new TemplateParam((byte) -1);
    }

    @Test
    public void testBinaryString() throws Exception {
        param = new TemplateParam((byte) 31);
        assertEquals("11111", param.toBinaryString());

        param = new TemplateParam((byte) 0);
        assertEquals("00000", param.toBinaryString());

        param = new TemplateParam((byte) 16);
        assertEquals("10000", param.toBinaryString());
    }
}