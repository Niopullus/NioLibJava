package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Sketch;


/**Image Node that operates as a Trigger
 * Created by Owen on 4/6/2016.
 */
public class SketchTrigger extends SketchNode implements Trigger {

    private int xRad;
    private int yRad;

    public SketchTrigger(final String name, final Sketch sketch, final int _xRad, final int _yRad) {
        super(name, sketch);
        xRad = _xRad;
        yRad = _yRad;
    }

    public SketchTrigger() {
        this("unnamed node", null, 0, 0);
    }

    public void integrate(final Node node) {
        super.integrate(node);
        if (node instanceof SketchTrigger) {
            final SketchTrigger sketchTrigger = (SketchTrigger) node;
            xRad = sketchTrigger.xRad;
            yRad = sketchTrigger.yRad;
        }
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
