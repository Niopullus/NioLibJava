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

    public ShapeNode(final String name, final int width, final int height, final Color color) {
        super(name, width, height);
        osetWidth(width);
        osetHeight(height);
        this.color = color;
    }

    public void draw() {
        Draw.rect(getTX(), getTY(), getWidth(), getHeight(), getZ(), getAngle(), color);
    }

}
