package com.avixy.qrtoken.negocio.servico.params.template;

import com.avixy.qrtoken.negocio.servico.params.FourBitParam;
import com.avixy.qrtoken.negocio.template.TemplateColor;

/**
 * Created on 17/03/2015
 *
 * @author I7
 */
public class TemplateColorParam extends FourBitParam {
    public TemplateColorParam(TemplateColor templateColor) {
        super((byte) templateColor.getPreset().ordinal());
    }
}
