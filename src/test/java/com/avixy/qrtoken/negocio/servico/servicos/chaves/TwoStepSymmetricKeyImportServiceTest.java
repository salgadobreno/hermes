package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//TODO defasated
//public class TwoStepSymmetricKeyImportServiceTest {
//    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
//    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
//    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
//    TwoStepSymmetricKeyImportService service = new TwoStepSymmetricKeyImportService(headerPolicy, timestampPolicy, passwordPolicy);
//    String expectedBinaryString = "";
//
//    @Before
//    public void setUp() throws Exception {
//        long epoch = 1409329200000L;
//        expectedBinaryString +=  "0100" + //template4
//                "0010" + //keytype 2
//                "00000" + //keylength 8 bits
//                "1111111111111111111111111111111111111111111111111111111111111111" + //senha
//                "1001011111011111" + // crc 97DF
//                "0011_1001_0011_1000_0011_0111_0011_1001"; //desafio 9879
//
//
//        service.setTimestamp(new Date(epoch));
//        service.setPin("1234");
//        service.setTemplate((byte) 4);
//        service.setKeyComponent(KeyType.HMAC, Hex.decodeHex("FFFFFFFFFFFFFFFF".toCharArray()));
//        service.setDesafio("9879");
//
//        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
//        when(timestampPolicy.get()).thenReturn(new byte[0]);
//        when(passwordPolicy.get()).thenReturn(new byte[0]);
//    }
//
//    @Test
//    public void testMessage() throws Exception {
//        assertArrayEquals(BinnaryMsg.get(expectedBinaryString), service.getMessage());
//    }
//
//    @Test
//    public void testServiceCode() throws Exception {
//        assertEquals(34, service.getServiceCode());
//    }
//
//    @Test
//    public void testOperations() throws Exception {
//        service.run();
//        verify(headerPolicy).getHeader(service);
//        verify(timestampPolicy).get();
//        verify(passwordPolicy).get();
//    }
//}