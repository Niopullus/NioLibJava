package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Animation;
import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**Image Node that can switch through images and animations
 * Created by Owen on 3/27/2016.
 */
public class DynamicImageNode extends ImageNode {

    private ArrayList<Animation> animations;
    private int curAnimation;
    private int animationTimer;
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

    public DynamicImageNode(final String name, final BufferedImage image) {
        this(name, image, image.getWidth(), image.getHeight());
    }

    public DynamicImageNode(final String name, final BufferedImage image, final int width, final int height) {
        super(name, image, width, height);
        this.animations = new ArrayList<Animation>();
        this.curAnimation = 0;
        this.animationTimer = 0;
        this.indefTimer = false;
    }

    public Animation getAnimation() {
        return animations.get(curAnimation);
    }

    public void addAnimation(final Animation animation) {
        animations.add(animation);
    }

    private void modCollisionBox(final Animation animation, final BufferedImage image, final int offsetMode) {
        CollisionOffset offset = getOffset(animation, image, offsetMode);
        mod = true;
        addCX(-offset.xOff);
        addCY(-offset.yOff);
        addCWidth(offset.xDiff);
        addCHeight(offset.yDiff);
    }

    private void unmodCollisionBox(final Animation animation, final BufferedImage image, final int offsetMode) {
        CollisionOffset offset = getOffset(animation, image, offsetMode);
        mod = false;
        addCX(offset.xOff);
        addCY(offset.yOff);
        addCWidth(-offset.xDiff);
        addCHeight(-offset.yDiff);
    }

    private CollisionOffset getOffset(final Animation animation, final BufferedImage image, final int offsetMode) {
        final int xDif = (int) ((animation.getWidth() - image.getWidth()) * getXScale());
        final int yDif = (int) ((animation.getHeight() - image.getHeight()) * getYScale());
        final int xOff = deriveXOffsetFromMode(offsetMode, xDif);
        final int yOff = deriveYOffsetFromMode(offsetMode, yDif);
        return new CollisionOffset(xDif, yDif, xOff, yOff);
    }

    public void runAnimation(final int index, final int offsetMode, final boolean modC) {
        indefTimer = true;
        runAnimation(index, 0, offsetMode, modC);
    }

    public void runAnimation(final int index, final int duration, final int offsetMode, final boolean modC) {
        curAnimation = index;
        animationTimer = duration;
        currentOffsetMode = offsetMode;
        if (modC) {
            modCollisionBox(getAnimation(), getImage(), offsetMode);
        }
    }

    public void runAnimationOnce(final int index, final int offsetMode, final boolean modC) {
        final Animation animation = animations.get(index);
        runAnimation(index, animation.calcRunOnceTime(), offsetMode, modC);
    }

    public void cancelAnimation() {
        this.animationTimer = 0;
        this.indefTimer = false;
        if (mod) {
            unmodCollisionBox(getAnimation(), getImage(), currentOffsetMode);
        }
    }

    public void draw() {
        final BufferedImage image = getImage();
        if (reset && mod) {
            unmodCollisionBox(getAnimation(), getImage(), currentOffsetMode);
            reset = false;
        }
        if (animationTimer > 0 || this.indefTimer) {
            if (animationTimer == 1) {
                reset = true;
            }
            final Animation animation = getAnimation();
            final int xDif = (int) ((animation.getWidth() - image.getWidth()) * getXScale());
            final int yDif = (int) ((animation.getHeight() - image.getHeight()) * getYScale());
            final int scaledAWidth = (int) (animation.getWidth() * getXScale());
            final int scaledAHeight = (int) (animation.getHeight() * getYScale());
            final int xOff = this.deriveXOffsetFromMode(currentOffsetMode, xDif);
            final int yOff = this.deriveYOffsetFromMode(currentOffsetMode, yDif);
            final int x = getTX() - xOff;
            final int y = getTY() - yOff;
            animation.draw(x, y, scaledAWidth, scaledAHeight, getZ(), getAngle());
            animationTimer--;
        } else {
            Draw.image(getTX(), getTY(), getWidth(), getHeight(), getZ(), getAngle(), image);
        }
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

        public CollisionOffset(final int xDiff, final int yDiff, final int xOff, final int yOff) {
            this.xDiff = xDiff;
            this.yDiff = yDiff;
            this.xOff = xOff;
            this.yOff = yOff;
        }

    }

}
