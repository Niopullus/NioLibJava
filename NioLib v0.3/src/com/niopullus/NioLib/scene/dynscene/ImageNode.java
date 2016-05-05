package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Owen on 3/19/2016.
 */
public class ImageNode extends Node {

    private BufferedImage image;

    public ImageNode(String name, String imageDir) {
        this(name, imageDir, Utilities.loadImage(imageDir).getWidth(), Utilities.loadImage(imageDir).getHeight());
    }

    public ImageNode(String name, String imageDir, int width, int height) {
        super(name, width, height);
        this.image = Utilities.loadImage(imageDir);
        this.setOWidth(this.image.getWidth());
        this.setOHeight(this.image.getHeight());
        this.csetXScale((double) width / this.getOWidth());
        this.csetYScale((double) height / this.getOHeight());
    }

    public void draw() {
        Draw.image(this.getTMinX(), Main.Height() - this.getTY() - this.getHeight(), this.getTMaxX(), Main.Height() - this.getTY(), 0, 0, this.image.getWidth(), this.image.getHeight(), this.getZ(), this.getAngle(), this.image);
    }

}
