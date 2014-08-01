import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
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
    QrSetup setup = new QrSetup(policy, Version.getVersionForNumber(4), ErrorCorrectionLevel.L, "mensagem".getBytes());

    @Test @Ignore
    public void testCalcHeader() throws Exception {
        // TODO: implementar com o protocolo de serviços pronto.
    }

    @Test
    public void testGetHeaderSize() throws Exception {
        Assert.assertEquals(policy.getHeaderSize(), policy.calcHeader(setup, 1).length);
    }

    @Test
    public void testGetQrsFor() throws Exception {
        assertEquals(setup.getQrQuantity(), policy.getQrsFor(setup).length);
    }
}