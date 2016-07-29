package com.niopullus.NioLib.draw;

import com.niopullus.NioLib.Picture;
import com.niopullus.NioLib.Sketch;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**Displays images by frame
 * Created by Owen on 3/24/2016.
 */
public class Animation implements Sketch, Parcel {

    private List<Picture> frames;
    private double currentFrame;
    private double frameRate;
    private int width;
    private int height;
    private boolean init;
    private static final double defaultFrameRate = 0.1;

    public Animation() {
        frames = new ArrayList<>();
        currentFrame = 0;
        frameRate = defaultFrameRate;
        init = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Picture getPicture() {
        return frames.get((int) currentFrame);
    }

    public void setFrameRate(final double _frameRate) {
        frameRate = _frameRate;
    }

    public void addFrame(final Picture picture) {
        frames.add(picture);
        if (!init) {
            width = picture.getWidth();
            height = picture.getHeight();
            init = true;
        }
    }

    public int getPictureWidth() {
        final Picture picture = getPicture();
        return picture.getWidth();
    }

    public int getPictureHeight() {
        final Picture picture = getPicture();
        return picture.getHeight();
    }

    public BufferedImage getImage() {
        final Picture picture = getPicture();
        currentFrame += frameRate;
        if ((int) currentFrame > frames.size() - 1) {
            currentFrame = 0;
        }
        return picture.getImage();
    }

    public int calcRunOnceTime() {
        final double times = frames.size() / frameRate;
        return (int) Math.ceil(times);
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.animation(this, 0, 0, width, height, 0);
    }

}
