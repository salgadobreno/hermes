package com.avixy.qrtoken.core;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Breno Salgado <breno.salgado@axivy.com>
 *
 * Created on 08/07/2014.
 */
public class QrUtils {

    /**
     * Gera um código QR a partir do <code>dados</code>
     *
     * @param dados
     * @param ecLevel
     * @return Um ByteArrayInputStream que é a imagem do QR Code
     */
    public static InputStream generate(String dados, ErrorCorrectionLevel ecLevel){
        /* ISO-8859-1 garante que não terá perda de dados em conversão de Strings em byte[] */
        QRCode qrCode = QRCode.from(dados).withCharset("ISO-8859-1");
        qrCode.withSize(500, 500).to(ImageType.PNG);
        qrCode.withErrorCorrection(ecLevel);

        return new ByteArrayInputStream(qrCode.stream().toByteArray());
    }
}
