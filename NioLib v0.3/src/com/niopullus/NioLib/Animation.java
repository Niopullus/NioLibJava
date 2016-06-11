package com.niopullus.NioLib;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**Displays images by frame
 * Created by Owen on 3/24/2016.
 */
public class Animation {

    private List<BufferedImage> frames;
    private double currentFrame;
    private double frameRate;
    private static final double defaultFrameRate = 0.1;

    public Animation() {
        this.frames = new ArrayList<>();
        this.currentFrame = 0;
        this.frameRate = defaultFrameRate;
    }

    public void addFrame(final BufferedImage image) {
        frames.add(image);
    }

    public void setFrameRate(final double frameRate) {
        this.frameRate = frameRate;
    }

    public int getWidth() {
        final BufferedImage image = frames.get((int) currentFrame);
        return image.getWidth();
    }

    public int getHeight() {
        final BufferedImage image = frames.get((int) currentFrame);
        return image.getHeight();
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

}
