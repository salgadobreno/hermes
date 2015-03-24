package com.avixy.qrtoken.negocio.servico.params.template;

import com.avixy.qrtoken.negocio.servico.params.FourBitParam;
import com.avixy.qrtoken.negocio.template.Text;

/**
 * Created on 17/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TextSizeParam extends FourBitParam {
    public TextSizeParam(Text.Size size) {
        super((byte)size.ordinal());
    }
}
