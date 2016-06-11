package com.niopullus.NioLib.scene;


import com.niopullus.NioLib.draw.Draw;

import java.awt.*;

/**Used to display a rectangular visual in multiple contexts
 * Created by Owen on 3/19/2016.
 */
public class Background {

    private int width;
    private int height;

    public Background() {
        this(0, 0);
    }

    public Background(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return null; //To be overridden
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void setColor(final Color color) {
        //To be overridden
    }

    public void draw(final int x, final int y, final int z, final Draw.DrawMode mode) {
        //To be overridden
    }

}
