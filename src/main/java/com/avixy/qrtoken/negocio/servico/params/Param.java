package com.avixy.qrtoken.negocio.servico.params;

/**
 * Um <code>Param</code> é um parâmetro que é enviado para o Qr Token dentro de um código QR.
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 10/09/2014
 */
public interface Param {
    /**
     * @return representação binária em String de '0' e '1' do parâmetro.
     */
    String toBinaryString();

}
