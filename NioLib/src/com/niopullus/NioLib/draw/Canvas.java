package com.niopullus.NioLib.draw;

import com.niopullus.NioLib.Sketch;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;


/**Used to invoke DrawElements
 * Created by Owen on 5/29/2016.
 */
public class Canvas {

    private List<DrawElement> elements;
    private boolean init;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int superWidth;
    private int superHeight;
    private ParcelElement model;
    public OriginDelegate o;
    private CenterDelegate c;
    public MiddleDelegate m;
    private TabDelegate t;

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

    public void setModel(final ParcelElement element) {
        model = element;
    }

    public void addElement(final DrawElement element) {
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
        element.setSuperElement(model);
        elements.add(element);
    }

    /**
     * Invokes the CenterDelegate via parameters
     * @param sw is the expected width of this Canvas
     * @param sh is the expected height of this Canvas
     * @return a CenterDelegate to be used for generic parcelDraw calls
     */
    public CenterDelegate c(final int sw, final int sh) {
        superWidth = sw;
        superHeight = sh;
        return c;
    }

    /**
     * Invokes the TabDelegate via parameters
     * @param sw is the expected width of this Canvas
     * @param sh is the expected height of this Canvas
     * @return a CenterDelegate to be used for generic parcelDraw calls
     */
    public TabDelegate t(final int sw, final int sh) {
        superWidth = sw;
        superHeight = sh;
        return t;
    }

    /**
     * Gets all DrawElements to be found within this object and also
     * within derivative ParcelElements
     * @return is the list of retrieved elements
     */
    public List<DrawElement> retrieveElements() {
        final List<DrawElement> result = new ArrayList<>();
        for (DrawElement element : elements) {
            if (element instanceof ParcelElement) {
                final ParcelElement parcelElement = (ParcelElement) element;
                result.addAll(parcelElement.retrieveElements());
            } else {
                result.add(element);
            }
        }
        return result;
    }

    /**
     * Used to provide variety for the specific way that a parcel is to be
     * drawn
     */
    public class DrawDelegate {

        public List<DrawElement> getElements() {
            return elements;
        }

        public void rect(final Color color, final int x, final int y, final int width, final int height, final int z) {
            rect(color, x, y, width, height, z, 0, 1);
        }

        public void rect(final Color color, final int x, final int y, final int width, final int height, final int z, final double angle,
                         final float opacity) {
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
            pack.opacity = opacity;
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
            image(image, x, y, x + width, y + height, 0, 0, image.getWidth(), image.getHeight(), z, angle, 1);
        }

