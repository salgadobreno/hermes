package com.avixy.qrtoken.negocio.servico.params.template;

import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.template.TemplateAlignment;

/**
 * Created on 17/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TextAlignmentParam implements Param {
    private TemplateAlignment templateAlignment;
    public TextAlignmentParam(TemplateAlignment alignment) {
        this.templateAlignment = alignment;
    }

    @Override
    public String toBinaryString() {
        return templateAlignment.toBinaryString();
    }
}
