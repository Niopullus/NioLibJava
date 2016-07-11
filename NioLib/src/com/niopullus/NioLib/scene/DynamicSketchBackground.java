package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Sketch;
import com.niopullus.NioLib.draw.Canvas;

/**Displays Sketches with greater capabilities
 * Created by Owen on 7/4/16.
 */
public class DynamicSketchBackground extends SketchBackground {

    private int xShiftSpeed;
    private int yShiftSpeed;
    private int wX;
    private int wY;
    private int wWidth;
    private int wHeight;

    public DynamicSketchBackground(final Sketch sketch) {
        this(sketch, 0, 0);
    }

    public DynamicSketchBackground(final Sketch sketch, final int width, final int height) {
        super(sketch, width, height);
    }

    public void setXShiftSpeed(final int speed) {
        xShiftSpeed = speed;
    }

    public void setyShiftSpeed(final int speed) {
        yShiftSpeed = speed;
    }

    public void setWX(final int _wX) {
        wX = _wX;
    }

    public void setWY(final int _wY) {
        wY = _wY;
    }

    public void setWWidth(final int _wWidth) {
        wWidth = _wWidth;
    }

    public void setWHeight(final int _wHeight) {
        wHeight = _wHeight;
    }

    public void setWindowBounds(final int _wX, final int _wY, final int _wWidth, final int _wHeight) {
        setWX(_wX);
        setWY(_wY);
        setWWidth(_wWidth);
        setWHeight(_wHeight);
    }

    public void parcelDraw(final Canvas canvas) {
        final Sketch sketch = getSketch();
        final int xBound = sketch.getWidth();
        final int yBound = sketch.getHeight();
        final int sx1, sy1, sx2, sy2;
        wX += xShiftSpeed;
        wY += yShiftSpeed;
        if (wX > xBound) {
            wX = wX % xBound;
        }
        if (wX < 0) {
            wX = wX % xBound + xBound;
        }
        if (wY > yBound) {
            wY = wY % yBound;
        }
        if (wY < 0) {
            wY = wY % yBound + yBound;
        }
        sx1 = wX;
        sy1 = wY;
        sx2 = wX + wWidth;
        sy2 = wY + wHeight;
        if (sx2 <= xBound && sy2 <= yBound) {
            canvas.o.sketch(sketch, 0, 0, getWidth(), getHeight(), sx1, sy1, sx2, sy2, 0, 0, 1);
        } else if (sx2 >= xBound && sy2 <= yBound) {
            final int xBound2 = (int) ((double) (xBound - sx1) / (sx2 - sx1) * getWidth());
            canvas.o.sketch(sketch, 0, 0, xBound2, getHeight(), sx1, sy1, xBound, sy2, 0, 0, 1);
            canvas.o.sketch(sketch, xBound2, 0, getWidth(), getHeight(), 0, sy1, sx2 - xBound, sy2, 0, 0, 1);
        } else if (sx2 <= xBound && sy2 >= yBound) {
            final int yBound2 = (int) ((double) (yBound - sy1) / (sy2 - sy1) * getHeight());
            canvas.o.sketch(sketch, 0, 0, getWidth(), yBound2, sx1, sy1, sx2, yBound, 0, 0, 1);
            canvas.o.sketch(sketch, 0, yBound2, getWidth(), getHeight(), sx1, 0, sx2, sy2 - yBound, 0, 0, 1);
        } else if (sx2 >= xBound && sy2 >= yBound) {
            final int xBound2 = (int) ((double) (xBound - sx1) / (sx2 - sx1) * getWidth());
            final int yBound2 = (int) ((double) (yBound - sy1) / (sy2 - sy1) * getHeight());
            canvas.o.sketch(sketch, 0, 0, xBound2, yBound2, sx1, sy1, xBound, yBound, 0, 0, 1);
            canvas.o.sketch(sketch, xBound2, 0, getWidth(), getHeight(), 0, sy1, sx2 - xBound, sy2, 0, 0, 1);
            canvas.o.sketch(sketch, 0, yBound2, getWidth(), getHeight(), sx1, 0, sx2, sy2 - yBound, 0, 0, 1);
            canvas.o.sketch(sketch, xBound2, yBound2, getWidth(), getHeight(), 0, 0, sx2 - xBound, sy2 - yBound, 0, 0, 1);
        }
    }

}
