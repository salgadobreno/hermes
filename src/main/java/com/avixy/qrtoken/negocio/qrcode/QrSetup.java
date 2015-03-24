package com.avixy.qrtoken.negocio.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

import javax.annotation.Nullable;

/**
 * Encapsulates a QR Code configuration
 * Byte encoding assumed, see: QR Code specification ISO/IEC 18004:2000
 *
 * @author Breno Salgado <breno.salgado@axivy.com>
 *
 * Created on 08/07/2014.
 */
public class QrSetup {

    private Version version;

    private ErrorCorrectionLevel ecLevel;

    /**
     * @param version QR Code version
     * @param ecLevel Error Correction level
     */
    public QrSetup(@Nullable Version version, ErrorCorrectionLevel ecLevel) {
        this.version = version;
        this.ecLevel = ecLevel;
    }

    /**
     * @return Number of <code>bytes</code> available for data in this setup
     */
    public int getAvailableBytes(){
        return version.getTotalCodewords() - getEcBytes() - getBytesForModeAndCharCount();
    }

    /**
     * @return Number of <code>bytes</code> reserved for Error Correction in this setup
     */
    public int getEcBytes(){
        return version.getECBlocksForLevel(ecLevel).getTotalECCodewords();
    }

    /**
     * @return Total number of keywords(bytes) possible in this setup
     */
    public int getTotalBytes() {
        return version.getTotalCodewords();
    }

    public ErrorCorrectionLevel getEcLevel() {
        return ecLevel;
    }

    private int getBytesForModeAndCharCount(){
        // Ver ISO/IEC 18004:2000 Table 2
        // 4 bits são para indicação de modo
        // 8 ou 16 bits pra indicação de length
        // +4 bits são completados com terminator(0000), omitido ou abreviado se não houver espaço.
        int versionNumber = version.getVersionNumber();
        if (versionNumber < 10){
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * @param length size of the data
     * @return Number of QR Codes needed to send a message with size <code>length</code> in this setup
     */
    public double getQrQtyFor(Integer length){
        return Math.ceil((length.doubleValue()) / getAvailableBytes());
    }
}
