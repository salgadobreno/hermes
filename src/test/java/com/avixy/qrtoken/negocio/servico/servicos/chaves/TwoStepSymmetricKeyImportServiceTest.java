//package com.avixy.qrtoken.negocio.servico.servicos.chaves;
//
//import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
//import com.avixy.qrtoken.negocio.qrcode.QrSetup;
//import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
//import com.avixy.qrtoken.negocio.servico.operations.RandomGenerator;
//import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
//import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import com.google.zxing.qrcode.decoder.Version;
//import org.apache.commons.lang.ArrayUtils;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Date;
//
//import static org.junit.Assert.assertArrayEquals;
//import static org.mockito.Mockito.*;
//
//public class TwoStepSymmetricKeyImportServiceTest {
//    QrtHeaderPolicy headerPolicy = mock(QrtHeaderPolicy.class);
//    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
//    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
//
//    RandomGenerator randomGenerator = new RandomGenerator() {
//        @Override
//        public void nextBytes(byte[] bytes) {
//            for (int i = 0; i < bytes.length; i++) {
//                bytes[i] = 1; // fake random
//            }
//        }
//    };
//
//    byte[] secrecyKey = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
//    byte[] authKey = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
//    byte[] component1 = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
//                         1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
//
//    TwoStepSymmetricKeyImportService service = new TwoStepSymmetricKeyImportAvixyService(headerPolicy, timestampPolicy, passwordPolicy, randomGenerator);
//    String expectedQr1 = "00001" + //length 1
//            "00001" + //length 2
//            "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" +
//            "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" +
//            "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" +
//            "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + "00000001" + //fake random
//            "1111001111110011"; // crc
//
//    String expectedQr2 = "00001" + //length 1
//            "00001" + //length 2
//            "00000000" + "00000011" + "00000010" + "00000101" + "00000100" + "00000111" + "00000110" + "00001001" + //component2
//            "00001000" + "00001011" + "00001010" + "00001101" + "00001100" + "00001111" + "00001110" + "00010001" + //component2
//            "00000000" + "00000011" + "00000010" + "00000101" + "00000100" + "00000111" + "00000110" + "00001001" + //component2
//            "00001000" + "00001011" + "00001010" + "00001101" + "00001100" + "00001111" + "00001110" + "00010001" + //component2
//            "1010001010011101"; //crc component2
//
//    @Before
//    public void setUp() throws Exception {
//        service.setTimestamp(new Date());
//        service.setSecrecyKey(secrecyKey);
//        service.setAuthKey(authKey);
//        service.setPin("1234");
//
//        when(headerPolicy.getHeader(service)).thenReturn(new byte[0]);
//        when(timestampPolicy.get()).thenReturn(new byte[0]);
//        when(passwordPolicy.get()).thenReturn(new byte[0]);
//    }
//
//    @Test
//    public void testMessage() throws Exception {
//        assertArrayEquals(BinaryMsg.get(expectedQr1), service.getQr1());
//        assertArrayEquals(BinaryMsg.get(expectedQr2), service.getQr2());
//
//        //qr1 xor qr2 deve ser igual a key1+key2
//        byte[] q1XorQ2 = new byte[32];
//        byte[] component2 = BinaryMsg.get(expectedQr2.substring(10));
//        for (int i = 0; i < 32; i++) {
//            q1XorQ2[i] = (byte) (component2[i] ^ component1[i]);
//        }
//
//        assertArrayEquals(q1XorQ2, ArrayUtils.addAll(secrecyKey, authKey));
//    }
//
//    @Test
//    public void testOperations() throws Exception {
//        service.getQrs(new QrSetup(Version.getVersionForNumber(2), ErrorCorrectionLevel.L));
//        verify(passwordPolicy, times(2)).get();
//        verify(headerPolicy, times(2)).getHeader(service);
//        verify(timestampPolicy, times(2)).get();
//    }
//
//    @Test
//    public void testAgainstTokenTest() throws Exception {
//
//        byte[] secrecyKey = {
//                0x4f, 0x6e, 0x65, 0x20, 0x64, 0x6f, 0x65, 0x73, 0x6e, 0x74, 0x20, 0x73, 0x69, 0x6d, 0x70, 0x6c,    //-> Encryption Key
//        };
//        byte[] authKey = {
//                0x79, 0x20, 0x67, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x74, 0x65, 0x20, 0x61, 0x20, 0x6b, 0x65, 0x79  //-> Auth Key
//        };
//        byte[] component1 = {
//                0x53, 0x6f, 0x2e, 0x2e, 0x20, 0x64, 0x6f, 0x20, 0x79, 0x6f, 0x75, 0x20, 0x77, 0x61, 0x6e, 0x74,
//                0x20, 0x61, 0x20, 0x6b, 0x65, 0x79, 0x20, 0x63, 0x6f, 0x6d, 0x70, 0x6f, 0x6e, 0x65, 0x6e, 0x74
//        };
//        String expectedQr1 = "00001" + // Secrecy key length 128 bits
//                "00001" + // HMAC Key length 128bits
//                "01010011" + "01101111" + "00101110" + "00101110" + "00100000" + "01100100" + "01101111" + "00100000" +
//                "01111001" + "01101111" + "01110101" + "00100000" + "01110111" + "01100001" + "01101110" + "01110100" +
//                "00100000" + "01100001" + "00100000" + "01101011" + "01100101" + "01111001" + "00100000" + "01100011" +
//                "01101111" + "01101101" + "01110000" + "01101111" + "01101110" + "01100101" + "01101110" + "01110100" +
//                "0010100011011110"// CRC: 0x28DE
//        ;
//        String expectedQr2 = "00001" + // Secrecy Key length 128 bits
//                "00001" + // HMAC key length 128 bits
//                "00011100" + "00000001" + "01001011" + "00001110" + "01000100" + "00001011" + "00001010" + "01010011" +
//                "00010111" + "00011011" + "01010101" + "01010011" + "00011110" + "00001100" + "00011110" + "00011000" +
//                "01011001" + "01000001" + "01000111" + "00001110" + "00001011" + "00011100" + "01010010" + "00000010" +
//                "00011011" + "00001000" + "01010000" + "00001110" + "01001110" + "00001110" + "00001011" + "00001101" +
//                "1011001010011001" // CRC: 0xB299"
//        ;
//
//        RandomGenerator randomGenerator2 = new RandomGenerator() {
//            public void nextBytes(byte[] bytes) {
//                System.arraycopy(component1, 0, bytes, 0, component1.length);
//            }
//        };
//
//        TwoStepSymmetricKeyImportService service2 = new TwoStepSymmetricKeyImportAvixyService(headerPolicy, timestampPolicy, passwordPolicy, randomGenerator2);
//
//        service2.setSecrecyKey(secrecyKey);
//        service2.setAuthKey(authKey);
//
//        assertArrayEquals(BinaryMsg.get(expectedQr1), service2.getQr1());
//        assertArrayEquals(BinaryMsg.get(expectedQr2), service2.getQr2());
//
//        //qr1 xor qr2 deve ser igual a key1+key2
//        byte[] q1XorQ2 = new byte[32];
//        byte[] component2 = BinaryMsg.get(expectedQr2.substring(10));
//        for (int i = 0; i < 32; i++) {
//            q1XorQ2[i] = (byte) (component2[i] ^ component1[i]);
//        }
//
//        assertArrayEquals(q1XorQ2, ArrayUtils.addAll(secrecyKey, authKey));
//    }
//}