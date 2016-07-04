package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Animation;
import com.niopullus.NioLib.Sketch;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**Used as a Node that contains extra visual capabilities
 * Created by Owen on 7/4/2016.
 */
public class DynamicNode extends Node {

    private List<Sketch> sketches;
    private int currentSketch;
    private int timer;
    private int currentOffsetMode;
    private boolean indefTimer;
    private boolean reset;
    private boolean mod;
    public static final int OFFSETMODE_MIDDLE = 0;
    public static final int OFFSETMODE_TOPLEFT = 1;
    public static final int OFFSETMODE_TOPRIGHT = 2;
    public static final int OFFSETMODE_BOTTOMLEFT = 3;
    public static final int OFFSETMODE_BOTTOMRIGHT = 4;
    public static final int OFFSETMODE_TOPMIDDLE = 5;
    public static final int OFFSETMODE_LEFTMIDDLE = 6;
    public static final int OFFSETMODE_RIGHTMIDDLE = 7;
    public static final int OFFSETMODE_BOTTOMMIDDLE = 8;

    public DynamicNode(final String name, final Sketch sketch) {
        this(name, sketch, sketch.getWidth(), sketch.getHeight());
    }

    public DynamicNode(final String name, final Sketch sketch, final int width, final int height) {
        super(name);
        sketches = new ArrayList<>();
        timer = 0;
        indefTimer = false;
    }

    public Sketch getSketch() {
        return sketches.get(currentSketch);
    }

    private CollisionOffset getOffset(final Sketch newSketch, final Sketch oldSketch, final int offsetMode) {
        final int xDif = (int) ((newSketch.getWidth() - oldSketch.getWidth()) * getXScale());
        final int yDif = (int) ((newSketch.getHeight() - oldSketch.getHeight()) * getYScale());
        final int xOff = deriveXOffsetFromMode(offsetMode, xDif);
        final int yOff = deriveYOffsetFromMode(offsetMode, yDif);
        return new CollisionOffset(xDif, yDif, xOff, yOff);
    }

    public void addSketch(final Sketch sketch) {
        sketches.add(sketch);
    }

    private void modCollisionBox(final Sketch newSketch, final Sketch oldSketch, final int offsetMode) {
        final CollisionOffset offset = getOffset(newSketch, oldSketch, offsetMode);
        mod = true;
        addCX(-offset.xOff);
        addCY(-offset.yOff);
        addCWidth(offset.xDiff);
        addCHeight(offset.yDiff);
    }

    private void unmodCollisionBox(final Sketch newSketch, final Sketch oldSketch, final int offsetMode) {
        final CollisionOffset offset = getOffset(newSketch, oldSketch, offsetMode);
        mod = false;
        addCX(offset.xOff);
        addCY(offset.yOff);
        addCWidth(-offset.xDiff);
        addCHeight(-offset.yDiff);
    }

    public void change(final int index, final int offsetMode, final boolean modC) {
        indefTimer = true;
        change(index, 0, offsetMode, modC);
    }

    public void change(final int index, final int duration, final int offsetMode, final boolean modC) {
        currentSketch = index;
        timer = duration;
        currentOffsetMode = offsetMode;
        if (modC) {
            modCollisionBox(getAnimation(), getImage(), offsetMode);
        }
    }

    public void chanceOnce(final int index, final int offsetMode, final boolean modC) {
        final Animation animation = animations.get(index);
        change(index, animation.calcRunOnceTime(), offsetMode, modC);
    }

    public void cancelChange() {
        animationTimer = 0;
        indefTimer = false;
        if (mod) {
            unmodCollisionBox(getSketch(), getImage(), currentOffsetMode);
        }
    }

    public void parcelDraw(final Canvas canvas) {
        if (reset && mod) {
            unmodCollisionBox(getAnimation(), getImage(), currentOffsetMode);
            reset = false;
        }
        if (timer > 0 || indefTimer) {
            if (timer == 1) {
                reset = true;
            }
            final Sketch sketch = getSketch();
            final int xDif = (int) ((sketch.getWidth() - sketch.getWidth()) * getXScale());
            final int yDif = (int) ((sketch.getHeight() - sketch.getHeight()) * getYScale());
            final int scaledAWidth = (int) (sketch.getWidth() * getXScale());
            final int scaledAHeight = (int) (sketch.getHeight() * getYScale());
            final int xOff = deriveXOffsetFromMode(currentOffsetMode, xDif);
            final int yOff = deriveYOffsetFromMode(currentOffsetMode, yDif);
            final int x = getTX() - xOff;
            final int y = getTY() - yOff;
            canvas.o.sketch(sketch, x, y, scaledAWidth, scaledAHeight, 0, 0);
            timer--;
        } else {
            canvas.o.sketch(image, 0, 0, getWidth(), getHeight(), 0, 0);
        }
        super.parcelDraw(canvas);
    }

    private int deriveXOffsetFromMode(final int mode, final int xDiff) {
        switch (mode) {
            case OFFSETMODE_BOTTOMLEFT: return 0;
            case OFFSETMODE_LEFTMIDDLE: return 0;
            case OFFSETMODE_TOPLEFT: return 0;
            case OFFSETMODE_TOPMIDDLE: return xDiff / 2;
            case OFFSETMODE_MIDDLE: return xDiff / 2;
            case OFFSETMODE_BOTTOMMIDDLE: return xDiff / 2;
            case OFFSETMODE_TOPRIGHT: return xDiff;
            case OFFSETMODE_RIGHTMIDDLE: return xDiff;
            case OFFSETMODE_BOTTOMRIGHT: return xDiff;
        }
        return xDiff / 2;
    }

    private int deriveYOffsetFromMode(final int mode, final int yDiff) {
        switch (mode) {
            case OFFSETMODE_TOPLEFT: return yDiff;
            case OFFSETMODE_TOPMIDDLE: return yDiff;
            case OFFSETMODE_TOPRIGHT: return yDiff;
            case OFFSETMODE_LEFTMIDDLE: return yDiff / 2;
            case OFFSETMODE_MIDDLE: return yDiff / 2;
            case OFFSETMODE_RIGHTMIDDLE: return yDiff / 2;
            case OFFSETMODE_BOTTOMLEFT: return 0;
            case OFFSETMODE_BOTTOMMIDDLE: return 0;
            case OFFSETMODE_BOTTOMRIGHT: return 0;
        }
        return yDiff / 2;
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



    }

}
