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
    private int originMode;
    private double angle;
    public static final int ORIGIN_BOTTOMLEFT = 1;
    public static final int ORIGIN_CENTER = 2;

    public DrawElement(final int dx1, final int dy1, final int dx2, final int dy2, final int z, final double angle) {
        this.dx1 = dx1;
        this.dy1 = Main.Height() - dy2;
        this.dx2 = dx2;
        this.dy2 = dy1;
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

    public void setDx1(final int n) {

    }

    public void adjustGraphics(final Graphics2D g, final double angle) {
        final int transX = dx1 + getWidth() / 2;
        final int transY = dy1 + getHeight() / 2;
        g.translate(transX, transY);
        g.rotate(angle);
        g.translate(-transX, -transY);
    }

    public final void draw(final Graphics2D g) {
        final AffineTransform old = g.getTransform();
        adjustGraphics(g, angle);
        display(g);
        g.setTransform(old);
    }

    public void display(final Graphics g) {
        //To be overridden
    }

}
