package com.avixy.qrtoken.negocio;

import org.apache.commons.lang.StringUtils;

/**
 * Represents a slice of data to be sent in a QR Code
 *
 * Created on 08/07/2014.
 *
 * @author Breno Salgado <breno.salgado@axivy.com>
 * @since 08/07/2014
 */
public class QrSlice {
    private byte[] header;
    private byte[] dados;
    private int length;

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
        this.header = header;
        this.dados = dados;
        this.length = length;
    }

    /**
     * Returns the full data in the QR Slice
     *
     * @return A new String with the <code>header</code> + <code>dados</code>, padded to the right with zeroes
     */
    public String getDados(){
        String str = new String(header).concat(new String(dados));
        return StringUtils.rightPad(str, length, '0');
    }
}
