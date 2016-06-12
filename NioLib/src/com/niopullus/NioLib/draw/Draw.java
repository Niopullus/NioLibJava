package com.niopullus.NioLib.draw;

import com.niopullus.NioLib.Animation;
import com.niopullus.NioLib.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;


/**Used to relay DrawElements to the DrawManager
 * Created by Owen on 5/29/2016.
 */
public class Draw {

    private static DrawManager drawManager = new DrawManager();
    public static final OriginDelegate o = new OriginDelegate();
    public static final CenterDelegate c = new CenterDelegate();
    public static final MiddleDelegate m = new MiddleDelegate();
    public static final TabDelegate t = new TabDelegate();

    public static DrawDelegate mode(final DrawMode mode) {
        if (mode == DrawMode.ORIGIN) {
            return o;
        } else if (mode == DrawMode.CENTERED) {
            return c;
        }
        return null;
    }

    public static void display(final Graphics2D g) {
        compileElements();
        drawManager.display(g);
    }

    public static void compileElements() {
        List<DrawDelegate> delegates = Arrays.asList(new DrawDelegate[]{o, c, m, t});
        for (DrawDelegate delegate : delegates) {
            drawManager.add(delegate.getElements());
        }
    }

    public static class DrawDelegate {

        private List<DrawElement> elements;

        public DrawDelegate() {
            elements = new ArrayList<>();
        }

        public List<DrawElement> getElements() {
            return elements;
        }

        public void addElement(final DrawElement element) {
            elements.add(element);
        }

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
            addElement(element);
        }

        public void image(final BufferedImage image, final int x, final int y, final int z) {
            image(image, x, y, 1, z);
        }

        public void image(final BufferedImage image, final int x, final int y, final double scaleFactor, final int z) {
            image(image, x, y, (int) (image.getWidth() * scaleFactor), (int) (image.getHeight() * scaleFactor), z);
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
            addElement(element);
        }

        public void animation(final Animation animation, final int x, final int y, final int z) {
            animation(animation, x, y, 1, z);
        }

        public void animation(final Animation animation, final int x, final int y, final double scaleFactor, final int z) {
            animation(animation, x, y, (int) (animation.getWidth() * scaleFactor), (int) (animation.getHeight() * scaleFactor), z);
        }

        public void animation(final Animation animation, final int x, final int y, final int width, final int height, final int z) {
            animation(animation, x, y, width, height, z, 0);
        }

        public void animation(final Animation animation, final int x, final int y, final int width, final int height, final int z, final double angle) {
            animation(animation, x, y, x + width, y + height, 0, 0, animation.getWidth(), animation.getHeight(), z, angle);
        }

        public void animation(final Animation animation, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1, final int sy1, final int sx2, final int sy2, final int z, final double angle) {
            final int width = dx2 - dx1;
            final int height = dy2 - dy1;
            final Point position = getPos(dx1, dy1, width, height);
            final ImageElement.ImageElementPack pack = new ImageElement.ImageElementPack();
            final ImageElement element;
            pack.image = animation.runFrame();
            pack.dx1 = position.x;
            pack.dy1 = position.y;
            pack.dx2 = position.x + width;
            pack.dy2 = position.y + height;
            pack.z = z;
            pack.angle = angle;
            element = new ImageElement(pack);
            addElement(element);
        }

        public void text(final String line, final Color color, final String fontName, final int fontSize, final int x, final int y, final int z) {
            text(line, color, fontName, fontSize, x, y,  z, 0);
        }

        public void text(final String line, final Color color, final String fontName, final int fontSize, final int x, final int y, final int z, final double angle) {
            text(line, color, new Font(fontName, Font.BOLD, fontSize), x, y, z, angle);
        }

        public void text(final String line, final Color color, final Font font, final int x, final int y, final int z, final double angle) {
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
            addElement(element);
        }

        public void parcel(final Parcel parcel, final int x, final int y, final int z, final double angle) {
            final ParcelDelegate delegate = new ParcelDelegate();
            final ParcelElement.ParcelElementPack pack = new ParcelElement.ParcelElementPack();
            final ParcelElement element;
            final List<DrawElement> elements;
            parcel.integrate(delegate);
            element = new ParcelElement(pack);
            drawManager.add(element);
            elements = delegate.getElements();
            pack.elements = elements;
            pack.x = x;
            pack.y = y;
            pack.width = delegate.getWidth();
            pack.height = delegate.getHeight();
            pack.z = z;
            pack.angle = angle;
            addElement(element);
        }

        public Point getPos(final int x, final int y, final int width, final int height) {
            return null; //To be overridden
        }

    }

    public static class OriginDelegate extends DrawDelegate {

        public Point getPos(final int x, final int y, final int width, final int height) {
            return new Point(x, y);
        }

    }

    public static class CenterDelegate extends DrawDelegate {

        public Point getPos(final int x, final int y, final int width, final int height) {
            final int nX = Main.Width() / 2 - width / 2 + x;
            final int nY = Main.Height() / 2 - height / 2 + y;
            return new Point(nX, nY);
        }

    }

    public static class MiddleDelegate extends DrawDelegate {

        public Point getPos(final int x, final int y, final int width, final int height) {
            final int nX = x - width / 2;
            final int nY = y - height / 2;
            return new Point(nX, nY);
        }

    }

    public static class TabDelegate extends DrawDelegate {

        public Point getPos(final int x, final int y, final int width, final int height) {
            final int nX = Main.Width() / 2 + x;
            final int nY = Main.Height() / 2 + y;
            return new Point(nX, nY);
        }

    }

    public enum DrawMode {

        ORIGIN,
        CENTERED,
        MIDDLE,
        TAB

    }

}
