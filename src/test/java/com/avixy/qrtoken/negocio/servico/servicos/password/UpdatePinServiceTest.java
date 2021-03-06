package com.avixy.qrtoken.negocio.servico.servicos.password;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdatePinServiceTest {
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    UpdatePinService service = new UpdatePinService(qrtHeaderPolicy, timestampPolicy, passwordPolicy);
    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        Long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b00110001, // PIN novo:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
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

        service.setPin("4321");
        service.setNewPin("1234");
        service.setTimestamp(new Date(epoch));

        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(qrtHeaderPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }


    @Test
    public void testServiceMessage() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testOps() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(qrtHeaderPolicy).getHeader(any(), any());
        verify(timestampPolicy).get();
        verify(passwordPolicy).get();
    }
}
