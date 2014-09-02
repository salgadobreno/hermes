import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.Service;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import org.junit.Ignore;
import org.junit.Test;

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
        public byte[] getHeader(Service service) {
            return "111".getBytes();
        }
    };
    QrSetup setup = new QrSetup(Version.getVersionForNumber(4), ErrorCorrectionLevel.L);

    @Test @Ignore
    public void testCalcHeader() throws Exception {
        // TODO: fazer teste com esquema bitwise
    }

//   @Test
//   public void testGetHeaderSize() throws Exception {
//        assertEquals(policy.getHeaderSize(), policy.getHeader(new HmacRtcService()).length);
//    }

}