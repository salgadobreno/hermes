package com.avixy.qrtoken.negocio.servico.params;

import org.junit.Test;

import static org.junit.Assert.*;

public class TemplateParamTest {
    TemplateParam param;

    @Test(expected = IllegalArgumentException.class)
    public void testUpperLimit() throws Exception {
        param = new TemplateParam((byte) 16);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowerLimit() throws Exception {
        param = new TemplateParam((byte) -1);
    }

    @Test
    public void testBinaryString() throws Exception {
        param = new TemplateParam((byte) 15);
        assertEquals("1111", param.toBinaryString());

        param = new TemplateParam((byte) 0);
        assertEquals("0000", param.toBinaryString());

        param = new TemplateParam((byte) 7);
        assertEquals("0111", param.toBinaryString());
    }
}