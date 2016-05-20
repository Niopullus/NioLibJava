package com.niopullus.NioLib.scene.dynscene;

import java.awt.*;

/**A trigger which takes the form of a colorable shape
 * Created by Owen on 4/6/2016.
 */
public class ShapeTrigger extends ShapeNode implements Trigger {

    private int xRad;
    private int yRad;

    public ShapeTrigger(final String name, final int width, final int height, final Color color, final int xRad, final int yRad) {
        super(name, width, height, color);
    }

    public int getXRad() {
        return xRad;
    }

    public int getYRad() {
        return yRad;
    }

    public int getCx() {
        return getX() + getWidth() / 2 - getXRad();
    }

    public int getCy() {
        return getY() + getHeight() / 2 - getYRad();
    }

    public int getCwidth() {
        return getWidth() / 2 + getXRad();
    }

    public int getCheight() {
        return getHeight() / 2 + getYRad();
    }

    public void trigger(CollideData data) {
        //To be overridden
    }

}
