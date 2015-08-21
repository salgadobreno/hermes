package com.avixy.qrtoken.negocio.servico.servicos.password;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdatePukServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    TimestampPolicy timestampPolicy = mock(TimestampPolicy.class);
    UpdatePukService service = new UpdatePukService(headerPolicy, timestampPolicy, passwordPolicy);

    @Before
    public void setUp() throws Exception {
        Long epoch = 1409329200000L;
        service.setPuk("4444");
        service.setNewPuk("3333");
        service.setTimestamp(new Date(epoch));
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testServiceMsg() throws Exception {
        byte[] expectedMsg = {
                0b00110011, // PUK novo:'3'
                0b00110011, // '3'
                0b00110011, // '3'
                0b00110011, // '3'
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
                0b00000000, // \0
        };

        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        Mockito.verify(headerPolicy).getHeader(any(), any());
        Mockito.verify(timestampPolicy).get();
        Mockito.verify(passwordPolicy).get();
    }
}
