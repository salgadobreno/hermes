package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.Token;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;

/**
 * Created on 12/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class Stripe extends Rect {
    public Stripe(double y, double height, TemplateColor color) {
        super(0, Token.DISPLAY_WIDTH, y, height, color);
    }

    @Override
    public String toBinary() {
        return TemplateFunction.TEMPLATE_FUNCTION_STRIPE.toBinaryString() +
                new ByteWrapperParam((byte) (y/2)).toBinaryString() +
                new ByteWrapperParam((byte) (height/2)).toBinaryString() +
                color.toBinaryString();
    }

    @Override
    public String toString() {
        return "Stripe";
    }
}
