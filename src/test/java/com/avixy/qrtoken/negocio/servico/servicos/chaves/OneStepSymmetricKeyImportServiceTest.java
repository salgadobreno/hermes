package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
import com.avixy.qrtoken.negocio.TestHelper;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OneStepSymmetricKeyImportServiceTest {
    OneStepSymmetricKeyImportService service = new OneStepSymmetricKeyImportService(TestHelper.getHeaderPolicy());
    String expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
//        expectedMsg = new byte[]{
//                /* 'shiftando' os bits de cada parâmetro manualmente */
//                0b01010100,
//                0b00000000,
//                (byte) 0b10101000,
//                0b00110000, // expected_epoch gmt / timestamp
//                0b00110001, // PIN:'1'
//                0b00110010, // '2'
//                0b00110011, // '3'
//                0b00110100, // '4'
//                0b00100100, // length 4
//                0b0100_0011,  //template4_
//                (byte) 0b00011_011, //keytype3_keylength3_key
//                (byte) 0b1_0011_011, // 's'
//                (byte) 0b0_0101_011, // 'e'
//                (byte) 0b0_1110_011, // 'n'
//                (byte) 0b0_1000_011, // 'h'
//                (byte) 0b0_0001_011, // 'a' _crc
//                (byte) 0b1_1001_101, // crc-ccitt 'senha' -> 0x79A1 == 31137 http://www.lammertbies.nl/comm/info/crc-calculation.html
//                (byte) 0b0_0001_001, // _desafio 0372
//                (byte) 0b1_0000_001,
//                (byte) 0b1_0011_001,
//                (byte) 0b1_0111_001,
//                (byte) 0b1_0010_000
//        };
        expectedMsg = "01010100" +
                "00000000" +
                "10101000" +
                "00110000" + // expected_epoch gmt / timestamp
                "0100" +  //template4_
                "0011" +  //keytype3
                "00011" + //_keylength3
                "01110011" + // 's'
                "01100101" + // 'e'
                "01101110" + // 'n'
                "01101000" + // 'h'
                "01100001" + // 'a'
                "01111001" + // crc
                "10100001" + // crc-ccitt 'senha' -> 0x79A1 == 31137 http://www.lammertbies.nl/comm/info/crc-calculation.html
                "00110000" + // desafio 0372
                "00110011" +
                "00110111" +
                "00110010" +
                "00110001" + // PIN:'1'
                "00110010" + // '2'
                "00110011" + // '3'
                "00110100" + // '4'
                "00000100"; // length 4

        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setTemplate((byte)4);
        service.setKeyComponent(KeyTypeParam.KeyType.RSA_ENCRYPTION, 192, "senha");
        service.setDesafio("0372");
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(BinnaryMsg.get(expectedMsg), service.getMessage());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(32, service.getServiceCode());
    }

}