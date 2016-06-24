package com.niopullus.NioLib.draw;

import com.niopullus.NioLib.Animation;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.guiscene.GUIScene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;


/**Used to relay DrawElements to the DrawManager
 * Created by Owen on 5/29/2016.
 */
public class Canvas {

    private List<DrawElement> elements;
    private boolean init;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    public OriginDelegate o;
    public CenterDelegate c;
    public MiddleDelegate m;
    public TabDelegate t;

    public Canvas() {
        elements = new ArrayList<>();
        init = false;
        minX = 0;
        minY = 0;
        maxX = 0;
        maxY = 0;
        o = new OriginDelegate();
        c = new CenterDelegate();
        m = new MiddleDelegate();
        t = new TabDelegate();
    }

    public List<DrawElement> getElements() {
        return elements;
    }

    public int getWidth() {
        return maxX - minX;
    }

    public int getHeight() {
        return maxY - minY;
    }

    public void addElement(final DrawElement element) {
        System.out.println("efeeeeeefv" + (element instanceof ShapeElement));
        if (!init) {
            minX = element.getDx1();
            maxX = element.getDx2();
            minY = element.getDy1();
            maxY = element.getDy2();
            init = true;
        } else {
            if (element.getDx1() < minX) {
                minX = element.getDx1();
            }
            if (element.getDx2() > maxX) {
                maxX = element.getDx2();
            }
            if (element.getDy1() < minY) {
                minY = element.getDy1();
            }
            if (element.getDy2() > maxY) {
                maxY = element.getDy2();
            }
        }
        elements.add(element);
    }

    public DrawDelegate mode(final DrawMode mode) {
        if (mode == DrawMode.ORIGIN) {
            return o;
        } else if (mode == DrawMode.CENTERED) {
            return c;
        } else if (mode == DrawMode.MIDDLE) {
            return m;
        } else if (mode == DrawMode.TAB) {
            return t;
        }
        return null;
    }

    public void display(final Graphics2D g) {
        System.out.println(1);
        final Comparator<DrawElement> comparator = (final DrawElement o1, final DrawElement o2) -> {
            final Integer z = o1.getZ();
            return z.compareTo(o2.getZ());
        };
        elements.sort(comparator);
        for (DrawElement element : elements) {
            element.draw(g);
            System.out.println("awwfwfe" + (element instanceof ShapeElement));
        }
    }

    public class DrawDelegate {

        public List<DrawElement> getElements() {
            return elements;
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
            final Canvas subCanvas = new Canvas();
            parcel.parcelDraw(subCanvas);
            canvas(subCanvas, x, y, subCanvas.getWidth(), subCanvas.getHeight(), z, angle);
        }

        public void parcel(final Parcel parcel, final int x, final int y, final int width, final int height, final int z, final double angle) {
            final Canvas subCanvas = new Canvas();
            parcel.parcelDraw(subCanvas);
            canvas(subCanvas, x, y, width, height, z, angle);
        }

        private void canvas(final Canvas canvas, final int x, final int y, final int width, final int height, final int z, final double angle) {
            final ParcelElement.ParcelElementPack pack = new ParcelElement.ParcelElementPack();
            final ParcelElement element;
            final List<DrawElement> elements = canvas.elements;
            pack.elements = elements;
            pack.x = x;
            pack.y = y;
            pack.width = width;
            pack.height = height;
            pack.z = z;
            pack.angle = angle;
            element = new ParcelElement(pack);
            //System.out.println("is it null?" + (element.getElements() == null));
            addElement(element);
        }

        public Point getPos(final int x, final int y, final int width, final int height) {
            return null; //To be overridden
        }

    }

    public class OriginDelegate extends DrawDelegate {

        public Point getPos(final int x, final int y, final int width, final int height) {
            return new Point(x, y);
        }

    }

    public class CenterDelegate extends DrawDelegate {

        public Point getPos(final int x, final int y, final int width, final int height) {
            final int nX = Main.Width() / 2 - width / 2 + x;
            final int nY = Main.Height() / 2 - height / 2 + y;
            return new Point(nX, nY);
        }

    }

    public class MiddleDelegate extends DrawDelegate {

        public Point getPos(final int x, final int y, final int width, final int height) {
            final int nX = x - width / 2;
            final int nY = y - height / 2;
            return new Point(nX, nY);
        }

    }

    public class TabDelegate extends DrawDelegate {

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
