package com.avixy.qrtoken.negocio.qrcode;

import org.apache.commons.lang.ArrayUtils;

import java.nio.charset.Charset;
import java.util.Arrays;

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
    static Charset CHARSET = Charset.forName("ISO-8859-1");

    /**
     * Creates a list of <code>QrSlice</code> for a given <code>setup</code>
     * @param setup
     * @return              A list of QrSlices
     */
    public QrSlice[] getQrsFor(QrSetup setup) {
        int qtdDeQrs = setup.getQrQuantity();
        int usableBytes = setup.getUsableBytes();
        int availableCodewordsForSetup = setup.getAvailableBytes();
        byte[] content = setup.getContent();

        QrSlice[] slices = new QrSlice[qtdDeQrs];
        for (int i = 0; i < slices.length; i++) {
            boolean last = i == (slices.length - 1);
            byte[] header;
            byte[] data;
            if (last) {
                data = Arrays.copyOfRange(content, usableBytes * i, content.length);
                header = calcHeader(setup, i);
                slices[i] = new QrSlice(ArrayUtils.addAll(header, data), availableCodewordsForSetup);
            } else {
                data = Arrays.copyOfRange(content, usableBytes * i, usableBytes * (i + 1));
                header = calcHeader(setup, i);
                slices[i] = new QrSlice(ArrayUtils.addAll(header, data), availableCodewordsForSetup);
            }
        }

        return slices;
    }

    /**
     * @return      The basic size of a QR Token Header
     */
    public int getHeaderSize(){
        return MOCK_HEADER.length();
    }

    /**
     * Generates a header for a QR Code
     * @param index         Index of the QR Code in the message
     * @return              A <code>String</code> representation of the resulting QR Token header
     */
    public byte[] calcHeader(QrSetup setup, int index){
        return String.format("!U!%03d%03d%04d", setup.getQrQuantity(), index, setup.getContentLength()).getBytes(CHARSET);
    }

}
