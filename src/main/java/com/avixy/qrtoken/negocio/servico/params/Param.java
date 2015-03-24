package com.avixy.qrtoken.negocio.servico.params;

/**
 * Represents a parameter to be sent to QR Token in a QR Code
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 10/09/2014
 */
public interface Param {
    /**
     * @return a '0' and '1' <code>String</code> representing the <code>byte</code> value of this parameter
     */
    String toBinaryString();

}
