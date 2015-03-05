package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import javafx.scene.canvas.GraphicsContext;

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
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Rect(Integer x, Integer width, Integer y, Integer height, TemplateColor color) {
        this(x.doubleValue(), y.doubleValue(), width.doubleValue(), height.doubleValue(), color);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(color.toColor());
        gc.fillRect(x, y, width, height);
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
}
