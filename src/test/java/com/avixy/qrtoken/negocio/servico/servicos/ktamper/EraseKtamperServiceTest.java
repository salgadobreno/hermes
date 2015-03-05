package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;

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
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    EraseKtamperService service = new EraseKtamperService(qrtHeaderPolicy, timestampPolicy, passwordPolicy);

    byte[] expectedOut;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000l;
        service.setPin("1234");
        service.setTimestamp(new Date(epoch));
        expectedOut = new byte[]{ };

        when(qrtHeaderPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
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
        service.getQrs(mock(QrSetup.class));
        verify(qrtHeaderPolicy).getHeader(service);
        verify(timestampPolicy).get();
    }
}
