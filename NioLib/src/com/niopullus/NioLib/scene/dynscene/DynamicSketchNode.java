package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Sketch;
import com.niopullus.NioLib.draw.Canvas;

import java.util.ArrayList;
import java.util.List;


/**Used as a Node that contains extra visual capabilities
 * Created by Owen on 7/4/2016.
 */
public class DynamicSketchNode extends SketchNode {

    private List<Sketch> sketches;
    private Sketch currentSketch;
    private OffsetMode currentOffsetMode;
    private int dx;
    private int dy;

    public DynamicSketchNode(final String name, final Sketch sketch) {
        this(name, sketch, sketch.getWidth(), sketch.getHeight());
    }

    public DynamicSketchNode(final String name, final Sketch sketch, final int width, final int height) {
        super(name, sketch, width, height);
        sketches = new ArrayList<>();
        currentSketch = null;
        currentOffsetMode = null;
    }

    public DynamicSketchNode() {
        this("unnamed node", null);
    }

    public void intergrate(final Node node) {
        super.integrate(node);
        sketches = new ArrayList<>();
        currentSketch = null;
        currentOffsetMode = null;
    }

    public Sketch getCurrentSketch() {
        return currentSketch;
    }

    public Sketch getSketch(final int index) {
        return index == 0 ? getSketch() : sketches.get(index - 1);
    }

    public int getSketchCount() {
        return sketches.size() + 1;
    }

    public void changeSketch(final int index, final OffsetMode mode, final boolean changeCollision) {
        final int width = getWidth();
        final int height = getHeight();
        final int widthDifference;
        final int heightDifference;
        final int sketchWidth;
        final int sketchHeight;
        currentSketch = sketches.get(index);
        currentOffsetMode = mode;
        sketchWidth = currentSketch.getWidth();
        sketchHeight = currentSketch.getHeight();
        widthDifference = sketchWidth - width;
        heightDifference = sketchHeight - height;
        final int sX = deriveCX(widthDifference);
        final int sY = deriveCY(heightDifference);
        dx = sX;
        dy = sY;
        if (changeCollision) {
            setCWidth(widthDifference);
            setCHeight(heightDifference);
            setCX(sX);
            setCY(sY);
        } else {
            setCWidth(0);
            setCHeight(0);
            setCX(0);
            setCY(0);
        }
    }

    private int deriveCX(final int difference) {
        final int result;
        switch (currentOffsetMode) {
            case TOPLEFT: case MIDDLELEFT: case BOTTOMLEFT: result = 0; break;
            case TOPMIDDLE: case MIDDLE: case BOTTOMMIDDLE: result = -difference / 2; break;
            case TOPRIGHT: case MIDDLERIGHT: case BOTTOMRIGHT: result = -difference; break;
            default: result = -1;
        }
        return result;
    }

    private int deriveCY(final int difference) {
        final int result;
        switch(currentOffsetMode) {
            case BOTTOMLEFT: case BOTTOMMIDDLE: case BOTTOMRIGHT: result = 0; break;
            case MIDDLELEFT: case MIDDLE: case MIDDLERIGHT: result = -difference / 2; break;
            case TOPLEFT: case TOPMIDDLE: case TOPRIGHT: result = -difference; break;
            default: result = -1;
        }
        return result;
    }

    public void parcelDraw(final Canvas canvas) {
        final Sketch sketch = getCurrentSketch();
        canvas.o.sketch(sketch, dx, dy, getWidth(), getHeight(), 0);
    }

    private class CollisionOffset {

        public int xDiff;
        public int yDiff;
        public int xOff;
        public int yOff;

        public CollisionOffset(final int _xDiff, final int _yDiff, final int _xOff, final int _yOff) {
            xDiff = _xDiff;
            yDiff = _yDiff;
            xOff = _xOff;
            yOff = _yOff;
        }

    }

    public enum OffsetMode {

        TOPLEFT,
        TOPMIDDLE,
        TOPRIGHT,
        MIDDLELEFT,
        MIDDLE,
        MIDDLERIGHT,
        BOTTOMLEFT,
        BOTTOMMIDDLE,
        BOTTOMRIGHT

    }

}
