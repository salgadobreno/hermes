package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateSymmetricKeyServiceTest {
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = mock(AesCryptedMessagePolicy.class);
    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
    UpdateSymmetricKeyService service = new UpdateSymmetricKeyAvixyService(headerPolicy, timestampPolicy, aesCryptedMessagePolicy, hmacKeyPolicy);
    String expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = "00000" + // keylength 0 - 8 bytes
                "1010111000001010101010101111100100100010100101011110110111100101" + // key
                "1010100000000010" + //crc
                "00000" + // auth keylength 0 - 8 bytes
                "1101111000111000101110101110010001101111001101110001011001111000" + // key
                "0000011100001101"; // crc

        service.setTimestamp(new Date(epoch));
        service.setHmacKey("zxcv".getBytes());
        service.setAesKey("bla".getBytes());

        service.setSecretKey(Hex.decodeHex("ae0aaaf92295ede5".toCharArray()));
        service.setAuthKey(Hex.decodeHex("de38bae46f371678".toCharArray()));

        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(any(), any())).thenReturn(new byte[0]);
        when(aesCryptedMessagePolicy.get(service)).thenReturn(new byte[0]);
    }

    @Test
    public void testMsg() throws Exception {
        assertArrayEquals(BinaryMsg.get(expectedMsg), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        Mockito.verify(aesCryptedMessagePolicy).get(service);
        Mockito.verify(timestampPolicy).get();
        Mockito.verify(headerPolicy).getHeader(any(), any());
        Mockito.verify(hmacKeyPolicy).apply(Mockito.<byte[]>any());
    }
}