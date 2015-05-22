package com.avixy.qrtoken.negocio.qrcode;

import com.avixy.qrtoken.core.QrUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
* Created on 16/10/2014
*
* @author Breno Salgado <breno.salgado@avixy.com>
*/
public class QrTokenCode {
    Charset CHARSET = Charset.forName("ISO-8859-1");

    private byte[] dados;
    private ErrorCorrectionLevel ecLevel;

    /**
     * @param dados QR Code data
     * @param setup QR Code configuration
     */
    public QrTokenCode(byte[] dados, QrSetup setup) {
        this.dados = dados;
        this.ecLevel = setup.getEcLevel();
    }

    public QrTokenCode(byte[] dados, QrSetup setup, int length) {
        if ((dados.length) > length) { throw new IllegalArgumentException("Length can't be shorter than the data"); }

        this.dados = dados;
        this.ecLevel = setup.getEcLevel();
    }

    /**
     * @return A new String with <code>dados</code> padded to the right with zeros(forcing ZXing to use the {@link com.avixy.qrtoken.negocio.qrcode.QrSetup}s version)
     *
     * <code>CHARSET</code> ISO-8859-1 is used to assure that original <code>bytes</code> are not lost in translation
     */
    public String getDados(){

        return new String(dados, CHARSET);
    }

    /**
     * @return A <code>ByteArrayInputStream</code> which is this <code>QrTokenCode</code>s image
     */
    public InputStream image(){
        return QrUtils.generate(getDados(), ecLevel);
    }

}
