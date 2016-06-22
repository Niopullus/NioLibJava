package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.Parcel;

import java.awt.image.BufferedImage;

/**
 * Created by Owen on 3/19/2016.
 */
public class ImageNode extends Node {

    private BufferedImage image;

    public ImageNode(final String name, final BufferedImage image, final int width, final int height) {
        super(name, width, height);
        this.image = image;
        this.osetWidth(image.getWidth());
        this.osetHeight(image.getHeight());
        this.csetXScale((double) width / ogetWidth());
        this.csetYScale((double) height / ogetHeight());
    }

    public ImageNode(final String name, final BufferedImage image) {
        this(name, image, image.getWidth(), image.getHeight());
    }

    public BufferedImage getImage() {
        return image;
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.image(image, 0, 0, getWidth(), getHeight(), 0);
        super.parcelDraw(canvas);
    }

}
