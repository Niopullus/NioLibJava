package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Draw;

import java.awt.*;

/**
 * Created by Owen on 3/19/2016.
 */
public class ColorBackground extends Background {

    private Color color;

    public ColorBackground() {
        super();
    }

    public ColorBackground(int width, int height, Color color) {
        this(0, 0, width, height, color);
    }

    public ColorBackground(int x, int y, int width, int height, Color color) {
        super(x, y, width, height);
        this.color = color;
    }

    public void draw() {
        this.draw(this.getX(), this.getY());
    }

    public void draw(int x, int y) {
        Draw.rect(x, y, this.getWidth(), this.getHeight(), this.getZ(), this.color);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

}
