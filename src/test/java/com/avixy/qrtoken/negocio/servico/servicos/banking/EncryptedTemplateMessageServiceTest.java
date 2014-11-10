package com.avixy.qrtoken.negocio.servico.servicos.banking;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created on 22/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
//public class EncryptedTemplateMessageServiceTest {
//    AesKeyPolicy aesKeyPolicy =  Mockito.mock(AesKeyPolicy.class);
//
//    AbstractEncryptedTemplateMessageService service = new AbstractEncryptedTemplateMessageService(new QrtHeaderPolicy(), aesKeyPolicy);
//
//    byte[] expectedMsg;
//
//    @Before
//    public void setUp() throws Exception {
//        long epoch = 1409329200000L;
//        expectedMsg = new byte[]{
//                0b01010100,
//                0b00000000,
//                (byte) 0b10101000,
//                0b00110000, // expected_epoch gmt / timestamp
//                0b00110001, // PIN:'1'
//                0b00110010, // '2'
//                0b00110011, // '3'
//                0b00110100, // '4'
//                0b00100100, // '$'
//                0b0011_0010, // template
//                (byte) 0b1000_0011, // a param
//                (byte) 0b00100000, // another param
//        };
//        service.setTimestamp(new Date(epoch));
//        service.setPin("1234");
//        service.setTemplate((byte) 3);
//        service.setParams(new ByteWrapperParam((byte) 40), new ByteWrapperParam((byte) 50));
//    }
//
//    @Test
//    public void testServiceCode() throws Exception {
//        assertEquals(12, service.getServiceCode());
//    }
//
//    @Test
//    public void testServiceMsg() throws Exception {
//        assertArrayEquals(expectedMsg, service.getMessage());
//    }
//
//    @Test
//    public void testCrypto() throws Exception {
//        service.run();
//        Mockito.verify(aesKeyPolicy).apply(Mockito.<byte[]>anyObject());
//    }
//}
