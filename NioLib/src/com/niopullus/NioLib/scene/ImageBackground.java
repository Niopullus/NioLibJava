package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.draw.Canvas;

import java.awt.image.BufferedImage;

/**Displays an image of a particular width and height
 * Created by Owen on 3/19/2016.
 */
public class ImageBackground extends Background {

    private BufferedImage image;

    public ImageBackground(final BufferedImage image, final int width, final int height) {
        super(width, height);
        this.image = image;
    }

    public ImageBackground(final BufferedImage image, final double scaleFactor) {
        this(image, (int) (image.getWidth() * scaleFactor), (int) (image.getHeight() * scaleFactor));
    }

    public ImageBackground(final BufferedImage image) {
        this(image, 1);
    }

    public void parcelDraw(final Canvas canvas) {
        canvas.o.image(image, 0, 0, getWidth(), getHeight(), 0);
    }

}
