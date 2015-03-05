package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.FourBitParam;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.params.NBitsParam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Created on 12/02/2015
 *
 * @author I7
 */
public class Text implements TemplateObj {
    public enum Size {
//        MINIMAL(0,0,0),
//        MICRO(0,0,0),
//        SMALL(17, 10, 16),
//        MEDIUM(0,0,0),
//        LARGE(21, 13, 20),
//        HUGE(33, 20, 30);
        MINIMAL(0,0,0), /**< 0000 - Minima: 6x8 - 40 linhas de 40 caracteres*/
        MICRO(0,0,0), /**< 0001 - Micro: 7x12 - 26 linhas de 34 caracteres*/
        SMALL(17,10,16), /**< 0010 - Pequena: 10x16 - 20 linhas de 24 caracteres*/
        MEDIUM(0,0,0), /**< 0011 - Media: 12x20 - 16 linhas de 20 caracteres*/
        LARGE(21,13,20), /**< 0100 - Grande: 20x32 - 10 linhas de 12 caracteres*/
        HUGE(33,20,30), /**< 0101 - Gigante: a definir */
        FONT_0110(0,0,0), /**< 0110 - Reservado */
        FONT_1111(0,0,0), /**< 1111 - Reservado */
        FONT_1000(0,0,0), /**< 1000 - Reservado */
        FONT_1001(0,0,0), /**< 1001 - Reservado */
        FONT_1010(0,0,0), /**< 1010 - Reservado */
        FONT_1011(0,0,0), /**< 1011 - Reservado */
        FONT_1100(0,0,0), /**< 1100 - Reservado */
        FONT_1101(0,0,0), /**< 1101 - Reservado */
        FONT_1110(0,0,0), /**< 1110 - Reservado */
        ARGUMENT(21,13,20);/**< 1111 - A seleção de fonte vem de um QR code - 4 bits*/

        private int value;
        private int width;
        private int height;
        Size(int i, int width, int height) {
            this.value = i;
            this.width = width;
            this.height = height;
        }
        public int getValue() { return value; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
        public String toBinaryString() {
            return new FourBitParam((byte) ordinal()).toBinaryString();
        }
    }
    public enum Alignment {
        LEFT, CENTER, RIGHT, ARGUMENT;

        public String toBinaryString() {
            return new NBitsParam((byte)2, (byte)ordinal()).toBinaryString();
        }
    }

    private int y;
    private TemplateColor color;
    private TemplateColor bgColor;
    private Font font;
    private String text;
    private Text.Size size;
    private Text.Alignment alignment;

    public Text(int y, TemplateColor color, TemplateColor bgColor, Text.Size size, Text.Alignment alignment, String text) {
        this.y = y;
        this.color = color;
        this.bgColor = bgColor;
        this.text = text;
        this.font = new Font("lucida console", size.getValue());
        this.size = size;
        this.alignment = alignment;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFont(font);
        gc.setFill(color.toColor());
        gc.fillText(text, calcAlignment(text.length(), size, alignment), y + size.getHeight() - 3); //TODO
    }

    @Override
    public String toBinary() {
        return TemplateFunction.TEMPLATE_FUNCTION_TEXT.toBinaryString() +
                new ByteWrapperParam((byte) (y / 2)).toBinaryString() +
                color.toBinaryString() +
                bgColor.toBinaryString() +
                size.toBinaryString() +
                alignment.toBinaryString() +
                new HuffmanEncodedParam(text).toBinaryString();
    }

    static int calcAlignment(int textLength, Text.Size size, Text.Alignment alignment) {
        switch (alignment) {
            case CENTER:
                return (Token.DISPLAY_WIDTH - (textLength * size.getWidth())) >> 1;
            case LEFT:
                return Token.HORIZONTAL_MARGIN;
            case RIGHT:
                return (Token.DISPLAY_WIDTH - (size.getWidth() * textLength));
            case ARGUMENT:
                return Token.HORIZONTAL_MARGIN;
            default:
                throw new IllegalArgumentException("Unexpected alignment");
        }
    }
}
