package com.niopullus.NioLib.draw;


import com.niopullus.NioLib.Main;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**Stores information about how to draw a specific item
 * Created by Owen on 3/29/2016.
 */
public class DrawElement {

    private int dx1;
    private int dy1;
    private int dx2;
    private int dy2;
    private int z;
    private double angle;
    private ParcelElement superElement;
    private float opacity;

    public DrawElement(final int x1, final int y1, final int x2, final int y2, final int _z, final double theta, final float _opacity) {
        dx1 = x1;
        dy1 = y1;
        dx2 = x2;
        dy2 = y2;
        z = _z;
        angle = theta;
        opacity = _opacity;
    }

    public int getDx1() {
        return dx1;
    }

    public int getDy1() {
        return dy1;
    }

    public int getDx2() {
        return dx2;
    }

    public int getDy2() {
        return dy2;
    }

    public int getZ() {
        return z;
    }

    public int getWidth() {
        return dx2 - dx1;
    }

    public int getHeight() {
        return dy2 - dy1;
    }

    public double getAngle() {
        return angle;
    }

    public float getOpacity() {
        return opacity;
    }

    public int getTDx1() {
        if (superElement == null) {
            return getDx1();
        } else {
            return (int) (superElement.getTDx1() + getDx1() * superElement.getXSF());
        }
    }

    public int getTDy1() {
        if (superElement == null) {
            return getDy1();
        } else {
            return (int) (superElement.getTDy1() + getDy1() * superElement.getYSF());
        }
    }

    public int getTDx2() {
        if (superElement == null) {
            return getDx2();
        } else {
            return (int) (superElement.getTDx1() + getDx2() * superElement.getXSF());
        }
    }

    public int getTDy2() {
        if (superElement == null) {
            return getDy2();
        } else {
            return (int) (superElement.getTDy1() + getDy2() * superElement.getYSF());
        }
    }

    public int getTZ() {
        if (superElement == null) {
            return getZ();
        } else {
            return getZ() + superElement.getTZ();
        }
    }

    public double getTAngle() {
        if (superElement == null) {
            return getAngle();
        } else {
            return getAngle() + superElement.getTAngle();
        }
    }

    public float getTOpacity() {
        if (superElement == null) {
            return getOpacity();
        } else {
            return getOpacity() * superElement.getTOpacity();
        }
    }

    public DrawPosition getDrawPosition() {
        final DrawPosition result = new DrawPosition();
        result.setDx1(getTDx1());
        result.setDy1(Main.Height() - getTDy2());
        result.setDx2(getTDx2());
        result.setDy2(Main.Height() - getTDy1());
        result.setZ(getTZ());
        result.setAngle(getTAngle());
        return result;
    }

    public void setSuperElement(final ParcelElement element) {
        superElement = element;
    }

    public void setDx1(final int x) {
        dx1 = x;
    }

    public void setDy1(final int y) {
        dy1 = y;
    }

    public void setDx2(final int x) {
        dx2 = x;
    }

    public void setDy2(final int y) {
        dy2 = y;
    }

    public void setZ(final int _z) {
        z = _z;
    }

    public void setAngle(final double _angle) {
        angle = _angle;
    }

    public void setOpacity(final float _opacity) {
        opacity = _opacity;
    }

    public void setPosition(final int dx1, final int dy1, final int dx2, final int dy2, final int z, final double angle, final float opacity) {
        setDx1(dx1);
        setDy1(dy1);
        setDx2(dx2);
        setDy2(dy2);
        setZ(z);
        setAngle(angle);
        setOpacity(opacity);
    }

    /**
     * Changes the angle of the graphics object such that it respects the
     * angle of the DrawElement object
     * @param g is the Graphics object
     * @param angle is the angle at which the DrawElement should be drawn
     * @param opacity is the opacity for which the DrawElement should be drawn
     */
    public void adjustGraphics(final Graphics2D g, final DrawPosition position, final double angle, final float opacity) {
        if (!(this instanceof ParcelElement)) {
            if (angle != 0) {
                final int transX = position.getDx1() + position.getWidth() / 2;
                final int transY = position.getDy1() + position.getHeight() / 2;
                g.translate(transX, transY);
                g.rotate(angle);
                g.translate(-transX, -transY);
            }
            if (opacity != 1) {
                final AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
                g.setComposite(ac);
            }
        }
    }

    /**
     * Displays this to the screen
     * @param g is the Graphics object
     */
    public final void draw(final Graphics2D g) {
        final DrawPosition drawPosition = getDrawPosition();
        if (drawPosition.isVisible()) {
            final AffineTransform old = g.getTransform();
            final Composite oldComp = g.getComposite();
            adjustGraphics(g, drawPosition, getTAngle(), getTOpacity());
            display(g, drawPosition);
            g.setTransform(old);
            g.setComposite(oldComp);
        }
    }

    /**
     * Overridden to the specific needs of the various types of
     * DrawElement objects
     * @param g is the Graphics object
     * @param drawPosition is the position at which this should be drawn
     */
    public void display(final Graphics2D g, final DrawPosition drawPosition) {
        //To be overridden
    }

}
