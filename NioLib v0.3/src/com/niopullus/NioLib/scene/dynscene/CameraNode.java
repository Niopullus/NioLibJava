package com.niopullus.NioLib.scene.dynscene;

/**A node with movement bounds designed to have the setCamera(Node) method used on
 * Created by Owen on 4/10/2016.
 */
public class CameraNode extends Node {

    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;

    public CameraNode() {
        super();
        this.xMin = 0;
        this.yMin = 0;
        this.xMax = 0;
        this.yMax = 0;
    }

    public void setBounds(final int xMin, final int yMin, final int xMax, final int yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public void setX(final int x) {
        if (x > xMax) {
            super.setX(xMax);
        } else if (x < xMin) {
            super.setX(xMin);
        } else {
            super.setX(x);
        }
    }

    public void setY(int y) {
        if (y > this.yMax) {
            super.setY(this.yMax);
        } else if (y < this.yMin) {
            super.setY(this.yMin);
        } else {
            super.setY(y);
        }
    }

}
