package com.avixy.qrtoken.negocio.qrcode;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created on 25/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrSetupTest {
    
    private Version version = Version.getVersionForNumber(10);

    private ErrorCorrectionLevel ecLevel = ErrorCorrectionLevel.L;

    private byte[] msg = "mensagem secreta".getBytes();

    @Test
    public void testGetAvailableBytes() throws Exception {
        // ISO/IEC 18004:2000 Table 7
        // Version - 1, EC - Low, Data Capacity(8-bit Byte) - 17
        QrSetup setup = new QrSetup(Version.getVersionForNumber(1), ecLevel);
        assertEquals(setup.getAvailableBytes(), 17);
    }

}