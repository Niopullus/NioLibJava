package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.draw.Draw;

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

    public void draw(final int x, final int y, final int z, final Draw.DrawMode mode) {
        Draw.mode(mode).image(image, x, y, getWidth(), getHeight(), z);
    }

}
