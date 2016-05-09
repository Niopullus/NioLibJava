package com.niopullus.NioLib.scene.dynscene.tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Owen on 4/11/2016.
 */
public class MultiTileReference extends TileReference {

    private int width;
    private int height;
    private ArrayList<ArrayList<BufferedImage>> images;

    public MultiTileReference(final String name, final int id, ArrayList<BufferedImage> images, final double friction, final double elasticity, final boolean collidable, final int width, final int height, final Tile tile) {
        super(name, id, null, friction, elasticity, collidable, tile);
        this.width = width;
        this.height = height;
        this.images = new ArrayList<ArrayList<BufferedImage>>();
        this.images.add(images);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage(final int index) {
        final ArrayList<BufferedImage> imageSet = images.get(0);
        return imageSet.get(index);
    }

    public BufferedImage getImage(final int set, final int index) {
        final ArrayList<BufferedImage> imageSet = images.get(set);
        return imageSet.get(index);
    }

    public void addSet(final ArrayList<BufferedImage> newSet) {
        images.add(newSet);
    }

}
