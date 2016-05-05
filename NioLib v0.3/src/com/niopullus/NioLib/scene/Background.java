package com.niopullus.NioLib.scene;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Owen on 3/19/2016.
 */
public class Background implements Serializable {

    private int x;
    private int y;
    private int z;
    private int width;
    private int height;

    public Background() {
        this(0, 0);
    }

    public Background(int width, int height) {
        this(0, 0, width, height);
    }

    public Background(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void draw() {

    }

    public void draw(int x, int y) {

    }

}
