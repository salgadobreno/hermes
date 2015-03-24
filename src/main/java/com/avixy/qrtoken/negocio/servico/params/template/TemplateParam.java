package com.avixy.qrtoken.negocio.servico.params.template;

import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.template.Template;

/**
 * Created on 03/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplateParam implements Param {
    private Template template;

    public TemplateParam(Template template) {
        this.template = template;
    }

    @Override
    public String toBinaryString() {
        return template.toBinary();
    }
}
