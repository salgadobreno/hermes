package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.params.NBitsParam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * Created on 11/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class WaitForButton implements TemplateObj {
    public enum NextAction {
        POWER_OFF, 	/**< 000 - Pressionar o botão desliga o token */
        NEW_SCREEN;	/**< 001 - Pressionar o botão troca para uma nova tela */

        public String toBinaryString() {
            return new NBitsParam((byte)3, (byte)ordinal()).toBinaryString();
        }
    }
    public static final int ARG_WAIT_SECONDS = 31;
    private int waitSeconds;
    private NextAction nextAction;

    public WaitForButton(int waitSeconds, NextAction nextAction) {
        this.waitSeconds = waitSeconds;
        this.nextAction = nextAction;
    }

    @Override
    public void render(GraphicsContext gc) { }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public String toBinary() {
        return TemplateFunction.TEMPLATE_FUNCTION_WAIT_FOR_BUTTON.toBinaryString() +
                new NBitsParam((byte)5, (byte)waitSeconds).toBinaryString() +
                nextAction.toBinaryString();
    }

    public int getWaitSeconds() {
        return waitSeconds;
    }

    public void setWaitSeconds(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    public NextAction getNextAction() {
        return nextAction;
    }

    public void setNextAction(NextAction nextAction) {
        this.nextAction = nextAction;
    }

    @Override
    public String toString() {
        return "WaitForButton{" + waitSeconds + ", " + nextAction + '}';
    }
}
