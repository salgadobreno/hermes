package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.Token;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A canvas which is able to render a {@link com.avixy.qrtoken.negocio.template.Template} the way QR Token would
 *
 * Created on 26/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TokenCanvas extends Canvas {
    private Template template = new Template();
    private boolean gridOn = true;

    private IntegerProperty currScreenProperty = new SimpleIntegerProperty(1);
    {
        currScreenProperty.addListener((observable, oldValue, newValue) -> {
            redraw();
        });
    }

    public TokenCanvas() {
        clear();
    }

    public void redraw() {
        clear();
        this.template.templateScreen(currScreenProperty.get()).render(getGraphicsContext2D());
        if (gridOn) {
            drawGrid();
        }
    }

    public void add(TemplateObj templateObj){
        this.template.templateScreen(currScreenProperty.get()).add(templateObj);
        redraw();
    }

    public void remove(TemplateObj templateObj) {
        template.templateScreen(template.screenIndexOf(templateObj)).remove(templateObj);
        redraw();
    }

    public void drawGrid(){
        GraphicsContext gc = getGraphicsContext2D();
        //grid
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        gc.setLineDashes(0);
        int x = 0;
        while (x < Token.DISPLAY_WIDTH) {
            x += 10;
            gc.strokeLine(x,0,x,Token.DISPLAY_HEIGHT);
        }
        int y = 0;
        while (y < Token.DISPLAY_HEIGHT) {
            y += 10;
            gc.strokeLine(0,y,Token.DISPLAY_WIDTH,y);
        }
        //\grid
    }

    private void clear(){
        getGraphicsContext2D().setFill(Color.WHITE);
        getGraphicsContext2D().fillRect(0, 0, Token.DISPLAY_WIDTH, Token.DISPLAY_HEIGHT);
    }

    public void setGridOn(boolean gridOn) {
        this.gridOn = gridOn;
        redraw();
    }

    public void setTemplate(Template template) {
        this.template = template;
        currScreenProperty.set(1);
        redraw();
    }

    public void highlight(TemplateObj templateObj) {
        if (templateObj != null) {
            currScreenProperty.set(template.screenIndexOf(templateObj));
            redraw();
            Rectangle rectangle = templateObj.getBounds();
            if (rectangle != null) {
                getGraphicsContext2D().setStroke(Color.RED);
                getGraphicsContext2D().setLineWidth(2);
                getGraphicsContext2D().strokeLine(rectangle.getX(), rectangle.getY(), rectangle.getX() + rectangle.getWidth(), rectangle.getY());
                getGraphicsContext2D().strokeLine(rectangle.getX(), rectangle.getY() + rectangle.getHeight(), rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());
                getGraphicsContext2D().strokeLine(rectangle.getX() + rectangle.getWidth(), rectangle.getY(), rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());
                getGraphicsContext2D().strokeLine(rectangle.getX(), rectangle.getY(), rectangle.getX(), rectangle.getY() + rectangle.getHeight());
            }
        }
    }

    public IntegerProperty currScreenProperty() {
        return currScreenProperty;
    }
}
