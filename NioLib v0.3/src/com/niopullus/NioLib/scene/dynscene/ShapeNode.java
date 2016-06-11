package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.draw.Draw;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**A node that is drawn in the form of a colorable rectangle
 * Created by Owen on 3/19/2016.
 */
public class ShapeNode extends Node {

    private Color color;

    public ShapeNode(final String name, final int width, final int height, final Color color) {
        super(name, width, height);
        this.color = color;
        osetWidth(width);
        osetHeight(height);
    }

    public void draw() {
        Draw.o.rect(color, getTX(), getTY(), getWidth(), getHeight(), getZ(), getAngle());
    }

}
