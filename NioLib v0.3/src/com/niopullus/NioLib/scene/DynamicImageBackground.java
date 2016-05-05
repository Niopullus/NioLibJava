package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.scene.dynscene.Node;
import com.niopullus.NioLib.utilities.Utilities;
import com.niopullus.NioLib.Draw;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Owen on 3/25/2016.
 */
public class DynamicImageBackground extends Background {

    private BufferedImage image;
    private int xShiftSpeed;
    private int yShiftSpeed;
    private int xShift;
    private int yShift;
    private Rectangle window;
    private boolean resetMode;

    public DynamicImageBackground(int width, int height, String imgDir) {
        this(0, 0, width, height, imgDir);
    }

    public DynamicImageBackground(int x, int y, int width, int height, String imgDir) {
        super( x, y, width, height);
        this.xShiftSpeed = 0;
        this.yShiftSpeed = 0;
        this.window = new Rectangle(0, 0, width, height);
        this.image = Utilities.loadImage(imgDir);
        this.resetMode = false;
    }

    public void draw() {
        this.draw(this.getX(), this.getY());
    }

    public void draw(int x, int y) {
        int xBound = this.image.getWidth();
        int yBound = this.image.getHeight();
        if (this.resetMode) {
            this.xShift += this.xShiftSpeed;
            this.yShift += this.yShiftSpeed;
        }
        this.window.x += this.xShift;
        this.window.y += this.yShift;
        if (this.window.x > xBound) {
            this.window.x = this.window.x % xBound;
        }
        if (this.window.x < 0) {
            this.window.x = this.window.x % xBound + xBound;
        }
        if (this.window.y > yBound) {
            this.window.y = this.window.y % yBound;
        }
        if (this.window.y < 0) {
            this.window.y = this.window.y % yBound + yBound;
        }
        int dx1 = x;
        int dy1 = y;
        int dx2 = x + this.getWidth();
        int dy2 = y + this.getHeight();
        int sx1 = this.window.x;
        int sy1 = this.window.y;
        int sx2 = this.window.x + this.window.width;
        int sy2 = this.window.y + this.window.height;
        if (sx2 <= xBound && sy2 <= yBound) {
            Draw.image(dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this.getZ(),this.image);
        } else if (sx2 >= xBound && sy2 <= yBound) {
            int xBound2 = (int) ((double) (xBound - sx1) / (sx2 - sx1) * this.getWidth());
            Draw.image(dx1, dy1, xBound2 + dx1, dy2, sx1, sy1, xBound, sy2, this.getZ(), this.image);
            Draw.image(xBound2 + dx1, dy1, dx2, dy2, 0, sy1, sx2 - xBound, sy2, this.getZ(), this.image);
        } else if (sx2 <= xBound && sy2 >= yBound) {
            int yBound2 = (int) ((double) (yBound - sy1) / (sy2 - sy1) * this.getHeight());
            Draw.image(dx1, dy1, dx2, yBound2 + dy1, sx1, sy1, sx2, yBound, this.getZ(), this.image);
            Draw.image(dx1, yBound2 + dy1, dx2, dy2, sx1, 0, sx2, sy2 - yBound, this.getZ(), this.image);
        } else if (sx2 >= xBound && sy2 >= yBound) {
            int xBound2 = (int) ((double) (xBound - sx1) / (sx2 - sx1) * this.getWidth());
            int yBound2 = (int) ((double) (yBound - sy1) / (sy2 - sy1) * this.getHeight());
            Draw.image(dx1, dy1, xBound2 + dx1, yBound2 + dy1, sx1, sy1, xBound, yBound, this.getZ(), this.image);
            Draw.image(xBound2 + dx1, dy1, dx2, dy2, 0, sy1, sx2 - xBound, sy2, this.getZ(), this.image);
            Draw.image(dx1, yBound2 + dy1, dx2, dy2, sx1, 0, sx2, sy2 - yBound, this.getZ(),this.image);
            Draw.image(xBound2 + dx1, yBound2 + dy1, dx2, dy2, 0, 0, sx2 - xBound, sy2 - yBound, this.getZ(), this.image);
        }
    }

    public void setxShiftSpeed(int xShiftSpeed) {
        this.xShiftSpeed = xShiftSpeed;
        if (!this.resetMode) {
            this.xShift = xShiftSpeed;
        }
    }

    public void setyShiftSpeed(int yShiftSpeed) {
        this.yShiftSpeed = yShiftSpeed;
        if (!this.resetMode) {
            this.yShift = yShiftSpeed;
        }
    }

    public void setWindow(Rectangle window) {
        this.window = window;
    }

    public void shiftX(int shift) {
        this.window.setBounds(this.window.x + shift, this.window.y, this.window.width, this.window.height);
    }

    public void shiftY(int shift) {
        this.window.setBounds(this.window.x, this.window.y + shift, this.window.width, this.window.height);
    }

    public void setXShift(int shift) {
        this.window.setBounds(shift, this.window.y, this.window.width, this.window.height);
    }

    public void setYShift(int shift) {
        this.window.setBounds(this.window.x, shift, this.window.width, this.window.height);
    }

    public void enableResetMode() {
        this.resetMode = true;
    }

    public void disableResetMode() {
        this.resetMode = false;
    }

}
