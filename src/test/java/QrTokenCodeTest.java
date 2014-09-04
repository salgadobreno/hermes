import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created on 25/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrTokenCodeTest {
    QrCodePolicy policy = new QrCodePolicy();
    byte[] header = "hhhhh".getBytes();
    byte[] dados = "dados".getBytes();
    QrSetup setup = new QrSetup(Version.getVersionForNumber(1), ErrorCorrectionLevel.L);
    // http://www.qrcode.com/en/about/version.html
    // Version 1, ECC Low => 17 binary bytes capacity

    @Test
    public void testConstructor(){
        QrCodePolicy.QrTokenCode slice = policy.new QrTokenCode(dados, setup);
        assertNotNull(slice);
    }

    @Test(expected = IllegalArgumentException.class) @Ignore
    public void testGetDadosArguments() {
        // should throw error if header+dados is bigger than the length
        QrCodePolicy.QrTokenCode tokenCode = policy.new QrTokenCode(new byte[10], setup);
    }

    @Test
    public void testGetDadosPadding(){
        // should right-pad the result
        QrSetup bigSetup = new QrSetup(Version.getVersionForNumber(10), ErrorCorrectionLevel.L);
        QrCodePolicy.QrTokenCode tokenCode = policy.new QrTokenCode(dados, bigSetup);
        assertEquals(tokenCode.getDados().charAt(tokenCode.getDados().length() - 1), '0');
    }

    @Test @Ignore
    public void testLosslessBytes(){
        // data in should == bytes out
        byte[] someChars = {40,50,60,70,30,20,21,45, 10, 20, 30, 40, 50, 60, 70, 80, 9};
        QrCodePolicy.QrTokenCode tokenCode = policy.new QrTokenCode(Arrays.copyOfRange(someChars, 3, someChars.length), setup);
        assertArrayEquals(someChars, tokenCode.getDados().getBytes());
    }
}