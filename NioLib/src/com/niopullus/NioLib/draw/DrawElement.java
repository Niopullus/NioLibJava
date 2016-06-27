package com.niopullus.NioLib.draw;


import com.niopullus.NioLib.Main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

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

    public DrawElement(final int dx1, final int dy1, final int dx2, final int dy2, final int z, final double angle) {
        this.dx1 = dx1;
        this.dy1 = dy1;
        this.dx2 = dx2;
        this.dy2 = dy2;
        this.z = z;
        this.angle = angle;
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

    public int getTDx1() {
        if (superElement == null) {
            return getDx1();
        } else {
            return (int) (superElement.getTDx1() + getDx1() * superElement.getxSF());
        }
    }

    public int getTDy1() {
        if (superElement == null) {
            return getDy1();
        } else {
            return (int) (superElement.getTDy1() + getDy1() * superElement.getySF());
        }
    }

    public int getTDx2() {
        if (superElement == null) {
            return getDx2();
        } else {
            return (int) (superElement.getTDx1() + getDx2() * superElement.getxSF());
        }
    }

    public int getTDy2() {
        if (superElement == null) {
            return getDy2();
        } else {
            return (int) (superElement.getTDy1() + getDy2() * superElement.getySF());
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

    public void setZ(final int z) {
        this.z = z;
    }

    public void setAngle(final double angle) {
        this.angle = angle;
    }

    public void setPosition(final int dx1, final int dy1, final int dx2, final int dy2, final int z, final double angle) {
        setDx1(dx1);
        setDy1(dy1);
        setDx2(dx2);
        setDy2(dy2);
        setZ(z);
        setAngle(angle);
    }

    public void adjustGraphics(final Graphics2D g, final double angle) {
        final int transX = dx1 + getWidth() / 2;
        final int transY = dy1 + getHeight() / 2;
        g.translate(transX, transY);
        g.rotate(angle);
        g.translate(-transX, -transY);
    }

    public final void draw(final Graphics2D g) {
        final DrawPosition drawPosition = getDrawPosition();
        if (drawPosition.isVisible()) {
            final AffineTransform old = g.getTransform();
            adjustGraphics(g, angle);
            display(g, drawPosition);
            g.setTransform(old);
        }
    }

    public void display(final Graphics2D g, final DrawPosition drawPosition) {
        //To be overridden
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

}
