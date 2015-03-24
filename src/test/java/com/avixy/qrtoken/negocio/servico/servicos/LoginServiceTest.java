package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.TokenHuffman;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.banking.LoginService;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

public class LoginServiceTest {
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = mock(AesCryptedMessagePolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);

    LoginService service = new LoginService(qrtHeaderPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, passwordPolicy);

    String loginCode = "885471";

    @Before
    public void setUp() throws Exception {
        service.setTemplateSlot((byte) 1);
        service.setPin("1234");
        service.setLoginCode(loginCode);
        service.setTimestamp(new Date(1409329200000L));
        when(aesCryptedMessagePolicy.get(service)).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
        when(qrtHeaderPolicy.getHeader(service)).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        String huffmanCode = new TokenHuffman().encode(loginCode);
        String expectedBinaryString = "" +
                "0001" + //template 1
                "00000110" + //length 6
                huffmanCode; //codigo de login 885471

        assertArrayEquals(new BinaryMsg(expectedBinaryString).toByteArray(), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(aesCryptedMessagePolicy).get(service);
        verify(timestampPolicy).get();
        verify(passwordPolicy).get();
        verify(qrtHeaderPolicy).getHeader(service);
        verify(hmacKeyPolicy).apply(Mockito.<byte[]>anyObject());
    }
}