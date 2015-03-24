package com.avixy.qrtoken.negocio.template;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created on 26/02/2015
 *
 * @author I7
 */
public class TokenCanvas extends Canvas {
    private Template template = new Template();
    private boolean gridOn = true;
    private double scaleY;
    private double scaleX;

    public TokenCanvas() {
        clear();
    }

    public void redraw(){
        redraw(1);
    }

    public void redraw(int subTemplate) {
        clear();
        this.template.subTemplate(subTemplate).render(getGraphicsContext2D());
        if (gridOn) {
            drawGrid();
        }
    }

    public void add(TemplateObj templateObj, int subTemplate){
        this.template.subTemplate(subTemplate).add(templateObj);
        redraw(subTemplate);

    }

    public void remove(TemplateObj templateObj, int subTemplate) {
//        this.template.getTemplateObjs().remove(templateObj);
        this.template.subTemplate(subTemplate).remove(templateObj);
        redraw(subTemplate);
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

    public void setGridOn(boolean gridOn, int currTemplate) {
        this.gridOn = gridOn;
        redraw(currTemplate);
    }

    public void setTemplate(Template template, int subTemplate) {
        this.template = template;
        redraw(subTemplate);
    }

    public void setTemplate(Template template) {
        this.template = template;
        redraw();
    }

    public void highlight(TemplateObj templateObj) {
        redraw(template.subTemplateOf(templateObj));
        if (templateObj != null) {
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
}
