package com.avixy.qrtoken.negocio.servico.servicos.log;

import com.avixy.qrtoken.core.HermesModule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShowLogServiceTest {
    ShowLogService service = HermesModule.getInjector().getInstance(ShowLogService.class);

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(57, service.getServiceCode());
    }

    @Test
    public void testMessage() throws Exception {
        byte[] expectedMsg = {};
        assertArrayEquals(expectedMsg, service.getMessage());
    }
}