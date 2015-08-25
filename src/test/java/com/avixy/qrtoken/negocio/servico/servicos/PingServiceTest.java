package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created on 03/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class PingServiceTest {
    QrtHeaderPolicy headerPolicy = Mockito.mock(QrtHeaderPolicy.class);
    PingService pingService = new PingService(headerPolicy);

    @Test
    public void testMessage() throws Exception {
        byte[] expectedBytes = {}; // Esse serviço é apenas o seu header

        assertArrayEquals(expectedBytes, pingService.getMessage());
    }
}
