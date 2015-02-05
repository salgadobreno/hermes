package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

//TODO defasated
//public class TwoStepDoubleSymmetricKeyImportServiceTest {
//    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
//    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
//    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
//    TwoStepDoubleSymmetricKeyImportService service = new TwoStepDoubleSymmetricKeyImportService(headerPolicy, timestampPolicy, passwordPolicy);
//    String expectedBinaryString = "";
//
//    @Before
//    public void setUp() throws Exception {
//        long epoch = 1409329200000L;
//        expectedBinaryString +=  "0100" + //template4
//                "0000" + //keytype0
//                "00000" + //keylengt 8 bits
//                "0010" + //keytype2
//                "00000" + //keylength 8 bits
//                "1111111111111111111111111111111111111111111111111111111111111111" +
//                "1111111111111111111111111111111111111111111111111111111111111111" +
//                "0110_1010_0100_1011" + // crc senha 6A4B
//                "0011_1001_0011_1000_0011_0111_0011_1001"; //desafio 9879
//
//        service.setTimestamp(new Date(epoch));
//        service.setPin("1234");
//        service.setTemplate((byte) 4);
//        service.setKeyType1(KeyType.TDES);
//        service.setKeyLength1(8);
//        service.setKeyType2(KeyType.HMAC);
//        service.setKeyLength2(8);
//        service.setKeys(Hex.decodeHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".toCharArray()));
//        service.setDesafio("9879");
//
//        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
//        when(passwordPolicy.get()).thenReturn(new byte[0]);
//        when(timestampPolicy.get()).thenReturn(new byte[0]);
//    }
//
//    @Test
//    public void testMessage() throws Exception {
//        assertArrayEquals(BinnaryMsg.get(expectedBinaryString), service.getMessage());
//    }
//
//    @Test
//    public void testOperations() throws Exception {
//        service.run();
//        verify(headerPolicy).getHeader(service);
//        verify(passwordPolicy).get();
//        verify(timestampPolicy).get();
//    }
//}