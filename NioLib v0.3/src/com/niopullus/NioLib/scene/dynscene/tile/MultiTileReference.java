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

    public MultiTileReference(String name, int id, ArrayList<BufferedImage> images, double friction, double elasticity, boolean collidable, int width, int height, Tile tile) {
        super(name, id, null, friction, elasticity, collidable, tile);
        this.width = width;
        this.height = height;
        this.images = new ArrayList<ArrayList<BufferedImage>>();
        this.images.add(images);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public BufferedImage getImage(int index) {
        return this.images.get(0).get(index);
    }

    public BufferedImage getImage(int set, int index) {
        return this.images.get(set).get(index);
    }

    public void addSet(ArrayList<BufferedImage> newSet) {
        this.images.add(newSet);
    }

}
