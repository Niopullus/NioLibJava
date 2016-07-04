package com.niopullus.NioLib;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.Parcel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**Displays images by frame
 * Created by Owen on 3/24/2016.
 */
public class Animation implements Sketch, Parcel {

    private List<BufferedImage> frames;
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

    public BufferedImage getImage() {
        return frames.get((int) currentFrame);
    }

    public void setFrameRate(final double _frameRate) {
        frameRate = _frameRate;
    }

    public void addFrame(final BufferedImage image) {
        frames.add(image);
        if (!init) {
            width = image.getWidth();
            height = image.getHeight();
            init = true;
        }
    }

    public BufferedImage runFrame() {
        final BufferedImage image = frames.get((int) currentFrame);
        currentFrame += frameRate;
        return image;
    }

    public int calcRunOnceTime() {
        final double times = frames.size() / frameRate;
        return (int) Math.ceil(times);
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.animation(this, 0, 0, width, height, 0);
    }

}
