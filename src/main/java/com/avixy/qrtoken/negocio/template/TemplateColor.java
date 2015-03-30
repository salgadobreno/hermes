package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.params.FourBitParam;
import com.avixy.qrtoken.negocio.servico.params.RGB565Param;
import com.avixy.qrtoken.negocio.servico.params.TwoBytesWrapperParam;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created on 19/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplateColor {

    public TemplateColor(Preset templateColorRgb, Double red, Double green, Double blue) {
        this(templateColorRgb, red.intValue(), green.intValue(), blue.intValue());
    }

    public enum Preset{
        TEMPLATE_COLOR_WHITE(0xff, 0xff, 0xff),       /**< 0000 - Branco R:0xFF G:0xFF B:0xFF */
        TEMPLATE_COLOR_LIGHT_GRAY(0xdc, 0xdc, 0xdc),  /**< 0001 - Cinza claro R:0xDC G:0xDC B:0xDC */
        TEMPLATE_COLOR_GRAY(0xbe, 0xbe, 0xbe),        /**< 0010 - Cinza médio R:0xBE G:0xBE B:0xBE */
        TEMPLATE_COLOR_DARK_GRAY(0x80, 0x80, 0x80),   /**< 0011 - Cinza escuro R:0x80 G:0x80 B:0x80 */
        TEMPLATE_COLOR_BLACK(0x00, 0x00, 0x00),       /**< 0100 - Preto R:0x00 G:0x00 B:0x00 */
        TEMPLATE_COLOR_PURPLE(0x70, 0x30, 0xa0),      /**< 0101 - Roxo R:0x70 G:0x30 B:0xA0 */
        TEMPLATE_COLOR_FLAG_BLUE(0x3e, 0x40, 0x95),   /**< 0110 - Azul Bandeira R:0x3E G:0x40 B:0x95 */
        TEMPLATE_COLOR_LIGHT_BLUE(0x00, 0xb0, 0xf0),  /**< 0111 - Azul Claro R:0x00 G:0xB0 B:0xF0 */
        TEMPLATE_COLOR_FLAG_GREEN(0x00, 0xa8, 0x59),  /**< 1000 - Verde Bandeira R:0x00 G:0xA8 B:0x59 */
        TEMPLATE_COLOR_LIGHT_GREEN(0x92, 0xd0, 0x50), /**< 1001 - Verde Claro R:0x92 G:0xD0 B:0x50 */
        TEMPLATE_COLOR_RED(0xff, 0x00, 0x00),         /**< 1010 - Vermelho R:0xFF G:0x00 B:0x00 */
        TEMPLATE_COLOR_WINE(0xc0, 0x00, 0x00),        /**< 1011 - Vinho R:0xC0 G:0x00 B:0x00 */
        TEMPLATE_COLOR_ORANGE(0xf7, 0x96, 0x46),      /**< 1100 - Laranja R:0xF7 G:0x96 B:0x46 */
        TEMPLATE_COLOR_YELLOW(0xff, 0xcc, 0x29),      /**< 1101 - Amarelo bandeira R:0xFF G:0xCC B:0x29 */
        TEMPLATE_COLOR_FETCH_FROM_MESSAGE(0x00, 0x00, 0x00),     /**< 1110 - A seleÃ§Ã£o de cor vem como argumento em um QR Code - 4 bits */
        TEMPLATE_COLOR_RGB(0xFF,0xFF,0xFF);                    /**< 1111 - Os prÃ³ximos dois bytes descrevem uma cor customizada em formato RGB565 */

        private final int r;
        private final int g;
        private final int b;

        Preset(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public Color toColor(){
            return Color.rgb(r,g,b);
        }

        public boolean isArg() {
            return this == TEMPLATE_COLOR_FETCH_FROM_MESSAGE;
        }
    }

    private final Preset preset;
    private int r,g,b;

    public TemplateColor(Preset preset) {
        this.preset = preset;
    }

    public TemplateColor(Preset preset, int r, int g, int b) {
        if (preset != Preset.TEMPLATE_COLOR_RGB) {
            throw new IllegalArgumentException("rgb constructor is for TEMPLATE_COLOR_RGB preset");
        }
        this.preset = preset;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    private static Map<Preset, TemplateColor> flyweight = new HashMap<>();
    public static TemplateColor get(Preset preset) {
        //NOTE: método chamador que deve fazer próprio tratamento p/ o RGB
        if (flyweight.get(preset) == null) {
            TemplateColor templateColor = new TemplateColor(preset);
            flyweight.put(preset, templateColor);
            return templateColor;
        } else {
            return flyweight.get(preset);
        }
    }

    public Color toColor() {
        if (preset == Preset.TEMPLATE_COLOR_RGB) {
            return Color.rgb(r, g, b);
        } else {
            return preset.toColor();
        }
    }

    public String toBinaryString() {
        if (preset == Preset.TEMPLATE_COLOR_RGB) {
            return new FourBitParam((byte) preset.ordinal()).toBinaryString() +
                    new RGB565Param(r, g, b).toBinaryString();
        }
        return new FourBitParam((byte) preset.ordinal()).toBinaryString();
    }

    public Preset getPreset() {
        return preset;
    }

    public boolean isArg(){
        return preset.isArg();
    }
}
