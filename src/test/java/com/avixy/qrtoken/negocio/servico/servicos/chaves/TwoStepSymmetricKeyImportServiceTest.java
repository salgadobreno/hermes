package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.RandomGenerator;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

public class TwoStepSymmetricKeyImportServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);

    RandomGenerator randomGenerator = new RandomGenerator() {
        @Override
        public void nextBytes(byte[] bytes) {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = 1; // fake random
            }
        }
    };

    byte[] secrecyKey = new byte[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    byte[] authKey = new byte[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    byte[] k2 = new byte[] {0,3,2,5,4,7,6,9,8,11,10,13,12,15,14,17,0,3,2,5,4,7,6,9,8,11,10,13,12,15,14,17};

    TwoStepSymmetricKeyImportService service = new TwoStepSymmetricKeyImportAvixyService(headerPolicy, timestampPolicy, passwordPolicy, randomGenerator);
    String expectedQr1 = "00001" + //length 1
            "00001" + //length 2
            "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" +
            "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" +
            "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" +
            "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + //fake random
            "1111001111110011"; // crc

    String expectedQr2 = "00001" + //length 1
            "00001" + //length 2
            "00000000" + "00000011" + "00000010" + "00000101" + "00000100" + "00000111" + "00000110" + "00001001" + //k2
            "00001000" + "00001011" + "00001010" + "00001101" + "00001100" + "00001111" + "00001110" + "00010001" + //k2
            "00000000" + "00000011" + "00000010" + "00000101" + "00000100" + "00000111" + "00000110" + "00001001" + //k2
            "00001000" + "00001011" + "00001010" + "00001101" + "00001100" + "00001111" + "00001110" + "00010001" + //k2
            "1010001010011101"; //crc k2
    //0,3,2,5,4,7,6,9,8,11,10,13,12,15,14,17,0,3,2,5,4,7,6,9,8,11,10,13,12,15,14,17
    //0b10100000, 0b00100010 crc



    @Before
    public void setUp() throws Exception {
        service.setTimestamp(new Date());
        service.setSecrecyKey(secrecyKey);
        service.setAuthKey(authKey);
        service.setPin("1234");

        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(BinaryMsg.get(expectedQr1), service.getQr1());
        assertArrayEquals(BinaryMsg.get(expectedQr2), service.getQr2());

        //qr1 xor qr2 deve ser igual a key1+key2
        byte[] q1XorQ2 = new byte[32];
        byte[] k1 = BinaryMsg.get(expectedQr1.substring(10));
        for (int i = 0; i < k2.length; i++) {
            q1XorQ2[i] = (byte) (k1[i] ^ k2[i]);
        }

        assertArrayEquals(q1XorQ2, ArrayUtils.addAll(secrecyKey, authKey));
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(new QrSetup(Version.getVersionForNumber(2), ErrorCorrectionLevel.L));
        verify(passwordPolicy, times(2)).get();
        verify(headerPolicy, times(2)).getHeader(service);
        verify(timestampPolicy, times(2)).get();
    }
}