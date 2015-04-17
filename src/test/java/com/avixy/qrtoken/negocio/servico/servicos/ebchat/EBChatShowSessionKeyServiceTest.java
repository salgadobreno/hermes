package com.avixy.qrtoken.negocio.servico.servicos.ebchat;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class EBChatShowSessionKeyServiceTest {
    HeaderPolicy headerPolicy = mock(HeaderPolicy.class);
    AesCryptedMessagePolicy messagePolicy = mock(AesCryptedMessagePolicy.class);
    TimestampPolicy timestampPolicy = mock(TimestampPolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    EBChatShowSessionKeyService service = new EBChatShowSessionKeyService(headerPolicy, timestampPolicy, messagePolicy, hmacKeyPolicy, passwordPolicy);

    @Before
    public void setUp() throws Exception {
        byte[] genericKey = new byte[32];

        service.setHmacKey(genericKey);
        service.setAesKey(genericKey);

        service.setChallenge("1234");
        service.setSessionSecrecyKey(genericKey);
        service.setSessionAuthKey(genericKey);

        when(messagePolicy.get(any())).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(any())).thenReturn(new byte[0]);
        when(hmacKeyPolicy.apply(any())).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        String expectedMessage = "00000100" + //4 chars
                "00110001001100100011001100110100" + // 1234
                "00101" + //keylength 128 - 32bytes
                //key secrecy
                "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                "1111000101001100" + //crc
                "00101" + //keylength 128 - 32bytes
                //key auth
                "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                "1111000101001100"//crc
                ;

        assertArrayEquals(BinaryMsg.get(expectedMessage), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(headerPolicy).getHeader(any());
        verify(messagePolicy).get(any());
        verify(messagePolicy).setKey(any());
        verify(timestampPolicy).get();
        verify(hmacKeyPolicy).apply(any());
    }
}