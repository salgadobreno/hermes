package com.avixy.qrtoken.core;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created on 08/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrUtils {

    /**
     * Generates a QR Code
     *
     * @param dados
     * @param ecLevel
     * @return A ByteArrayInputStream of the QR Code image.
     */
    public static InputStream generate(String dados, ErrorCorrectionLevel ecLevel){
        QRCode qrCode = QRCode.from(dados).withCharset("ISO-8859-1");
        qrCode.withSize(500, 500).to(ImageType.PNG);
        qrCode.withErrorCorrection(ecLevel);

        return new ByteArrayInputStream(qrCode.stream().toByteArray());
    }
}
