package com.avixy.qrtoken.negocio.qrcode;

import com.avixy.qrtoken.core.QrUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
* Created on 16/10/2014
*
* @author Breno Salgado <breno.salgado@avixy.com>
*/
public class QrTokenCode {
    Charset CHARSET = Charset.forName("ISO-8859-1");

    private byte[] dados;
    private Integer length;
    private ErrorCorrectionLevel ecLevel;

    /**
     * @param dados conteúdo do QrCode
     * @param setup configuração do QrCode
     */
    public QrTokenCode(byte[] dados, QrSetup setup) {
        this.dados = dados;
        this.ecLevel = setup.getEcLevel();
    }

    public QrTokenCode(byte[] dados, QrSetup setup, int length) {
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
        String qrPayload = new String(dados, CHARSET);

        if (length != null) {
            qrPayload = StringUtils.rightPad(new String(dados, CHARSET), length, '0');
        } // depreciado.. agora fazemos padding no ServiceAssembler

        return qrPayload;
    }

    /**
     * @return Um <code>ByteArrayInputStream</code> que é a imagem desse <code>QrTokenCode</code>
     */
    public InputStream image(){
        return QrUtils.generate(getDados(), ecLevel);
    }

}
