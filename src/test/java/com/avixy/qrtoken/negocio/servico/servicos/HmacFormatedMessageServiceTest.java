package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.RangedTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import com.avixy.qrtoken.negocio.template.Template;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

public class HmacFormatedMessageServiceTest extends TestCase {
    QrtHeaderPolicy qrtHeaderPolicy = mock(QrtHeaderPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = mock(AesCryptedMessagePolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    RangedTimestampPolicy timestampPolicy = mock(RangedTimestampPolicy.class);
    HmacFormatedMessageService service = new HmacFormatedMessageService(qrtHeaderPolicy, timestampPolicy, hmacKeyPolicy, passwordPolicy, aesCryptedMessagePolicy);

    Template template = new Template() {
        @Override
        public String toBinary() {
            return "010101";
        }
    };

    @Override
    public void setUp() throws Exception {
        service.setTemplate(template);
        when(aesCryptedMessagePolicy.get(service)).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
        when(qrtHeaderPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
    }

    public void testBinnaryMsg() throws Exception {
        String expectedMsg = "010101";

        assertArrayEquals(BinaryMsg.get(expectedMsg), service.getMessage());
    }

    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(aesCryptedMessagePolicy).get(service);
        verify(timestampPolicy).get();
        verify(passwordPolicy).get();
        verify(qrtHeaderPolicy).getHeader(any(), any());
        verify(hmacKeyPolicy).apply(Mockito.<byte[]>anyObject());
    }
}