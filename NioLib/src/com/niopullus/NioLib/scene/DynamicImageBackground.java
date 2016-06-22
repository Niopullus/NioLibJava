package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;
import java.awt.image.BufferedImage;

/**ImageBackground that can shift
 * Created by Owen on 3/25/2016.
 */
public class DynamicImageBackground extends Background {

    private BufferedImage image;
    private int xShiftSpeed;
    private int yShiftSpeed;
    private Rectangle window;

    public DynamicImageBackground(final BufferedImage image, final int width, final int height) {
        this.xShiftSpeed = 0;
        this.yShiftSpeed = 0;
        this.window = new Rectangle(0, 0, width, height);
        this.image = image;
    }

    public void parcelDraw(final Canvas canvas) {
        final int xBound = image.getWidth();
        final int yBound = image.getHeight();
        final int sx1, sy1, sx2, sy2;
        window.x += xShiftSpeed;
        window.y += yShiftSpeed;
        if (window.x > xBound) {
            window.x = window.x % xBound;
        }
        if (window.x < 0) {
            window.x = window.x % xBound + xBound;
        }
        if (window.y > yBound) {
            window.y = window.y % yBound;
        }
        if (window.y < 0) {
            window.y = window.y % yBound + yBound;
        }
        sx1 = window.x;
        sy1 = window.y;
        sx2 = window.x + window.width;
        sy2 = window.y + window.height;
        if (sx2 <= xBound && sy2 <= yBound) {
            canvas.o.image(image, 0, 0, getWidth(), getHeight(), sx1, sy1, sx2, sy2, 0, 0);
        } else if (sx2 >= xBound && sy2 <= yBound) {
            final int xBound2 = (int) ((double) (xBound - sx1) / (sx2 - sx1) * getWidth());
            canvas.o.image(image, 0, 0, xBound2, getHeight(), sx1, sy1, xBound, sy2, 0, 0);
            canvas.o.image(image, xBound2, 0, getWidth(), getHeight(), 0, sy1, sx2 - xBound, sy2, 0, 0);
        } else if (sx2 <= xBound && sy2 >= yBound) {
            final int yBound2 = (int) ((double) (yBound - sy1) / (sy2 - sy1) * getHeight());
            canvas.o.image(image, 0, 0, getWidth(), yBound2, sx1, sy1, sx2, yBound, 0, 0);
            canvas.o.image(image, 0, yBound2, getWidth(), getHeight(), sx1, 0, sx2, sy2 - yBound, 0, 0);
        } else if (sx2 >= xBound && sy2 >= yBound) {
            final int xBound2 = (int) ((double) (xBound - sx1) / (sx2 - sx1) * getWidth());
            final int yBound2 = (int) ((double) (yBound - sy1) / (sy2 - sy1) * getHeight());
            canvas.o.image(image, 0, 0, xBound2, yBound2, sx1, sy1, xBound, yBound, 0, 0);
            canvas.o.image(image, xBound2, 0, getWidth(), getHeight(), 0, sy1, sx2 - xBound, sy2, 0, 0);
            canvas.o.image(image, 0, yBound2, getWidth(), getHeight(), sx1, 0, sx2, sy2 - yBound, 0, 0);
            canvas.o.image(image, xBound2, yBound2, getWidth(), getHeight(), 0, 0, sx2 - xBound, sy2 - yBound, 0, 0);
        }
    }

    public void setxShiftSpeed(final int xShiftSpeed) {
        this.xShiftSpeed = xShiftSpeed;
    }

    public void setyShiftSpeed(final int yShiftSpeed) {
        this.yShiftSpeed = yShiftSpeed;
    }

    public void setWindow(final Rectangle window) {
        this.window = window;
    }

    public void shiftX(final int shift) {
        window.setBounds(window.x + shift, window.y, window.width, window.height);
    }

    public void shiftY(final int shift) {
        window.setBounds(window.x, window.y + shift, window.width, window.height);
    }

    public void setXShift(final int shift) {
        window.setBounds(shift, window.y, window.width, window.height);
    }

    public void setYShift(final int shift) {
        window.setBounds(window.x, shift, window.width, window.height);
    }

}
