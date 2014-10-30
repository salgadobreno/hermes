package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UpdateFirmwareServiceTest {
    UpdateFirmwareService service = new UpdateFirmwareService(new QrtHeaderPolicy(), new HmacKeyPolicy());
    byte[] serviceQr;
    byte[] payloadQr;

    @Before
    public void setUp() throws Exception {
        QrSetup setup = new QrSetup(Version.getVersionForNumber(1), ErrorCorrectionLevel.L);
//        byte[] content = new byte[]{0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa};
        byte[] content = new byte[]{ 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa }; // content
        byte module_offset = 111;
        String challenge = "123456";
        byte interruption_stuff = 0;

        serviceQr = new byte[] {
                0,
                0,
                // SERVICE_FIRMWARE_SYM_UPDATE
                0x3f, // header
                // 2 bytes para indicar a quantidade de frames no update
                0, 1,
                // 2 bytes para indicar o somat�rio de bytes em todos os frames
                0, 10,
                //1 byte para indicar o offset do m�dulo
                111,
                // 6 chars como challenge para confirma��o do update
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                // 1 byte com o n�mero de fun��es de interrup��o/exce��o a serem atualizadas na tabela
                0,
//                0, 0 //padding
        };

        payloadQr = new byte[]{
                0, 0, // frame
                0, 10, // payload do frame
//                0x00, 0x20, 0x70, 0x47, 0x00, (byte)0xbf, // Assembly do Diego
                0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa,
                0, 0, 0 // padding
        };

        service.setQrSetup(setup);
        service.setContent(content);
        service.setModuleOffset((byte) 111);
        service.setChallenge(challenge);
        service.setInterruptionStuff(interruption_stuff);
    }

    @Test
    public void testServiceCode() throws Exception { assertEquals(63, service.getServiceCode()); }

    @Test
    public void testFirstQr() throws Exception {
        assertArrayEquals(serviceQr, service.getInitialQr());
    }

    @Test
    public void testData() throws Exception {
        assertArrayEquals(payloadQr, service.getData());
    }
}