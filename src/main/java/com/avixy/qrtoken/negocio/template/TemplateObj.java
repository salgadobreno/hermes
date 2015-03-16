package com.avixy.qrtoken.negocio.template;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * Created on 12/02/2015
 *
 * @author I7
 */
public interface TemplateObj {
    void render(GraphicsContext gc);

    Rectangle getBounds();

    default String toBinary() {
        return "";
    }
}
