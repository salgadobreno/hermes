//package com.avixy.qrtoken.negocio.servico.servicos.chaves;
//
//import com.avixy.qrtoken.negocio.qrcode.QrSetup;
//import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
//import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
//import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
//import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import java.util.Date;
//
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class ExportSymKeyServiceTest {
//    HmacKeyPolicy hmacKeyPolicy = mock(HmacKeyPolicy.class);
//    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
//    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
//    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
//    ExportSymKeyService service = new ExportSymKeyService(headerPolicy, timestampPolicy, passwordPolicy, hmacKeyPolicy);
//
//    byte[] expectedMsg;
//
//    @Before
//    public void setUp() throws Exception {
//        long epoch = 1409329200000L;
//        expectedMsg = new byte[]{
////                0b01010100,
////                0b00000000,
////                (byte) 0b10101000,
////                0b00110000,     // timestamp
////                0b00110001, // PIN:'1'
////                0b00110010, // '2'
////                0b00110011, // '3'
////                0b00110100, // '4'
////                0b00000100, // length 4
//        };
//
//        service.setTimestamp(new Date(epoch));
//        service.setPin("1234");
//        service.setHmacKey("key".getBytes());
//
//        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
//        when(timestampPolicy.get()).thenReturn(new byte[0]);
//        when(passwordPolicy.get()).thenReturn(new byte[0]);
//    }
//
//    @Test
//    public void testMessage() throws Exception {
//        assertArrayEquals(expectedMsg, service.getMessage());
//    }
//
//    @Test
//    public void testCrypto() throws Exception {
//        service.getQrs(mock(QrSetup.class));
//        verify(hmacKeyPolicy).apply(Mockito.<byte[]>any());
//        verify(headerPolicy).getHeader(service);
//        verify(passwordPolicy).get();
//        verify(timestampPolicy).get();
//    }
//}