package com.avixy.qrtoken.negocio.qrcode;

import com.avixy.qrtoken.core.QrUtils;
import com.avixy.qrtoken.negocio.servico.Service;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Class that wraps and applies business rules concerning QR Code messages and Services
 *
 * Created on 08/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrCodePolicy {
    public static int HEADER_SIZE = 3;
    static Charset CHARSET = Charset.forName("ISO-8859-1");

    public QrCodePolicy() {}

    /**
     * @return      The basic size of a QR Token Header
     */
    public int getHeaderSize(){
        return HEADER_SIZE;
    }

    public InputStream getQr(Service service, QrSetup setup) {
        byte[] data = service.exec();
        byte[] header = getHeader(service);
        QrTokenCode tokenCode = new QrTokenCode(header, data, setup.getAvailableBytes());

        return QrUtils.generate(tokenCode.getDados(), setup.getEcLevel());
    }

    public byte[] getHeader(Service service) {
        byte[] header = new byte[getHeaderSize()];
        // byte[0] e byte[1] == 0 por default
        header[2] = service.getServiceCode();
        return header;
    }

    private class QrTokenCode {
        private byte[] dados;
        private int length;
        Charset CHARSET = Charset.forName("ISO-8859-1");

        /**
         * Creates a new QrSlice.
         *
         * @param dados full content of the QrSlice in bytes
         * @param length full length for the data in the slice. <code>length</code> can't be smaller than the combined size
         *               of <code>header</code> and <code>dados</code> otherwise it will throw an
         *               <code>IllegalArgumentException</code>
         */
        public QrTokenCode(byte[] header, byte[] dados, int length) {
            if (dados.length > length) { throw new IllegalArgumentException("Length can't be shorter than the data"); }
            this.dados = ArrayUtils.addAll(header, dados);
            this.length = length;
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
}
