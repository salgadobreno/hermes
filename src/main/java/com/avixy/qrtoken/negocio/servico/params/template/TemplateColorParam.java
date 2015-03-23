package com.avixy.qrtoken.negocio.servico.params.template;

import com.avixy.qrtoken.negocio.servico.params.FourBitParam;
import com.avixy.qrtoken.negocio.servico.params.Param;
import com.avixy.qrtoken.negocio.template.TemplateColor;

/**
 * Created on 17/03/2015
 *
 * @author I7
 */
public class TemplateColorParam implements Param {
    private TemplateColor templateColor;
    public TemplateColorParam(TemplateColor templateColor) {
        this.templateColor = templateColor;
    }


    @Override
    public String toBinaryString() {
        return templateColor.toBinaryString();
    }
}
