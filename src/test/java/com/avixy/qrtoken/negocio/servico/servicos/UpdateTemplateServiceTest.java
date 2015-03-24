package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.avixy.qrtoken.negocio.template.Template;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

public class UpdateTemplateServiceTest {
    Template template = new Template() {
        @Override
        public String toBinary() {
            return "00000001";
        }
    };
    HeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    UpdateTemplateService service = new UpdateTemplateService(headerPolicy, timestampPolicy, hmacKeyPolicy, passwordPolicy);

    String expectedBinaryMsg;

    @Before
    public void setUp() throws Exception {
        expectedBinaryMsg = "0001" + //slot 1
                "00000001"; //template

        service.setTemplate(template);
        service.setTemplateSlot((byte) 1);

        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(BinaryMsg.get(expectedBinaryMsg), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(timestampPolicy).get();
        verify(passwordPolicy).get();
        verify(hmacKeyPolicy).apply(any());
    }
}