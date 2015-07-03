package com.avixy.qrtoken.negocio.servico.params.template;

import com.avixy.qrtoken.negocio.servico.params.FiveBitParam;

/**
 * Created on 23/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplateSlotParam extends FiveBitParam {
    public TemplateSlotParam(byte template) {
        super(template);
        if (template > 17 || template < 0){
            throw new IllegalArgumentException("Template value must be between 0 and 17.");
        }
    }

}
