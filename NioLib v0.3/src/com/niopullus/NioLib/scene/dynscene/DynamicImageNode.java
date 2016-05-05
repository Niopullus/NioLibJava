package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Animation;
import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Owen on 3/27/2016.
 */
public class DynamicImageNode extends Node {

    private BufferedImage image;
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

    public DynamicImageNode(String name, String imgDir) {
        this(name, imgDir, Utilities.loadImage(imgDir).getWidth(), Utilities.loadImage(imgDir).getHeight());
    }

    public DynamicImageNode(String name, String imgDir, int width, int height) {
        super(name, width, height);
        this.image = Utilities.loadImage(imgDir);
        this.animations = new ArrayList<Animation>();
        this.curAnimation = 0;
        this.animationTimer = 0;
        this.setOWidth(this.image.getWidth());
        this.setOHeight(this.image.getHeight());
        this.csetXScale((double) width / this.getOWidth());
        this.csetYScale((double) height / this.getOHeight());
        this.indefTimer = false;
    }

    public void addAnimation(Animation animation) {
        this.animations.add(animation);
    }

    public void runAnimation(int index, int offsetMode, boolean modC) {
        this.curAnimation = index;
        this.currentOffsetMode = offsetMode;
        this.indefTimer = true;
        if (modC) {
            this.mod = true;
            Animation animation = this.animations.get(this.curAnimation);
            int xDif = (int) ((animation.getWidth() - this.image.getWidth()) * getXScale());
            int yDif = (int) ((animation.getHeight() - this.image.getHeight()) * getYScale());
            int xOff = this.deriveXOffsetFromMode(this.currentOffsetMode, xDif);
            int yOff = this.deriveYOffsetFromMode(this.currentOffsetMode, yDif);
            int scaledAWidth = (int) (animation.getWidth() * this.getXScale());
            int scaledAHeight = (int) (animation.getHeight() * this.getYScale());
            this.addCX(-xOff);
            this.addCY(-yOff);
            this.addCWidth(xDif);
            this.addCHeight(yDif);
        }
    }

    public void runAnimation(int index, int duration, int offsetMode, boolean modC) {
        System.out.println(duration);
        this.curAnimation = index;
        this.animationTimer = duration;
        this.currentOffsetMode = offsetMode;
        if (modC) {
            this.mod = true;
            Animation animation = this.animations.get(this.curAnimation);
            int xDif = (int) ((animation.getWidth() - this.image.getWidth()) * getXScale());
            int yDif = (int) ((animation.getHeight() - this.image.getHeight()) * getYScale());
            int xOff = this.deriveXOffsetFromMode(this.currentOffsetMode, xDif);
            int yOff = this.deriveYOffsetFromMode(this.currentOffsetMode, yDif);
            this.addCX(-xOff);
            this.addCY(-yOff);
            this.addCWidth(xDif);
            this.addCHeight(yDif);
        }
    }

    public void runAnimationOnce(int index, int offsetMode, boolean modC) {
        this.runAnimation(index, this.animations.get(index).calcRunOnceTime(), offsetMode, modC);
    }

    public void cancelAnimation() {
        this.animationTimer = 0;
        this.indefTimer = false;
        if (this.mod) {
            this.resetCOffset();
        }
    }

    private void resetCOffset() {
        Animation animation = this.animations.get(this.curAnimation);
        int xDif = (int) ((animation.getWidth() - this.image.getWidth()) * getXScale());
        int yDif = (int) ((animation.getHeight() - this.image.getHeight()) * getYScale());
        int xOff = this.deriveXOffsetFromMode(this.currentOffsetMode, xDif);
        int yOff = this.deriveYOffsetFromMode(this.currentOffsetMode, yDif);
        this.addCX(xOff);
        this.addCY(yOff);
        this.addCWidth(-xDif);
        this.addCHeight(-yDif);
        this.mod = false;
    }

    public void draw() {
        if (this.reset && this.mod) {
            resetCOffset();
            this.reset = false;
        }
        if (this.animationTimer > 0 || this.indefTimer) {
            if (this.animationTimer == 1) {
                this.reset = true;
            }
            Animation animation = this.animations.get(this.curAnimation);
            int xDif = (int) ((animation.getWidth() - this.image.getWidth()) * getXScale());
            int yDif = (int) ((animation.getHeight() - this.image.getHeight()) * getYScale());
            int scaledAWidth = (int) (animation.getWidth() * this.getXScale());
            int scaledAHeight = (int) (animation.getHeight() * this.getYScale());
            int xOff = this.deriveXOffsetFromMode(this.currentOffsetMode, xDif);
            int yOff = this.deriveYOffsetFromMode(this.currentOffsetMode, yDif);
            animation.draw(this.getTX() - xOff, Main.Height() - yOff - this.getHeight() - this.getTY(), scaledAWidth + this.getTX() - xOff, -yOff + Main.Height() - this.getTY() - this.getHeight() + scaledAHeight, this.getZ(), this.getAngle());
            this.animationTimer--;
        } else {
            Draw.image(this.getTX(), Main.Height() - this.getTY() - this.getHeight(), this.getTX() + this.getWidth(), Main.Height() - this.getTY() - this.getHeight() + this.getHeight(), 0, 0, this.image.getWidth(), this.image.getHeight(), this.getZ(), this.getAngle(), this.image);
        }
    }

    private int deriveXOffsetFromMode(int mode, int xDiff) {
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

    private int deriveYOffsetFromMode(int mode, int yDiff) {
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

}
