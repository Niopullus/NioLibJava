package com.niopullus.NioLib.scene.dynscene;

/**
 * Created by Owen on 4/6/2016.
 */
public class ImageTrigger extends ImageNode implements Trigger {

    private int xRad;
    private int yRad;

    public ImageTrigger(String name, String imgDir, int xRad, int yRad) {
        super(name, imgDir);
        this.xRad = xRad;
        this.yRad = yRad;
    }

    public void trigger() {

    }

    public int getCx() {
        return this.getX() + this.getWidth() - this.xRad;
    }

    public int getCy() {
        return this.getY() + this.getHeight() - this.yRad;
    }

    public int getCwidth() {
        return -this.getWidth() + this.xRad * 2;
    }

    public int getCheight() {
        return -this.getHeight() + this.yRad * 2;
    }

}
