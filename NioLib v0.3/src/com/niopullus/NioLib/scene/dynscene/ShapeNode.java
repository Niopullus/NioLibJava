package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.Draw;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * Created by Owen on 3/19/2016.
 */
public class ShapeNode extends Node {

    private Color color;

    public ShapeNode(String name, int width, int height, Color color) {
        super(name, width, height);
        this.setOWidth(width);
        this.setOHeight(height);
        this.color = color;
    }

    public void draw() {
        Draw.rect(this.getTX(), Main.Height() - this.getTY() - this.getHeight(), this.getWidth(), this.getHeight(), this.getZ(), this.getAngle(), this.color);
    }

}
