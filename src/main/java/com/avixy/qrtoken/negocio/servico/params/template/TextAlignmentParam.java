package com.avixy.qrtoken.negocio.servico.params.template;

import com.avixy.qrtoken.negocio.servico.params.NBitsParam;
import com.avixy.qrtoken.negocio.template.Text;

/**
 * Created on 17/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TextAlignmentParam extends NBitsParam {
    public TextAlignmentParam(Text.Alignment alignment) {
        super((byte)2, (byte)alignment.ordinal());
    }
}
