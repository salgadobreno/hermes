package com.avixy.qrtoken.negocio;

import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;

/**
 * Created on 08/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TestHelper {
    private static QrtHeaderPolicy headerPolicy = new QrtHeaderPolicy();

    public static QrtHeaderPolicy getHeaderPolicy(){
        return headerPolicy;
    }

}
