package com.avixy.qrtoken.negocio;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

/**
 * A non-instantiable class that wraps and applies business rules concerning QR Code messages
 *
 * Created on 08/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrCodePolicy {
    // Header
    // !u!0010000255
    // !U!0050000233
    //    xxx <- numero de qrs
    //       xxx <- indice do qr
    //          xxxx <- numero de bytes a serem lidos
    public static String MOCK_HEADER = "!u!0010010255";

    private QrCodePolicy(){}

    /**
     * Creates a list of <code>QrSlice</code> for a given <code>content</code>, <code>Version</code>, <code>ErrorCorrectionLevel</code>
     * @param content       String representation of the full content to be made into one or more QR Code messages
     * @param version       QR Code <code>Version</code> to be used in the QR(s)
     * @param ecLevel       <code>ErrorCorrectionLevel</code> to be used in the QR(s)
     * @return              A list of QrSlices
     */
    public static QrSlice[] getQrsFor(String content, Version version, ErrorCorrectionLevel ecLevel) {
        QrSetup setup = new QrSetup(version, ecLevel, content.length());

        int qtdDeQrs = setup.getQrQuantityFor(getHeaderSize());
        int usableBytes = setup.getUsableBytesFor(getHeaderSize());
        int availableCodewordsForSetup = setup.getAvailableBytes();

        QrSlice[] slices = new QrSlice[qtdDeQrs];
        for (int i = 0; i < slices.length; i++) {
            boolean last = i == (slices.length - 1);
            if (last) {
                String data = content.substring(usableBytes * i);
                slices[i] = new QrSlice(calcHeader(qtdDeQrs, i, data.length()).getBytes(), data.getBytes(), availableCodewordsForSetup);
            } else {
                String data = content.substring(usableBytes * i, usableBytes * (i + 1));
                slices[i] = new QrSlice(calcHeader(qtdDeQrs, i, data.length()).getBytes(), data.getBytes(), availableCodewordsForSetup);
            }
        }

        return slices;
    }

    /**
     * @param setup
     * @return The number of bytes that can be used for data as defined in the <code>qrSetup</code>
     */
    public static int usableBytesFor(QrSetup setup){
        return setup.getUsableBytesFor(getHeaderSize());
    }

    /**
     * @param setup
     * @return The number of QR Codes it'll take to send a message as defined by the <code>setup</code>
     */
    public static int qrQtyFor(QrSetup setup){
        return setup.getQrQuantityFor(getHeaderSize());
    }

    /**
     * @param setup
     * @return The number of <code>byte</code> reserved for Error Correction according to the <code>QrSetup</code> specified
     */
    public static int ecBytesFor(QrSetup setup){
        return setup.getErrorCorrectionBytes();
    }

    /**
     * @return      The basic size of a QR Token Header
     */
    public static int getHeaderSize(){
        return MOCK_HEADER.length();
    }

    /**
     * Generates a header for a QR Code
     * @param qrQty         Number of QR Codes in the complete message
     * @param index         Index of the QR Code in the message
     * @param contentSize   The length of the data in a QR Code
     * @return              A <code>String</code> representation of the resulting QR Token header
     */
    public static String calcHeader(int qrQty, int index, int contentSize){
        return String.format("!U!%03d%03d%04d", qrQty, index, contentSize);
    }

}
