package com.niopullus.NioLib.scene.dynscene.tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**Reference for a MultiTile
 * Created by Owen on 4/11/2016.
 */
public class MultiTileReference extends TileReference {

    private int width;
    private int height;
    private ArrayList<ArrayList<BufferedImage>> images;

    public MultiTileReference(final String name, final int id, final ArrayList<BufferedImage> images, final double friction, final double elasticity, final boolean collidable, final int width, final int height, final MultiTile tile) {
        super(name, id, null, friction, elasticity, collidable, tile);
        this.width = width;
        this.height = height;
        this.images = new ArrayList<ArrayList<BufferedImage>>();
        this.images.add(images);
    }

    public int getWidth() { //Gets the width of MultiTile in terms of tiles
        return width;
    }

    public int getHeight() { //Gets the height of MultiTile in terms of tiles
        return height;
    }

    public BufferedImage getImage(final int index) {
        return getImage(0, index);
    }

    public BufferedImage getImage(final int set, final int index) {
        final ArrayList<BufferedImage> imageSet = images.get(set);
        return imageSet.get(index);
    }

    public void addSet(final ArrayList<BufferedImage> set) {
        images.add(set);
    }

}
