package com.niopullus.NioLib.scene;


import java.awt.*;
import com.niopullus.NioLib.draw.Canvas;

/**Displays a rectangle of a particular color
 * Created by Owen on 3/19/2016.
 */
public class ColorBackground extends Background {

    private Color color;

    public ColorBackground() {
        super();
    }

    public ColorBackground(final Color _color, final int width, final int height) {
        super(width, height);
        color = _color;
    }

    public ColorBackground(final Color color) {
        this(color, 0, 0);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color _color) {
        color = _color;
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.rect(color, 0, 0, getWidth(), getHeight(), 0);
    }

}
