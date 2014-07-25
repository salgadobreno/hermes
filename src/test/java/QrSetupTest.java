import com.avixy.qrtoken.negocio.QrSetup;
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

    @Test
    public void testGetAvailableBytes() throws Exception {
        // ISO/IEC 18004:2000 Table 7
        // Version - 1, EC - Low, Data Capacity(8-bit Byte) - 17
        QrSetup setup = new QrSetup(Version.getVersionForNumber(1), ecLevel, 30);
        assertEquals(setup.getAvailableBytes(), 17);
    }

    @Test
    public void testGetUsableBytesFor() throws Exception {
        // Equals usable - header param
        String header = "123";
        QrSetup setup = new QrSetup(Version.getVersionForNumber(1), ecLevel, 30);
        assertEquals(setup.getUsableBytesFor(header.length()), 14);
    }

    @Test
    public void testGetQrQuantityFor() throws Exception {
        // Given a header of 3
        // And content of 27
        // And 17 bytes available for Version - 1, EC - Low, Data Capacity(byte) - 17
        // header of 3 and content of 27 should need 2 QR Codes
        String header = "123";
        QrSetup setup = new QrSetup(Version.getVersionForNumber(1), ecLevel, 27);
        assertEquals(setup.getQrQuantityFor(header.length()), 2);
    }

}