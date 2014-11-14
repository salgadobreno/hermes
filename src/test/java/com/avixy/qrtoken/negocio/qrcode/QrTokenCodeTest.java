package com.avixy.qrtoken.negocio.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created on 25/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrTokenCodeTest {
    QrCodePolicy policy = new BasicQrCodePolicy();
    byte[] header = "hhhhh".getBytes();
    byte[] dados = "dados".getBytes();
    QrSetup setup = new QrSetup(Version.getVersionForNumber(1), ErrorCorrectionLevel.L);
    // http://www.qrcode.com/en/about/version.html
    // Version 1, ECC Low => 17 binary bytes capacity

    @Test
    public void testConstructor(){
        QrTokenCode slice = new QrTokenCode(dados, setup);
        assertNotNull(slice);
    }

    @Test(expected = IllegalArgumentException.class) @Ignore("acho que esta defasado")
    public void testGetDadosArguments() {
        /* should throw error if dados is bigger than the length of setup */
        QrTokenCode tokenCode = new QrTokenCode(new byte[300], setup);
    }

    @Test @Ignore(value = "defasado")
    public void testGetDadosPadding(){
        /* should right-pad the result */
        QrSetup bigSetup = new QrSetup(Version.getVersionForNumber(10), ErrorCorrectionLevel.L);
        QrTokenCode tokenCode = new QrTokenCode(dados, bigSetup);
        assertEquals(tokenCode.getDados().charAt(tokenCode.getDados().length() - 1), '0');
    }
}