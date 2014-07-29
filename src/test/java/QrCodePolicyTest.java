import com.avixy.qrtoken.negocio.QrCodePolicy;
import com.avixy.qrtoken.negocio.QrSetup;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created on 25/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrCodePolicyTest {

    @Test @Ignore
    public void testCalcHeader() throws Exception {
        // TODO: implementar com o protocolo de serviços pronto.
    }

    @Test
    public void testGetHeaderSize() throws Exception {
        Assert.assertEquals(QrCodePolicy.getHeaderSize(), QrCodePolicy.getHeaderSize());
    }

    @Test
    public void testGetQrsFor() throws Exception {
        Version version = Version.getVersionForNumber(1);
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        String content = "blablabla";

        QrSetup setup = new QrSetup(version, errorCorrectionLevel, content.length());
        assertEquals(setup.getQrQuantityFor(QrCodePolicy.getHeaderSize()), QrCodePolicy.getQrsFor(content.getBytes(), version, errorCorrectionLevel).length);
    }
}