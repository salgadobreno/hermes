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

public class EBChatSessionKeyServiceTest {
    HeaderPolicy headerPolicy = mock(HeaderPolicy.class);
    AesCryptedMessagePolicy messagePolicy = mock(AesCryptedMessagePolicy.class);
    TimestampPolicy timestampPolicy = mock(TimestampPolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    EBChatSessionKeyService service = new EBChatSessionKeyService(headerPolicy, timestampPolicy, messagePolicy, hmacKeyPolicy, passwordPolicy);

    byte[] genericKey = new byte[32];

    @Before
    public void setUp() throws Exception {

        service.setHmacKey(genericKey);
        service.setAesKey(genericKey);


        when(messagePolicy.get(any())).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
        when(hmacKeyPolicy.apply(any())).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        service.setChallenge("1234");
        service.setSessionSecrecyKey(genericKey);
        service.setSessionAuthKey(genericKey);


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
    public void testMessage256() throws Exception {
        byte[] key = new byte[32];
        for (int i = 0; i < key.length; i++) {
            key[i] = (byte)0x55;
        }
        service.setSessionAuthKey(key);
        service.setSessionSecrecyKey(key);
        service.setChallenge("1234");
        String expectedMessage = "00000100" + "00110001" + "00110010" + "00110011" + "00110100" + // Length + Challenge "1234"
        "00101" +		// KEY_LENGTH_256
        "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101"  +// KEY 0X55 (16 TIMES)
        "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" +
        "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101"  +// KEY 0X55 (16 TIMES)
        "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" +
                "0111110100111111" +// CRC 0x7D3F

        "00101" +		// KEY_LENGTH_256
                "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101"  +// KEY 0X55 (16 TIMES)
                "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" +
                "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101"  +// KEY 0X55 (16 TIMES)
                "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" + "01010101" +
                "0111110100111111"; // CRC 0x7D3F

        assertArrayEquals(BinaryMsg.get(expectedMessage), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(headerPolicy).getHeader(any(), any());
        verify(messagePolicy).get(any());
        verify(messagePolicy).setKey(any());
        verify(timestampPolicy).get();
        verify(hmacKeyPolicy).apply(any());
    }
}