package com.niopullus.NioLib.draw;

import com.niopullus.NioLib.Main;

/**Denotes the position at which a DrawElement should be drawn
 * Created by Owen on 6/25/2016.
 */
public class DrawPosition {

    private int dx1;
    private int dy1;
    private int dx2;
    private int dy2;

    public DrawPosition() {
        dx1 = 0;
        dy1 = 0;
        dx2 = 0;
        dy2 = 0;
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

    public int getWidth() {
        return getDx2() - getDx1();
    }

    public int getHeight() {
        return getDy2() - getDy1();
    }

    public void setDx1(final int _dx1) {
        dx1 = _dx1;
    }

    public void setDy1(final int _dy1) {
        dy1 = _dy1;
    }

    public void setDx2(final int _dx2) {
        dx2 = _dx2;
    }

    public void setDy2(final int _dy2) {
        dy2 = _dy2;
    }

    public String toString() {
        return "(" + dx1 + "," + dy1 + ") - (" + dx2 + "," + dy2 + ")";
    }

    public boolean isVisible() {
        final boolean cond1 = dx1 <= Main.Width();
        final boolean cond2 = dy1 <= Main.Height();
        final boolean cond3 = dx2 >= 0;
        final boolean cond4 = dy2 >= 0;
        return cond1 && cond2 && cond3 && cond4;
    }

}
