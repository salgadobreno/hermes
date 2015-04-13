package com.avixy.qrtoken.negocio.template;

/**
* Created on 09/04/2015
*
* @author Breno Salgado <breno.salgado@avixy.com>
*/
public enum TemplateSize {
    SHORT(200);
    private final int size;

    TemplateSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public static TemplateSize getTemplateSizeFor(int templateIndex) {
        if (templateIndex >= 0 && templateIndex < 15) {
            return SHORT;
        } else {
            throw new IllegalArgumentException("Invalid index");
        }
    };
}