        public void image(final BufferedImage image, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1, final int sy1,
                          final int sx2, final int sy2, final int z, final double angle, final float opacity) {
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
            pack.sx1 = sx1;
            pack.sy1 = sy1;
            pack.sx2 = sx2;
            pack.sy2 = sy2;
            pack.z = z;
            pack.angle = angle;
            pack.opacity = opacity;
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

        public void animation(final Animation animation, final int x, final int y, final int width, final int height, final int z,
                              final double angle) {
            animation(animation, x, y, x + width, y + height, 0, 0, animation.getWidth(), animation.getHeight(), z, angle, 1);
        }

        public void animation(final Animation animation, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1,
                              final int sy1, final int sx2, final int sy2, final int z, final double angle, final float opacity) {
            final int width = dx2 - dx1;
            final int height = dy2 - dy1;
            final Point position = getPos(dx1, dy1, width, height);
            final ImageElement.ImageElementPack pack = new ImageElement.ImageElementPack();
            final ImageElement element;
            pack.image = animation.getImage();
            pack.dx1 = position.x;
            pack.dy1 = position.y;
            pack.dx2 = position.x + width;
            pack.dy2 = position.y + height;
            pack.sx1 = sx1;
            pack.sy1 = sy1;
            pack.sx2 = sx2;
            pack.sy2 = sy2;
            pack.z = z;
            pack.angle = angle;
            pack.opacity = opacity;
            element = new ImageElement(pack);
            addElement(element);
        }

        public void sketch(final Sketch sketch, final int x, final int y, final int z) {
            sketch(sketch, x, y, 1, z);
        }

        public void sketch(final Sketch sketch, final int x, final int y, final double scaleFactor, final int z) {
            sketch(sketch, x, y, (int) (sketch.getWidth() * scaleFactor), (int) (sketch.getHeight() * scaleFactor), z);
        }

        public void sketch(final Sketch sketch, final int x, final int y, final int width, final int height, final int z) {
            sketch(sketch, x, y, width, height, z, 0);
        }

        public void sketch(final Sketch sketch, final int x, final int y, final int width, final int height, final int z,
                              final double angle) {
            sketch(sketch, x, y, x + width, y + height, 0, 0, sketch.getPictureWidth(), sketch.getPictureHeight(), z, angle, 1);
        }

        public void sketch(final Sketch sketch, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1,
                              final int sy1, final int sx2, final int sy2, final int z, final double angle, final float opacity) {
            final int width = dx2 - dx1;
            final int height = dy2 - dy1;
            final Point position = getPos(dx1, dy1, width, height);
            final ImageElement.ImageElementPack pack = new ImageElement.ImageElementPack();
            final ImageElement element;
            pack.image = sketch.getImage();
            pack.dx1 = position.x;
            pack.dy1 = position.y;
            pack.dx2 = position.x + width;
            pack.dy2 = position.y + height;
            pack.sx1 = sx1;
            pack.sy1 = sy1;
            pack.sx2 = sx2;
            pack.sy2 = sy2;
            pack.z = z;
            pack.angle = angle;
            pack.opacity = opacity;
            element = new ImageElement(pack);
            addElement(element);
        }

        public void text(final String line, final Color color, final String fontName, final int fontSize, final int x, final int y, final int z) {
            text(line, color, fontName, fontSize, x, y,  z, 0);
        }

        public void text(final String line, final Color color, final String fontName, final int fontSize, final int x, final int y, final int z,
                         final double angle) {
            text(line, color, new Font(fontName, Font.BOLD, fontSize), x, y, z, angle, 1);
        }

        public void text(final String line, final Color color, final Font font, final int x, final int y, final int z, final double angle,
                         final float opacity) {
            final FontMetrics fontMetrics = StringSize.getFontMetrics(font);
            final int width = fontMetrics.stringWidth(line);
            final int height = fontMetrics.getAscent() - fontMetrics.getDescent();
            final Point position = getPos(x, y, width, height);
            final TextElement.TextElementPack pack = new TextElement.TextElementPack();
            final TextElement element;
            pack.line = line;
            pack.font = font;
            pack.color = color;
            pack.x = position.x;
            pack.y = position.y;
            pack.width = width;
            pack.height = height;
            pack.z = z;
            pack.angle = angle;
            pack.opacity = opacity;
            element = new TextElement(pack);
            addElement(element);
        }

        public void parcel(final Parcel parcel, final int x, final int y, final int z, final double angle) {
            parcel(parcel, x, y, 0, 0, z, angle, 1, true);
        }

        public void parcel(final Parcel parcel, final int x, final int y, final int width, final int height, final int z, final double angle,
                           final float opacity) {
            parcel(parcel, x, y, width, height, z, angle, opacity, false);
        }

        private void parcel(final Parcel parcel, final int x, final int y, int width, int height, final int z, final double angle,
                            final float opacity, final boolean infer) {
            final ParcelElement.ParcelElementPack pack = new ParcelElement.ParcelElementPack();
            final ParcelElement element;
            final Canvas canvas;
            final Point position;
            element = new ParcelElement();
            canvas = new Canvas();
            canvas.model = element;
            parcel.parcelDraw(canvas);
            if (infer) {
                width = canvas.getWidth();
                height = canvas.getHeight();
            }
            position = getPos(x, y, width, height);
            pack.x = position.x;
            pack.y = position.y;
            pack.z = z;
            pack.angle = angle;
            pack.opacity = opacity;
            pack.elements = canvas.elements;
            pack.width = width;
            pack.height = height;
            pack.xSF = (double) width / canvas.getWidth();
            pack.ySF = (double) height / canvas.getHeight();
            element.define(pack);
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
            final int nX = superWidth / 2 - width / 2 + x;
            final int nY = superHeight / 2 - height / 2 + y;
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
            final int nX = superWidth / 2 + x;
            final int nY = superHeight / 2 + y;
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
