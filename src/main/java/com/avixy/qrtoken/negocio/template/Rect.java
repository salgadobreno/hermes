package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * Created on 12/02/2015
 *
 * @author I7
 */
public class Rect implements TemplateObj {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected TemplateColor color;

    public Rect(double x, double width, double y, double height, TemplateColor color) {
        this.x = x;
        this.width = width;
        this.y = y;
        this.height = height;
        this.color = color;
    }

    public Rect(Integer x, Integer width, Integer y, Integer height, TemplateColor color) {
        this(x.doubleValue(), width.doubleValue(), y.doubleValue(), height.doubleValue(), color);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(color.toColor());
        gc.fillRect(x, y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public String toBinary(){
        return TemplateFunction.TEMPLATE_FUNCTION_RECTANGLE.toBinaryString() +
                new ByteWrapperParam((byte) (x)).toBinaryString() +
                new ByteWrapperParam((byte) (width)).toBinaryString() +
                new ByteWrapperParam((byte) (y/2)).toBinaryString() +
                new ByteWrapperParam((byte) (height/2)).toBinaryString() +
                color.toBinaryString();
    }

    @Override
    public String toString() {
        return "Rect";
    }

    public Double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public TemplateColor getColor() {
        return color;
    }

    public void setColor(TemplateColor color) {
        this.color = color;
    }
}
