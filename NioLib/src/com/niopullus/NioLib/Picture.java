package com.niopullus.NioLib;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.Parcel;

import java.awt.image.BufferedImage;

/**
 * Created by Owen on 7/4/2016.
 */
public class Picture implements Sketch, Parcel {

    private BufferedImage image;

    public Picture(final BufferedImage _image) {
        image = _image;
    }

    public Picture(final String fileName) {
        image = Utilities.loadImage(fileName);
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.image(image, 0, 0, image.getWidth(), image.getHeight(), 0);
    }

}
