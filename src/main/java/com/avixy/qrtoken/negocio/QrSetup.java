package com.avixy.qrtoken.negocio;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

/**
 * Encapsulates a <code>Version</code>, <code>ErrorCorrectionLevel</code> and content <code>length</code> and provides
 * utility methods related to QR Code data calculations
 *
 * Assumes Byte encoding. See: QR Code specification ISO/IEC 18004:2000
 *
 * Created on 08/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrSetup {

    private Version version;

    private ErrorCorrectionLevel ecLevel;

    private Integer contentSize;

    /**
     * @param version
     * @param ecLevel
     * @param contentSize
     */
    public QrSetup(Version version, ErrorCorrectionLevel ecLevel, int contentSize) {
        this.version = version;
        this.ecLevel = ecLevel;
        this.contentSize = contentSize;
    }

    /**
     * @param headerSize
     * @return The number of QR Codes it'll take to send a message in this setup given a header of
     * <code>headerSize</code>
     */
    public int getQrQuantityFor(int headerSize){
        Double div = Math.ceil(contentSize.doubleValue() / getUsableBytesFor(headerSize));
        return div.intValue();
    }

    /**
     * @param headerSize
     * @return The number of bytes that can be used for data in this setup given a header of <code>headerSize</code>
     */
    public int getUsableBytesFor(int headerSize){
        return getAvailableBytes() - headerSize;
    }

    /**
     * @return The number of data bytes available for use in this setup
     */
    public int getAvailableBytes(){
        return version.getTotalCodewords() - getErrorCorrectionBytes() - getBytesForModeAndCharCount();
    }

    /**
     * @return The number of bytes reserved for Error Correction in this setup
     */
    public int getErrorCorrectionBytes(){
        return version.getECBlocksForLevel(ecLevel).getTotalECCodewords();
    }

    /**
     * @return The total number of 8-bit codewords possible for a QR Code in this setup
     */
    public int getTotalBytes() {
        return version.getTotalCodewords();
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
}
