package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Sketch;

import java.awt.image.BufferedImage;

/**Image Node that operates as a Trigger
 * Created by Owen on 4/6/2016.
 */
public class SketchTrigger extends SketchNode implements Trigger {

    private int xRad;
    private int yRad;

    public SketchTrigger(final String name, final Sketch sketch, final int xRad, final int yRad) {
        super(name, sketch);
        setup(xRad, yRad);
    }

    public SketchTrigger() {
        this("unnamed node", null, 0, 0);
    }

    public void init(final Node node) {
        super.init(node);
        if (node instanceof SketchTrigger) {
            final SketchTrigger sketchTrigger = (SketchTrigger) node;
            setup(sketchTrigger.xRad, sketchTrigger.yRad);
        }
    }

    public void setup(final int _xRad, final int _yRad) {
        xRad = _xRad;
        yRad = _yRad;
    }

    public void trigger(final CollideData data) {
        //To be overridden
    }

    public int getCx() {
        return getX() + getWidth() / 2 - xRad;
    }

    public int getCy() {
        return getY() + getHeight() / 2 - yRad;
    }

    public int getCwidth() {
        return getWidth() / 2 + xRad;
    }

    public int getCheight() {
        return getHeight() / 2 + yRad;
    }

    public int getXRad() {
        return xRad;
    }

    public int getYRad() {
        return yRad;
    }

}
