package com.avixy.qrtoken.negocio.template;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created on 26/02/2015
 *
 * @author I7
 */
public class TokenCanvas extends Canvas {
    private Template template = new Template();
    boolean gridOn = true;

    public TokenCanvas() {
        clear();
    }

    public void redraw() {
        clear();
        this.template.render(getGraphicsContext2D());
        if (gridOn) {
            drawGrid();
        }
    }

    public void add(TemplateObj templateObj){
        this.template.add(templateObj);
        redraw();
    }

    public void drawGrid(){
        GraphicsContext gc = getGraphicsContext2D();
        //grid
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
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
        redraw();
    }
}
