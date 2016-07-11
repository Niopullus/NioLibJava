package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;

/**A node that is drawn in the form of a colorable rectangle
 * Created by Owen on 3/19/2016.
 */
public class ShapeNode extends Node {

    private Color color;

    public ShapeNode(final String name, final Color color, final int width, final int height) {
        super(name, width, height);
        setup(color, width, height);
    }

    public ShapeNode() {
        this("unnamed", null, 0, 0);
    }

    public void init(final Node node) {
        super.init(node);
        if (node instanceof ShapeNode) {
            final ShapeNode shapeNode = (ShapeNode) node;
            setup(shapeNode.color, shapeNode.ogetWidth(), shapeNode.ogetHeight());
        }
    }

    public void setup(final Color _color, final int width, final int height) {
        color = _color;
        osetWidth(width);
        osetHeight(height);
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.rect(color, 0, 0, getWidth(), getHeight(), 0);
    }

}
