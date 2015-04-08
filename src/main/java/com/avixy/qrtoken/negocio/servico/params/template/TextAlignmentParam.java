package com.avixy.qrtoken.negocio.servico.params.template;

import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.template.Text;

/**
 * Created on 17/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TextAlignmentParam implements Param {
    private Text.Alignment alignment;
    public TextAlignmentParam(Text.Alignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public String toBinaryString() {
        return alignment.toBinaryString();
    }
}
