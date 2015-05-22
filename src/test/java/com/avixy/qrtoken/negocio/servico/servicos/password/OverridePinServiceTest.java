package com.avixy.qrtoken.negocio.servico.servicos.password;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class OverridePinServiceTest {
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    OverridePinService service = new OverridePinService(qrtHeaderPolicy, timestampPolicy, passwordPolicy);

    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                0b00000100, // length 4
                0b00110001, // PIN:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
        };
        service.setPin("1234");
        service.setPuk("4444");
        service.setTimestamp(new Date(epoch));

        when(qrtHeaderPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(qrtHeaderPolicy).getHeader(any(), any());
        verify(timestampPolicy).get();
        verify(passwordPolicy).get();
    }
}
