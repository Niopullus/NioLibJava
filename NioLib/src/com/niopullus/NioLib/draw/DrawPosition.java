package com.niopullus.NioLib.draw;

import com.niopullus.NioLib.Main;

/**
 * Created by Owen on 6/25/2016.
 */
public class DrawPosition {

    private int dx1;
    private int dy1;
    private int dx2;
    private int dy2;
    private int z;
    private double angle;

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

    public double getAngle() {
        return angle;
    }

    public int getWidth() {
        return getDx2() - getDx1();
    }

    public int getHeight() {
        return getDy2() - getDy1();
    }

    public void setDx1(int dx1) {
        this.dx1 = dx1;
    }

    public void setDy1(int dy1) {
        this.dy1 = dy1;
    }

    public void setDx2(int dx2) {
        this.dx2 = dx2;
    }

    public void setDy2(int dy2) {
        this.dy2 = dy2;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public String toString() {
        return "(" + dx1 + "," + dy1 + ") - (" + dx2 + "," + dy2 + ") - " + z + ", " + angle;
    }

    public boolean isVisible() {
        final boolean cond1 = dx1 <= Main.Width();
        final boolean cond2 = dy1 <= Main.Height();
        final boolean cond3 = dx2 >= 0;
        final boolean cond4 = dy2 >= 0;
        return cond1 && cond2 && cond3 && cond4;
    }

}
