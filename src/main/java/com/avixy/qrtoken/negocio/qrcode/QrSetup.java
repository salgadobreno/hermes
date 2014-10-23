package com.avixy.qrtoken.negocio.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

import javax.annotation.Nullable;

/**
 * Encapsula uma configuração de QR, provê métodos util sobre um set-up de QR.
 * Assume-se encoding Byte. ver: QR Code specification ISO/IEC 18004:2000.
 * @author Breno Salgado <breno.salgado@axivy.com>
 *
 * Created on 08/07/2014.
 */
public class QrSetup {

    private Version version;

    private ErrorCorrectionLevel ecLevel;

    /**
     * @param version Versão de QR Code do setup
     * @param ecLevel Nível de correção de erro
     */
    public QrSetup(@Nullable Version version, ErrorCorrectionLevel ecLevel) {
        this.version = version;
        this.ecLevel = ecLevel;
    }

    /**
     * @return O número de bytes disponível para inclusão de dados nesse setup
     */
    public int getAvailableBytes(){
        return version.getTotalCodewords() - getEcBytes() - getBytesForModeAndCharCount();
    }

    /**
     * @return O número de bytes reservados pra correção de erro nesse setup
     */
    public int getEcBytes(){
        return version.getECBlocksForLevel(ecLevel).getTotalECCodewords();
    }

    /**
     * @return O número total de palavras-chave de 8 bits possível em um QR nesse setup
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

    public double getQrQtyFor(Integer length){
        return Math.ceil((length.doubleValue()) / getAvailableBytes());
    }
}
