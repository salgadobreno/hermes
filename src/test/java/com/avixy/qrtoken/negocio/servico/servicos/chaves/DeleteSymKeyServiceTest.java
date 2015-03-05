package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

public class DeleteSymKeyServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    DeleteSymKeyService service = new DeleteSymKeyAvixyService(headerPolicy, timestampPolicy, hmacKeyPolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{ };

        service.setTimestamp(new Date(epoch));
        service.setHmacKey("bla".getBytes());

        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(hmacKeyPolicy.apply(Matchers.<byte[]>any())).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testServiceMsg() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testOps() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(hmacKeyPolicy).apply(Matchers.<byte[]>any());
        verify(timestampPolicy).get();
        verify(headerPolicy).getHeader(service);
    }
}