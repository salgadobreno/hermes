package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OneStepSymmetricKeyImportServiceTest {
    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    OneStepSymmetricKeyImportService service = new OneStepSymmetricKeyImportAvixyService(headerPolicy, timestampPolicy, passwordPolicy);
    String expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        //http://binary.online-toolz.com/tools/hex-binary-convertor.php
        //http://www.lammertbies.nl/comm/info/crc-calculation.html -> CRC-CCITT (0xFFFF)
        expectedMsg = "00000" + // keylength 0 - 8 bytes
                "1010111000001010101010101111100100100010100101011110110111100101" + // key
                "1010100000000010" + // crc
                "00000" + // auth keylength 0 - 8 bytes
                "1101111000111000101110101110010001101111001101110001011001111000" + // key
                "0000011100001101"; // crc

        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setSecrecyKey(Hex.decode("ae0aaaf92295ede5"));
        service.setAuthKey(Hex.decode("de38bae46f371678"));

        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
        when(timestampPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(BinaryMsg.get(expectedMsg), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        verify(headerPolicy).getHeader(service);
        verify(passwordPolicy).get();
        verify(timestampPolicy).get();
    }
}