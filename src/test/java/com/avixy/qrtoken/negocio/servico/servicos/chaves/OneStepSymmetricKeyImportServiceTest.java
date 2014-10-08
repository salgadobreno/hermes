package com.avixy.qrtoken.negocio.servico.servicos.chaves;

import com.avixy.qrtoken.negocio.servico.chaves.crypto.NullKeyPolicy;
import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OneStepSymmetricKeyImportServiceTest {
    OneStepSymmetricKeyImportService service = new OneStepSymmetricKeyImportService();
    byte[] expectedMsg;

    @Before
    public void setUp() throws Exception {
        long epoch = 1409329200000L;
        expectedMsg = new byte[]{
                /* 'shiftando' os bits de cada parâmetro manualmente */
                0b01010100,
                0b00000000,
                (byte) 0b10101000,
                0b00110000, // expected_epoch gmt / timestamp
                0b00110001, // PIN:'1'
                0b00110010, // '2'
                0b00110011, // '3'
                0b00110100, // '4'
                0b00100100, // '$'
                0b0100_0011,  //template4_
                (byte) 0b00011_011, //keytype3_keylength3_key
                (byte) 0b1_0011_011, // 's'
                (byte) 0b0_0101_011, // 'e'
                (byte) 0b0_1110_011, // 'n'
                (byte) 0b0_1000_011, // 'h'
                (byte) 0b0_0001_011, // 'a' _crc
                (byte) 0b1_1001_101, // crc-ccitt 'senha' -> 0x79A1 == 31137 http://www.lammertbies.nl/comm/info/crc-calculation.html
                (byte) 0b0_0001_001, // _desafio 0372
                (byte) 0b1_0000_001,
                (byte) 0b1_0011_001,
                (byte) 0b1_0111_001,
                (byte) 0b1_0010_000
        };
        service.setTimestamp(new Date(epoch));
        service.setPin("1234");
        service.setTemplate((byte)4);
        service.setKeyComponent(KeyTypeParam.KeyType.RSA_ENCRYPTION, 192, "senha");
        service.setDesafio("0372");
    }

    @Test
    public void testMessage() throws Exception {
        assertArrayEquals(expectedMsg, service.getMessage());
    }

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(32, service.getServiceCode());
    }

}