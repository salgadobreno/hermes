package com.avixy.qrtoken.negocio.template;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created on 12/02/2015
 *
 * @author I7
 */
public interface TemplateObj {
    void render(GraphicsContext gc);

    default String toBinary() {
        return "";
    }
}
