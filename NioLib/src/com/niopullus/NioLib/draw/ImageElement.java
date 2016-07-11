package com.niopullus.NioLib.draw;

import java.awt.*;
import java.awt.image.BufferedImage;

/**Stores information how to draw an Image
 * Created by Owen on 5/29/2016.
 */
public class ImageElement extends DrawElement {

    private BufferedImage image;
    private int sx1;
    private int sy1;
    private int sx2;
    private int sy2;

    public ImageElement(final ImageElementPack pack) {
        super(pack.dx1, pack.dy1, pack.dx2, pack.dy2, pack.z, pack.angle, pack.opacity);
        image = pack.image;
        sx1 = pack.sx1;
        sy1 = pack.sy1;
        sx2 = pack.sx2;
        sy2 = pack.sy2;
    }

    public void display(final Graphics2D g, final DrawPosition drawPosition) {
        g.drawImage(image, drawPosition.getDx1(), drawPosition.getDy1(), drawPosition.getDx2(), drawPosition.getDy2(), sx1, sy1, sx2, sy2, null);
    }

    public static class ImageElementPack {

        public BufferedImage image;
        public int dx1;
        public int dy1;
        public int dx2;
        public int dy2;
        public int sx1;
        public int sy1;
        public int sx2;
        public int sy2;
        public int z;
        public double angle;
        public float opacity;

    }

}
