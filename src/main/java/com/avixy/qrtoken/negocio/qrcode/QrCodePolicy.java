package com.avixy.qrtoken.negocio.qrcode;

import com.avixy.qrtoken.core.QrUtils;
import com.avixy.qrtoken.negocio.servico.Service;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto que define e aplica regras do Avixy QR Token para os códigos QR
 *
 * Created on 08/07/2014.
 * @author Breno Salgado <breno.salgado@axivy.com>
 */
public class QrCodePolicy {
    public static int HEADER_SIZE = 3;

    public QrCodePolicy() {}

    /**
     * @return O tamanho básico do header de um QR
     */
    public int getHeaderSize(){
        return HEADER_SIZE;
    }

    /**
     * @param service
     * @param setup
     * @return Um <code>List</code> de <code>QrTokenCode</code>. Não necessáriamente será mais de um, o container deve
     * se virar p/ tratar 0, 1 ou muitos QRs de retorno apropriadamente.
     */
    public List<QrTokenCode> getQrs(Service service, QrSetup setup) throws GeneralSecurityException {
        //TODO: implement multiple QRs
        // verifica se precisa de mais de 1 qr ...
        byte[] data = service.getData();
        byte[] header = getHeader(service);
        QrTokenCode tokenCode = new QrTokenCode(header, data, setup);
        List<QrTokenCode> tokenCodeList = new ArrayList<>();
        tokenCodeList.add(tokenCode);

        return tokenCodeList;
    }

    /**
     * @param service
     * @return Tripa de dados para o *header* pra esse <code>Service</code> dentro dessa política de QRs
     * <code>Policy</code>
     */
    public byte[] getHeader(Service service) {
        byte[] header = new byte[getHeaderSize()];
        // byte[0] e byte[1] == 0 por default
        header[2] = (byte) service.getServiceCode();
        return header;
    }

    public class QrTokenCode {
        Charset CHARSET = Charset.forName("ISO-8859-1");

        private byte[] dados;
        private int length;
        private ErrorCorrectionLevel ecLevel;

        /**
         * @param header bytes de header
         * @param dados conteúdo do QrCode
         * @param setup configuração do QrCode
         */
        public QrTokenCode(byte[] header, byte[] dados, QrSetup setup) {
            this.length = setup.getAvailableBytes();
            if ((header.length + dados.length) > length) { throw new IllegalArgumentException("Length can't be shorter than the data"); }

            this.dados = ArrayUtils.addAll(header, dados);
            this.ecLevel = setup.getEcLevel();
        }

        /**
         * @return A new String with <code>dados</code> padded to the right with zeros
         * @return <code>String</code> com os dados definitivo desse Qr Code. A <code>String</code> tem padding
         * à direita com zeros pra forçar o ZXing a usar a versão do <code>setup</code> definida, e usa o
         * <code>CHARSET</code> ISO-8859-1 pra assegurar que os bytes originais não são perdidos na transição p/ String
         */
        public String getDados(){
            return StringUtils.rightPad(new String(dados, CHARSET), length, '0');
        }

        /**
         * @return Um <code>ByteArrayInputStream</code> que é a imagem desse <code>QrTokenCode</code>
         */
        public InputStream image(){
            return QrUtils.generate(getDados(), ecLevel);
        }

    }
}
