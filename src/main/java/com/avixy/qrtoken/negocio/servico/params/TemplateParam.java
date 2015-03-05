package com.avixy.qrtoken.negocio.servico.params;

import com.avixy.qrtoken.negocio.template.Template;

/**
 * Created on 03/03/2015
 *
 * @author I7
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
