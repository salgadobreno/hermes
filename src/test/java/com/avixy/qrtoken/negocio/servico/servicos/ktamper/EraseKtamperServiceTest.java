package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.negocio.servico.operations.PinPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class EraseKtamperServiceTest {
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    PinPolicy pinPolicy = mock(PinPolicy.class);
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    EraseKtamperService service = new EraseKtamperService(qrtHeaderPolicy, timestampPolicy, pinPolicy);

    byte[] expectedOut;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000l;
        service.setPin("1234");
        service.setTimestamp(new Date(epoch));
        expectedOut = new byte[]{ };
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(21, service.getServiceCode());
    }

    @Test
    public void testServiceMessage() throws Exception {
        assertArrayEquals(expectedOut, service.getMessage());
    }

    @Test
    public void testServiceMessageComPUK() throws Exception {
        /* "é obrigatório OU o PIN OU PUK" */
        expectedOut = new byte[]{ };

        service.setPuk("4444");
        assertArrayEquals(expectedOut, service.getMessage());
    }

    @Test
    public void testOps() throws Exception {
        service.run();
        verify(qrtHeaderPolicy).getHeader(service);
        verify(timestampPolicy).get();
    }
}
