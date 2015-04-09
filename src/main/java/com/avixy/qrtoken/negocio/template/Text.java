package com.avixy.qrtoken.negocio.template;

import com.avixy.qrtoken.negocio.Token;
import com.avixy.qrtoken.negocio.servico.params.ByteWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.FourBitParam;
import com.avixy.qrtoken.negocio.servico.params.HuffmanEncodedParam;
import com.avixy.qrtoken.negocio.servico.params.NBitsParam;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 12/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class Text implements TemplateObj {
    public enum Alignment {
        LEFT, CENTER, RIGHT, ARGUMENT;

        public String toBinaryString() {
            return new NBitsParam((byte)2, (byte)ordinal()).toBinaryString();
        }

        public boolean isArg() { return this == ARGUMENT; };

        static int calcAlignment(Text textObj)  {
            int maxTextLength = 0;
            String[] lines = textObj.getText().split("\\n");
            for (String line : lines) {
                if (maxTextLength < line.length()) maxTextLength = line.length();
            }

            switch (textObj.getAlignment()) {
                case CENTER:
                    return (Token.DISPLAY_WIDTH - (maxTextLength * textObj.getSize().getWidth())) >> 1;
                case LEFT:
                    return Token.HORIZONTAL_MARGIN;
                case RIGHT:
                    return (Token.DISPLAY_WIDTH - (textObj.getSize().getWidth() * maxTextLength));
                case ARGUMENT:
                    return Token.HORIZONTAL_MARGIN;
                default:
                    throw new IllegalArgumentException("Unexpected alignment");
            }
        }
    }

    public enum Size {
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
        public boolean isArg() { return this == ARGUMENT; };
    }

    private int y;
    private TemplateColor color;
    private TemplateColor bgColor;
    private Font font;
    private String text;
    private Text.Size size;
    private Text.Alignment alignment;

    public static final String ARG_TEXT_FOR_DISPLAY = "{arg}";
    public static final String TEXT_FROM_ARGUMENT = ARG_TEXT_FOR_DISPLAY;

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
        for (TextToken textToken : allTokens()) {
            Rectangle r = textToken.getBounds();
            gc.setFill(textToken.getBgColor().toColor());
            gc.fillRect(r.getX(), r.getY(), textToken.getText().length() * textToken.getSize().getWidth(), r.getHeight());
            gc.setFont(textToken.getFont());
            gc.setFill(textToken.getColor().toColor());
            gc.fillText(textToken.getText(), Text.Alignment.calcAlignment(textToken), textToken.getY() + size.getHeight() - 3); //TODO
        }
    }

    @Override
    public Rectangle getBounds() {
        double x,y,w,h;
        double maxX = 0;
        x = y = 999;
        w = h = 0;
        for (TextToken textToken : allTokens()) {
            x = textToken.getBounds().getX() < x ? textToken.getBounds().getX() : x;
            maxX = textToken.getBounds().getX() + textToken.getBounds().getWidth() > maxX ? textToken.getBounds().getX() + textToken.getBounds().getWidth() : maxX;
            y = textToken.getBounds().getY() < y ? textToken.getBounds().getY() : y;
            w = textToken.getBounds().getWidth() > w ? textToken.getBounds().getWidth() : w;
            h = textToken.getBounds().getHeight() + textToken.getBounds().getY() > h ? textToken.getBounds().getHeight() + textToken.getBounds().getY() : h;
        }
        return new Rectangle(x, y, maxX - x, (h - y));
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

    private List<TextToken> allTokens() {
        List<TextToken> textTokens = new ArrayList<>();
        String[] lines = text.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            textTokens.add(new TextToken(y + (size.getHeight() * i),
                            color,
                            bgColor,
                            size,
                            alignment,
                            lines[i])
            );
        }

        return textTokens;
    }

    class TextToken extends Text {
        public TextToken(int y, TemplateColor color, TemplateColor bgColor, Size size, Text.Alignment alignment, String text) {
            super(y, color, bgColor, size, alignment, text);
        }

        @Override
        public Rectangle getBounds() {
            int x = Text.Alignment.calcAlignment(this);

            return new Rectangle(x, getY(), (getText().length() * getSize().getWidth()), getSize().getHeight());
        }
    }

    public boolean isArg() {
        return text.equals(TEXT_FROM_ARGUMENT);
    }

    @Override
    public String toString() {
        return "Text{" +
               '\'' + text.replaceAll("\\n", " ") + '\'' +
                '}';
    }

    public Integer getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public TemplateColor getColor() {
        return color;
    }

    public void setColor(TemplateColor color) {
        this.color = color;
    }

    public TemplateColor getBgColor() {
        return bgColor;
    }

    public void setBgColor(TemplateColor bgColor) {
        this.bgColor = bgColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.font = new Font("lucida console", size.getValue());
        this.size = size;
    }

    public Text.Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Text.Alignment alignment) {
        this.alignment = alignment;
    }
}
