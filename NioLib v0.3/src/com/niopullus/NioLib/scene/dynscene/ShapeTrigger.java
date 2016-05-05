package com.niopullus.NioLib.scene.dynscene;

import java.awt.*;

/**
 * Created by Owen on 4/6/2016.
 */
public class ShapeTrigger extends ShapeNode implements Trigger {

    private int xRad;
    private int yRad;

    public ShapeTrigger(String name, int width, int height, Color color, int xRad, int yRad) {
        super(name, width, height, color);
    }

    public void trigger() {

    }

    public int getCx() {
        return this.getX() + this.getWidth() - this.xRad;
    }

    public int getCy() {
        return this.getY() + this.getHeight() - this.yRad;
    }

    public int getCwidth() {
        return -this.getWidth() + this.xRad * 2;
    }

    public int getCheight() {
        return -this.getHeight() + this.yRad * 2;
    }

}
