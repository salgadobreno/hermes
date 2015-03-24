package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

//TODO: defasated
public class OneStepDoubleSymmetricKeyImportServiceTest {
//    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
//    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
//    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
//    OneStepDoubleSymmetricKeyImportService service = new OneStepDoubleSymmetricKeyImportService(headerPolicy, timestampPolicy, passwordPolicy);
//
//    String expectedMsg;
//
//    @Before
//    public void setUp() throws Exception {
//        long epoch = 1409329200000L;
//        expectedMsg =  "0100" +//template4
//                "0010" + //keytype2
//                "00011" +//keylength3
//                "0010" + //keytype2
//                "00100" + //keylength4
//                "011100110110010101101110011010000110000100110001011100110110010101101110011010000110000100110010" +
//                "1011110001110110" + //crc 'senha1senha2' -> 0xBC76 == -17290
//                "00111001001110000011011100111001"; //desafio 9879
//
//        service.setTimestamp(new Date(epoch));
//        service.setPin("1234");
//        service.setTemplateSlot((byte)4);
//        service.setKeyType1(KeyType.HMAC);
//        service.setKeyLength1(192/8);
//        service.setKeyType2(KeyType.HMAC);
//        service.setKeyLength2(224/8);
//        service.setKeys("senha1senha2".getBytes());
//        service.setDesafio("9879");
//
//        when(passwordPolicy.get()).thenReturn(new byte[0]);
//        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
//        when(timestampPolicy.get()).thenReturn(new byte[0]);
//    }
//
//    @Test
//    public void testServiceCode() throws Exception {
//        assertEquals(33, service.getServiceCode());
//    }
//
//    @Test
//    public void testMessage() throws Exception {
//        assertArrayEquals(BinnaryMsg.get(expectedMsg), service.getMessage());
//    }
//
//    @Test
//    public void testOperations() throws Exception {
//        service.run();
//        verify(headerPolicy).getHeader(service);
//        verify(passwordPolicy).get();
//        verify(timestampPolicy).get();
//    }
}