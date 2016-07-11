package com.niopullus.NioLib.draw;

import java.awt.*;

/**Stores information about how to draw a shape
 * Created by Owen on 5/29/2016.
 */
public class ShapeElement extends DrawElement {

    private Color color;

    public ShapeElement(final ShapeElementPack pack) {
        super(pack.x, pack.y, pack.x + pack.width, pack.y + pack.height, pack.z, pack.angle, pack.opacity);
        color = pack.color;
    }

    public void display(final Graphics2D g, final DrawPosition drawPosition) {
        g.setColor(color);
        g.fillRect(drawPosition.getDx1(), drawPosition.getDy1(), drawPosition.getWidth(), drawPosition.getHeight());
    }

    public static class ShapeElementPack {

        public Color color;
        public int x;
        public int y;
        public int width;
        public int height;
        public int z;
        public double angle;
        public float opacity;

    }

}
