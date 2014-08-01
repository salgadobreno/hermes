package com.avixy.qrtoken.negocio.qrcode;

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
    static Charset CHARSET = Charset.forName("ISO-8859-1");

    /**
     * Creates a new QrSlice.
     *
     * @param dados full content of the QrSlice in bytes
     * @param length full length for the data in the slice. <code>length</code> can't be smaller than the combined size
     *               of <code>header</code> and <code>dados</code> otherwise it will throw an
     *               <code>IllegalArgumentException</code>
     */
    public QrSlice(byte[] dados, int length) {
        if (dados.length > length) { throw new IllegalArgumentException("Length can't be shorter than the data"); }
        this.length = length;
        this.dados = dados;
    }

    /**
     * Returns the full data in the QR Slice
     *
     * @return A new String with <code>dados</code> padded to the right with zeros
     */
    public String getDados(){
        return StringUtils.rightPad(new String(dados, CHARSET), length, '0');
    }
}
