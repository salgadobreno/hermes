package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.NBitsParam;

import java.util.HashMap;
import java.util.Map;

/**
* Created on 26/03/2015
*
* @author Breno Salgado <breno.salgado@avixy.com>
*/
public class TemplateAlignment {
    public enum Preset {
        LEFT, CENTER, RIGHT, ARGUMENT, CUSTOM;

        public String toBinaryString() {
            return new NBitsParam((byte)2, (byte)ordinal()).toBinaryString();
        }

        public boolean isArg() { return this == ARGUMENT; };
    }

    private final Preset preset;
    private int xPosition;

    public TemplateAlignment(Preset preset) {
        this.preset = preset;
    }

    public TemplateAlignment(Preset preset, int xPosition) {
        this.preset = preset;
        this.xPosition = xPosition;
    }

    private static Map<Preset, TemplateAlignment> flyweight = new HashMap<>();
    public static TemplateAlignment get(Preset alignment) {
        //NOTE: método chamador deve fazer próprio tratamento p/ CUSTOM
        if (flyweight.get(alignment) == null) {
            TemplateAlignment templateAlignment = new TemplateAlignment(alignment);
            flyweight.put(alignment, templateAlignment);
            return templateAlignment;
        } else {
            return flyweight.get(alignment);
        }
    }

    public String toBinaryString() {
        if (preset == Preset.CUSTOM) {
            return preset.toBinaryString() + new ByteWrapperParam((byte) xPosition).toBinaryString();
        } else {
            return preset.toBinaryString();
        }
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public Preset getPreset() {
        return preset;
    }

    static int calcAlignment(Text textObj)  {
        int maxTextLength = 0;
        String[] lines = textObj.getText().split("\\n");
        for (String line : lines) {
            if (maxTextLength < line.length()) maxTextLength = line.length();
        }

        switch (textObj.getAlignment().getPreset()) {
            case CENTER:
                return (Token.DISPLAY_WIDTH - (maxTextLength * textObj.getSize().getWidth())) >> 1;
            case LEFT:
                return Token.HORIZONTAL_MARGIN;
            case RIGHT:
                return (Token.DISPLAY_WIDTH - (textObj.getSize().getWidth() * maxTextLength));
            case ARGUMENT:
                return Token.HORIZONTAL_MARGIN;
            case CUSTOM:
                return textObj.getAlignment().xPosition;
            default:
                throw new IllegalArgumentException("Unexpected alignment");
        }
    }

    public boolean isArg() {
        return preset.isArg();
    }
}
