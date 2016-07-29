package com.niopullus.NioLib.scene.guiscene;

/**
 * Created by Owen on 7/17/2016.
 */
public class Size {

    private int width;
    private int height;

    public Size(final int _width, final int _height) {
        width = _width;
        height = _height;
    }

    public Size() {
        this(0, 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(final int _width) {
        width = _width;
    }

    public void setHeight(final int _height) {
        height = _height;
    }

}
