package com.niopullus.NioLib.scene.dynscene;

/**A node with movement bounds designed to have the setCamera(Node) method used on
 * Created by Owen on 4/10/2016.
 */
public class CameraNode extends Node {

    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;

    public CameraNode(final String name, final int xMin, final int yMin, final int xMax, final int yMax) {
        super(name);
        setup(xMin, yMin, xMax, yMax);
    }

    public CameraNode() {
        this("unnamed node", 0, 0, 0, 0);
    }

    public void init(final Node node) {
        super.init(node);
        if (node instanceof CameraNode) {
            final CameraNode cameraNode = (CameraNode) node;
            setup(cameraNode.xMin, cameraNode.yMin, cameraNode.xMax, cameraNode.yMax);
        }
    }

    public void setup(final int xMin, final int yMin, final int xMax, final int yMax) {
        setBounds(xMin, yMin, xMax, yMax);
    }

    public void setBounds(final int _xMin, final int _yMin, final int _xMax, final int _yMax) {
        xMin = _xMin;
        yMin = _yMin;
        xMax = _xMax;
        yMax = _yMax;
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

    public void setY(final int y) {
        if (y > yMax) {
            super.setY(yMax);
        } else if (y < yMin) {
            super.setY(yMin);
        } else {
            super.setY(y);
        }
    }

}
