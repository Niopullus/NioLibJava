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
        super(pack.x, pack.y, 0, 0, pack.z, pack.angle);
    }

    public void display(final Graphics g) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(line, getDx1(), getDy1());
    }

    public static class TextElementPack {

        public String line;
        public Font font;
        public Color color;
        public int x;
        public int y;
        public int z;
        public double angle;

    }

}
