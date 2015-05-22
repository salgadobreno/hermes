package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class EraseKtamperServiceTest {
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    EraseKtamperService service = new EraseKtamperService(qrtHeaderPolicy, timestampPolicy, hmacKeyPolicy);

    byte[] expectedOut;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000l;
        service.setTimestamp(new Date(epoch));
        expectedOut = new byte[]{ };

        when(qrtHeaderPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(hmacKeyPolicy.apply(any())).thenReturn(new byte[0]);
    }

    @Test
    public void testServiceMessage() throws Exception {
        assertArrayEquals(expectedOut, service.getMessage());
    }

    @Test
    public void testServiceMessageComPUK() throws Exception {
        expectedOut = new byte[]{ };

        assertArrayEquals(expectedOut, service.getMessage());
    }

    @Test
    public void testOps() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(qrtHeaderPolicy).getHeader(any(), any());
        verify(timestampPolicy).get();
        verify(hmacKeyPolicy).apply(any());
    }
}
