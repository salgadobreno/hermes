package com.avixy.qrtoken.negocio.template;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * An element that can be rendered by QR Token and {@link com.avixy.qrtoken.negocio.template.TokenCanvas}
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
