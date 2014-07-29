package com.avixy.qrtoken.negocio;

import org.apache.commons.lang.StringUtils;

import java.nio.charset.Charset;

/**
 * Represents a slice of data to be sent in a QR Code
 *
 * Created on 08/07/2014.
 *
 * @author Breno Salgado <breno.salgado@axivy.com>
 * @since 08/07/2014
 */
public class QrSlice {
    private byte[] dados;
    private int length;
    Charset CHARSET = Charset.forName("ISO-8859-1");

    /**
     * Creates a new QrSlice.
     *
     * @param header bytes for header
     * @param dados bytes for data
     * @param length full length for the data in the slice. <code>length</code> can't be smaller than the combined size
     *               of <code>header</code> and <code>dados</code> otherwise it will throw an
     *               <code>IllegalArgumentException</code>
     */
    public QrSlice(byte[] header, byte[] dados, int length) {
        if (header.length + dados.length > length) { throw new IllegalArgumentException("Length can't be shorter than the data"); }
        this.length = length;
        this.dados = new byte[header.length + dados.length];

        System.arraycopy(header, 0, this.dados, 0, header.length);
        System.arraycopy(dados, 0, this.dados, header.length, dados.length);
    }

    /**
     * Returns the full data in the QR Slice
     *
     * @return A new String with the <code>header</code> + <code>dados</code>, padded to the right with zeroes
     */
    public String getDados(){
        return StringUtils.rightPad(new String(dados, CHARSET), length, '0');
    }
}
