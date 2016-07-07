package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Sketch;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;

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

    public void parcelDraw(final Canvas canvas) {
        canvas.o.rect(color, 0, 0, getWidth(), getHeight(), 0);
        super.parcelDraw(canvas);
    }

}
