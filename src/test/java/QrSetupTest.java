import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
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

    QrCodePolicy policy = new QrCodePolicy(){
        @Override
        public int getHeaderSize(){
            return 3;
        }

        @Override
        public byte[] calcHeader(QrSetup setup, int index) {
            return "111".getBytes();
        }
    };

    @Test
    public void testGetAvailableBytes() throws Exception {
        // ISO/IEC 18004:2000 Table 7
        // Version - 1, EC - Low, Data Capacity(8-bit Byte) - 17
        QrSetup setup = new QrSetup(policy, Version.getVersionForNumber(1), ecLevel, msg);
        assertEquals(setup.getAvailableBytes(), 17);
    }

    @Test
    public void testGetUsableBytes() throws Exception {
        // Equals available bytes - header bytes
        QrSetup setup = new QrSetup(policy, Version.getVersionForNumber(1), ecLevel, msg);
        assertEquals(setup.getUsableBytes(), 14);
    }

    @Test
    public void testGetQrQuantity() throws Exception {
        // Given a header of 3
        // And content of 27
        // And 17 bytes available for Version - 1, EC - Low, Data Capacity(byte) - 17
        // header of 3 and content of 27 should need 2 QR Codes
        QrSetup setup = new QrSetup(policy, Version.getVersionForNumber(1), ecLevel, msg);
        assertEquals(2, setup.getQrQuantity());
    }

}