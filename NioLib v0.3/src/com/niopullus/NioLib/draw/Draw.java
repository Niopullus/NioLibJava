package com.niopullus.NioLib.draw;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;


/**Used to relay DrawElements to the DrawManager
 * Created by Owen on 5/29/2016.
 */
public class Draw {

    private static DrawManager drawManager = new DrawManager();
    public static final OriginDelegate o = new OriginDelegate();
    public static final CenterDelegate c = new CenterDelegate();

    public static void display(final Graphics2D g) {
        drawManager.display(g);
    }

    private static class DrawDelegate {

        public void rect(final Color color, final int x, final int y, final int width, final int height, final int z) {
            rect(color, x, y, width, height, z, 0);
        }

        public void rect(final Color color, final int x, final int y, final int width, final int height, final int z, final double angle) {
            final Point position = getPos(x, y, width, height);
            final ShapeElement.ShapeElementPack pack = new ShapeElement.ShapeElementPack();
            final ShapeElement element;
            pack.color = color;
            pack.x = position.x;
            pack.y = position.y;
            pack.width = width;
            pack.height = height;
            pack.z = z;
            pack.angle = angle;
            element = new ShapeElement(pack);
            drawManager.add(element);
        }

        public void image(final BufferedImage image, final int x, final int y, final int width, final int height, final int z) {
            image(image, x, y, width, height, z, 0);
        }

        public void image(final BufferedImage image, final int x, final int y, final int width, final int height, final int z, final double angle) {
            image(image, x, y, x + width, y + height, 0, 0, image.getWidth(), image.getHeight(), z, angle);
        }

        public void image(final BufferedImage image, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1, final int sy1, final int sx2, final int sy2, final int z, final double angle) {
            final int width = dx2 - dx1;
            final int height = dy2 - dy1;
            final Point position = getPos(dx1, dy1, width, height);
            final ImageElement.ImageElementPack pack = new ImageElement.ImageElementPack();
            final ImageElement element;
            pack.image = image;
            pack.dx1 = position.x;
            pack.dy1 = position.y;
            pack.dx2 = position.x + width;
            pack.dy2 = position.y + height;
            pack.z = z;
            pack.angle = angle;
            element = new ImageElement(pack);
            drawManager.add(element);
        }

        public void text(final String line, final String fontName, final Color color, final int fontSize, final int x, final int y, final int z) {
            text(line, fontName, color, fontSize, x, y,  z, 0);
        }

        public void text(final String line, final String fontName, final Color color, final int fontSize, final int x, final int y, final int z, final double angle) {
            text(line, new Font(fontName, Font.BOLD, fontSize), color, x, y, z, angle);
        }

        public void text(final String line, final Font font, final Color color, final int x, final int y, final int z, final double angle) {
            final FontMetrics fontMetrics = new FontMetrics(font) {};
            final int width = fontMetrics.stringWidth(line);
            final int height = fontMetrics.getHeight();
            final Point position = getPos(x, y, width, height);
            final TextElement.TextElementPack pack = new TextElement.TextElementPack();
            final TextElement element;
            pack.line = line;
            pack.font = font;
            pack.color = color;
            pack.x = x;
            pack.y = y;
            pack.z = z;
            pack.angle = angle;
            element = new TextElement(pack);
            drawManager.add(element);
        }

        public Point getPos(final int x, final int y, final int width, final int height) {
            return null; //To be overridden
        }

    }

    public static class OriginDelegate extends DrawDelegate {

        public Point getPos(final int x, final int y, final int width, final int height) {
            final int nY = Main.Height() - y - width;
            return new Point(x, nY);
        }

    }

    public static class CenterDelegate extends DrawDelegate {

        public Point getPos(final int x, final int y, final int width, final int height) {
            final int nX = Main.Width() / 2 - width / 2 + x;
            final int nY = Main.Height() * 3 / 2 - height * 3 / 2 - y;
            return new Point(nX, nY);
        }

    }

    public static enum DrawMode {

        ORIGIN,
        CENTERED

    }

}
