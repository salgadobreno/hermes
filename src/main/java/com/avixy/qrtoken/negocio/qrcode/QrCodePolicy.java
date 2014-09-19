package com.avixy.qrtoken.negocio.qrcode;

import com.avixy.qrtoken.core.QrUtils;
import com.avixy.qrtoken.negocio.servico.servicos.Service;
import com.avixy.qrtoken.negocio.servico.header.HeaderPolicy;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.crypto.CryptoException;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto que orquestra a criação dos Qr Codes para o Qr Token
 * @author Breno Salgado <breno.salgado@axivy.com>
 *
 * Created on 08/07/2014.
 */
public class QrCodePolicy {
    public static int HEADER_SIZE = 3;
    private HeaderPolicy headerPolicy;

    /** TODO:
     * implement multiple QRs
     */

    public QrCodePolicy(HeaderPolicy headerPolicy) {
        this.headerPolicy = headerPolicy;
    }

    /**
     * @param service
     * @param setup
     * @return Um <code>List</code> de <code>QrTokenCode</code>. Não necessáriamente será mais de um, o container deve
     * se virar p/ tratar 0, 1 ou muitos QRs de retorno apropriadamente.
     */
    public List<QrTokenCode> getQrs(Service service, QrSetup setup) throws GeneralSecurityException, CryptoException {
        // verifica se precisa de mais de 1 qr ...
        byte[] data = ArrayUtils.addAll(headerPolicy.getHeader(service), service.getData());
        QrTokenCode tokenCode = new QrTokenCode(data, setup);
        List<QrTokenCode> tokenCodeList = new ArrayList<>();
        tokenCodeList.add(tokenCode);

        return tokenCodeList;
    }

    public class QrTokenCode {
        Charset CHARSET = Charset.forName("ISO-8859-1");

        private byte[] dados;
        private int length;
        private ErrorCorrectionLevel ecLevel;

        /**
         * @param dados conteúdo do QrCode
         * @param setup configuração do QrCode
         */
        public QrTokenCode(byte[] dados, QrSetup setup) {
            this.length = setup.getAvailableBytes();
            if ((dados.length) > length) { throw new IllegalArgumentException("Length can't be shorter than the data"); }

            this.dados = dados;
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
        public InputStream image(){ return QrUtils.generate(getDados(), ecLevel); }

    }
}
