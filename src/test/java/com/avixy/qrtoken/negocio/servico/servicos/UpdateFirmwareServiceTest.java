package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AesKeyPolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import org.bouncycastle.crypto.CryptoException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.GeneralSecurityException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateFirmwareServiceTest {
    AesKeyPolicy aesKeyPolicy = new AesKeyPolicy(){
        @Override
        public byte[] apply(byte[] msg) throws CryptoException, GeneralSecurityException {
            return msg;
        }

        @Override
        public byte[] getInitializationVector() {
            return new byte[0];
        }
    };
    HmacKeyPolicy hmacKeyPolicy = new HmacKeyPolicy(){
        @Override
        public byte[] apply(byte[] msg) throws GeneralSecurityException {
            return msg;
        }
    };
    UpdateFirmwareService service = new UpdateFirmwareService(new QrtHeaderPolicy(), aesKeyPolicy, hmacKeyPolicy);
    byte[] serviceQr;
    byte[] payloadQr;

    @Before
    public void setUp() throws Exception {
        QrSetup setup = new QrSetup(Version.getVersionForNumber(1), ErrorCorrectionLevel.L);
//        byte[] content = new byte[]{0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa};
        byte[] content = new byte[]{ 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa }; // content
        byte moduleOffset = 111;
        String challenge = "123456";
        byte interruptionCounter = 1;
        byte[] interruptionInfo = new byte[]{ 0xA, 0xB, 0xC, 0xD, 0xE };

        serviceQr = new byte[] {
                (byte) 0xFF,
                (byte) 0xFF,
                // SERVICE_FIRMWARE_SYM_UPDATE
                (byte)ServiceCode.SERVICE_FIRMWARE_SYM_UPDATE.ordinal(), // header
                // 2 bytes para indicar a quantidade de frames no update
                0, 1,
                // 2 bytes para indicar o somat?rio de bytes em todos os frames
                0, 10,
                //1 byte para indicar o offset do m?dulo
                111,
                // 6 chars como challenge para confirma??o do update
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                1, // 1 byte com o n?mero de fun??es de interrup??o/exce??o a serem atualizadas na tabela
                0xA, 0xB, 0xC, 0xD, 0xE,
        };

        // QR Version 2: 17 bytes
        payloadQr = new byte[]{
                0, 0, // offset
                0, 10, // payload do frame
//                0x00, 0x20, 0x70, 0x47, 0x00, (byte)0xbf, // Assembly do Diego
                0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa,
                0, 0, 0//, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 // padding
        };

        service.setQrSetup(setup);
        service.setContent(content);
        service.setModuleOffset((byte) moduleOffset);
        service.setChallenge(challenge);
        service.setInterruptionCount(interruptionCounter);
        service.setInterruptionBytes(interruptionInfo);
    }

    @Test
    public void testFirstQr() throws Exception {
        assertArrayEquals(serviceQr, service.getInitialQr());
    }

    @Test
    public void testData() throws Exception {
        assertArrayEquals(payloadQr, service.run());
    }

    @Test
    public void testOffset() throws Exception {
        byte[] data = new byte[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}; //20 1s
        serviceQr = new byte[] {
                (byte) 0xFF,
                (byte) 0xFF, // SERVICE_FIRMWARE_SYM_UPDATE
                (byte)ServiceCode.SERVICE_FIRMWARE_SYM_UPDATE.ordinal(), // header
                0, 2, // 2 bytes para indicar a quantidade de frames no update
                0, 20, // 2 bytes para indicar o somat?rio de bytes em todos os frames
                //1 byte para indicar o offset do m?dulo
                111,
                // 6 chars como challenge para confirma??o do update
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                1, // 1 byte com o n?mero de fun??es de interrup??o/exce??o a serem atualizadas na tabela
                0xA, 0xB, 0xC, 0xD, 0xE,
        };
        // QR Version 2: 17 bytes
        payloadQr = new byte[]{
                0, 0, // offset
                0, 13, // payload
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1,// first payload qr

                0, 13, // offset
                0, 7, // payload
                1, 1, 1, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 0// second payload qr
        };
        service.setContent(data);

        assertArrayEquals(serviceQr, service.getInitialQr());
        assertArrayEquals(payloadQr, service.run());
    }
}
