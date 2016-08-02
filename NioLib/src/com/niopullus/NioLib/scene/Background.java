package com.niopullus.NioLib.scene;


import com.niopullus.NioLib.draw.*;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;

/**Used to display a rectangular visual in multiple contexts
 * Created by Owen on 3/19/2016.
 */
public class Background implements Parcel {

    private int width;
    private int height;

    public Background() {
        this(0, 0);
    }

    public Background(final int _width, final int _height) {
        width = _width;
        height = _height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(final int _width) {
        width = _width;
    }

    public void setHeight(final int _height) {
        height = _height;
    }

    public void parcelDraw(final Canvas canvas) {
        //To be overridden
    }

}
