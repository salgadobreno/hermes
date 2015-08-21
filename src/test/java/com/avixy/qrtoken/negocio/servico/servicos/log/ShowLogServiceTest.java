package com.avixy.qrtoken.negocio.servico.servicos.log;

import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ShowLogServiceTest {
    ShowLogService service = new ShowLogService(new QrtHeaderPolicy());

    @Test
    public void testMessage() throws Exception {
        byte[] expectedMsg = {};
        assertArrayEquals(expectedMsg, service.getMessage());
    }
}