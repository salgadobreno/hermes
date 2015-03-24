package com.avixy.qrtoken.negocio.template;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * An object that can passed in a {@link com.avixy.qrtoken.negocio.template.Template} to QR Token
 *
 * Created on 12/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface TemplateObj {
    void render(GraphicsContext gc);

    Rectangle getBounds();

    default String toBinary() {
        return "";
    }
}
