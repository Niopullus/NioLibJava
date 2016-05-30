package com.niopullus.NioLib.scene;

import java.awt.*;
import java.io.Serializable;

/**
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
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void draw(final int x, final int y, final int z) {

    }

}
