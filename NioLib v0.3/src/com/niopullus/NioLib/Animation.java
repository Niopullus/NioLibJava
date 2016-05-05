package com.niopullus.NioLib;

import com.niopullus.NioLib.utilities.Utilities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Owen on 3/24/2016.
 */
public class Animation {

    private ArrayList<BufferedImage> frames;
    private double currentFrame;
    private double frameIterationCoefficient;

    public Animation() {
        this.frames = new ArrayList<BufferedImage>();
        this.currentFrame = 0;
        this.frameIterationCoefficient = 0.1;
    }

    public void addFrame(BufferedImage img) {
        this.frames.add(img);
    }

    public void addFrame(String imgDir) {
        this.frames.add(Utilities.loadImage(imgDir));
    }

    public void draw(int x, int y, int z, double angle) {
        if (this.currentFrame + this.frameIterationCoefficient < this.frames.size()) {
            this.currentFrame += this.frameIterationCoefficient;
        } else {
            this.currentFrame = 0;
        }
        Draw.image(x, y, z, angle, this.frames.get((int) this.currentFrame));
    }

    public void draw(int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, int z) {
        this.draw(dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, z, 0);
    }

    public void draw(int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, int z, double angle) {
        if (this.currentFrame + this.frameIterationCoefficient < this.frames.size()) {
            this.currentFrame += this.frameIterationCoefficient;
        } else {
            this.currentFrame = 0;
        }
        Draw.image(dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, z, angle, this.frames.get((int) this.currentFrame));
    }

    public void draw(int dx1, int dy1, int dx2, int dy2, int z, double angle) {
        if (this.currentFrame + this.frameIterationCoefficient < this.frames.size()) {
            this.currentFrame += this.frameIterationCoefficient;
        } else {
            this.currentFrame = 0;
        }
        BufferedImage image = this.frames.get((int) this.currentFrame);
        Draw.image(dx1, dy1, dx2, dy2, 0, 0, image.getWidth(), image.getHeight(), z, angle, image);
    }

    public void setSpeed(double speed) {
        this.frameIterationCoefficient = speed;
    }

    public int getWidth() {
        return this.frames.get((int) this.currentFrame).getWidth();
    }

    public int getHeight() {
        return this.frames.get((int) this.currentFrame).getHeight();
    }

    public int calcRunOnceTime() {
        return (int) Math.ceil((double) this.frames.size() / this.frameIterationCoefficient);
    }

}
