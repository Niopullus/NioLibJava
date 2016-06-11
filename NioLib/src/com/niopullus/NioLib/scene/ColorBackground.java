package com.niopullus.NioLib.scene;


import com.niopullus.NioLib.draw.Draw;

import java.awt.*;

/**Displays a rectangle of a particular color
 * Created by Owen on 3/19/2016.
 */
public class ColorBackground extends Background {

    private Color color;

    public ColorBackground() {
        super();
    }

    public ColorBackground(final Color color, final int width, final int height) {
        super(width, height);
        this.color = color;
    }

    public ColorBackground(final Color color) {
        this(color, 0, 0);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public void draw(final int x, final int y, final int z, final Draw.DrawMode mode) {
        Draw.mode(mode).rect(color, x, y, getWidth(), getHeight(), z);
    }

}
