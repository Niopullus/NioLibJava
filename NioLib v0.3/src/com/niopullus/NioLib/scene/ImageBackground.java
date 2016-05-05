package com.niopullus.NioLib.scene;

import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.image.BufferedImage;

/**
 * Created by Owen on 3/19/2016.
 */
public class ImageBackground extends Background {

    private BufferedImage image;

    public ImageBackground(int x, int y, int width, int height, String imgDir) {
        super(x, y, width, height);
        this.image = Utilities.loadImage(imgDir);
    }

    public ImageBackground(int x, int y, String imgDir) {
        this(x, y, Utilities.loadImage(imgDir).getWidth(), Utilities.loadImage(imgDir).getHeight(), imgDir);
    }

    public void draw() {
        this.draw(this.getX(), this.getY());
    }

    public void draw(int x, int y) {
        Draw.image(x, y, x + this.getWidth(), y + this.getHeight(), 0, 0, this.image.getWidth(), this.image.getHeight(), this.getZ(), this.image);
    }

}
