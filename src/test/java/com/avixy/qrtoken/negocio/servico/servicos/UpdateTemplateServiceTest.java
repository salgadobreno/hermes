package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.*;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.avixy.qrtoken.negocio.template.Template;
import com.avixy.qrtoken.negocio.template.TemplateSize;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateTemplateServiceTest {
    Template template = new Template() {
        @Override
        public String toBinary() {
            return "00000001"; //[1]
        }
    };
    HeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    TimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = mock(AesCryptedMessagePolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    RandomGenerator randomGenerator = new RandomGenerator() {
        @Override
        public void nextBytes(byte[] bytes) {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = -1;
            }
        }
    };
    UpdateTemplateService service = new UpdateTemplateService(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy, randomGenerator);

    String expectedBinaryMsg;

    @Before
    public void setUp() throws Exception {
        service.setTemplateSlot((byte) 0);
        service.setTemplate(template);

        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
        when(aesCryptedMessagePolicy.get(any())).thenReturn(new byte[0]);
    }

    @Test
    public void testShortSlotMessage() throws Exception {
        service.setTemplateSlot((byte) 0);
        expectedBinaryMsg = StringUtils.rightPad("00000" + "00000001", (TemplateSize.SHORT.getSize() * 8), '1');

        assertArrayEquals(BinaryMsg.get(expectedBinaryMsg), service.getMessage());
        assertEquals(220, service.getMessage().length);
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(timestampPolicy).get();
        verify(hmacKeyPolicy).apply(any());
        verify(aesCryptedMessagePolicy).get(any());
    }
}