package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsgTest;
import com.avixy.qrtoken.core.extensions.components.PasswordField;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UpdateSymmetricKeyServiceTest {
    SettableTimestampPolicy timestampPolicy = Mockito.mock(SettableTimestampPolicy.class);
    QrtHeaderPolicy headerPolicy = Mockito.mock(QrtHeaderPolicy.class);
    AesCryptedMessagePolicy aesCryptedMessagePolicy = Mockito.mock(AesCryptedMessagePolicy.class);
    HmacKeyPolicy hmacKeyPolicy = Mockito.mock(HmacKeyPolicy.class);
    PasswordPolicy passwordPolicy = Mockito.mock(PasswordPolicy.class);
    UpdateSymmetricKeyService service = new UpdateSymmetricKeyAvixyService(headerPolicy, timestampPolicy, passwordPolicy, aesCryptedMessagePolicy, hmacKeyPolicy);
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
        service.setPin("123456");

        service.setSecretKey(Hex.decodeHex("ae0aaaf92295ede5".toCharArray()));
        service.setAuthKey(Hex.decodeHex("de38bae46f371678".toCharArray()));

        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(aesCryptedMessagePolicy.get(service)).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMsg() throws Exception {
        assertArrayEquals(BinnaryMsg.get(expectedMsg), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.run();
        Mockito.verify(aesCryptedMessagePolicy).get(service);
        Mockito.verify(timestampPolicy).get();
        Mockito.verify(headerPolicy).getHeader(service);
        Mockito.verify(hmacKeyPolicy).apply(Mockito.<byte[]>any());
        Mockito.verify(passwordPolicy).setPassword(Mockito.anyString());
        Mockito.verify(passwordPolicy).get();
    }
}