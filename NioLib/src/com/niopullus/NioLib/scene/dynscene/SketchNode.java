package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Sketch;
import com.niopullus.NioLib.draw.Canvas;

/**Node that is displayed as a Sketch
 * Created by Owen on 3/19/2016.
 */
public class SketchNode extends Node {

    private Sketch sketch;

    public SketchNode(final String name, final Sketch _sketch, final int width, final int height) {
        super(name, width, height);
        sketch = _sketch;
        csetXScale((double) width / ogetWidth());
        csetYScale((double) height / ogetHeight());
        if (sketch != null) {
            osetWidth(sketch.getWidth());
            osetHeight(sketch.getHeight());
        }
    }

    public SketchNode(final String name, final Sketch sketch) {
        this(name, sketch, sketch.getWidth(), sketch.getHeight());
    }

    public SketchNode() {
        this("unnamed node", null, 0, 0);
    }

    public void integrate(final Node node) {
        super.integrate(node);
        if (node instanceof SketchNode) {
            final SketchNode sketchNode = (SketchNode) node;
            sketch = sketchNode.sketch;
            csetXScale((double) node.getWidth() / ogetWidth());
            csetYScale((double) node.getHeight() / ogetHeight());
            if (sketch != null) {
                osetWidth(sketch.getWidth());
                osetHeight(sketch.getHeight());
            }
        }
    }

    public Sketch getSketch() {
        return sketch;
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.sketch(sketch, 0, 0, getWidth(), getHeight(), 0);
    }

}
