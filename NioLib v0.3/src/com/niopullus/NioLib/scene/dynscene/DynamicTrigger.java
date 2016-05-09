package com.niopullus.NioLib.scene.dynscene;

import java.awt.image.BufferedImage;

/**Trigger that also serves as a Dynamic Image Node
 * Created by Owen on 4/6/2016.
 */
public class DynamicTrigger extends DynamicImageNode implements Trigger {

    private int xRad;
    private int yRad;

    public DynamicTrigger(final String name, final BufferedImage image, final int xRad, final int yRad) {
        super(name, image);
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

}
