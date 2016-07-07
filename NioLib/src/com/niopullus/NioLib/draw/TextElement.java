package com.niopullus.NioLib.draw;

import java.awt.*;

/**Stores information about how to draw a line of text
 * Created by Owen on 5/29/2016.
 */
public class TextElement extends DrawElement {

    private String line;
    private Font font;
    private Color color;

    public TextElement(final TextElementPack pack) {
        super(pack.x, pack.y, pack.x + pack.width, pack.y + pack.height, pack.z, pack.angle);
        line = pack.line;
        font = pack.font;
        color = pack.color;
    }

    public void display(final Graphics2D g, final DrawPosition drawPosition) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(line, drawPosition.getDx1(), drawPosition.getDy1() + getHeight());
    }

    public static class TextElementPack {

        public String line;
        public Font font;
        public Color color;
        public int x;
        public int y;
        public int z;
        public int width;
        public int height;
        public double angle;

    }

}
