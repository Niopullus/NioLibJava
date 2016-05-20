package com.niopullus.NioLib.scene.dynscene.tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**Reference pertaining to a Multi Tile
 * Created by Owen on 4/11/2016.
 */
public class MultiTileReference extends TileReference {

    private int width;
    private int height;
    private ArrayList<ArrayList<BufferedImage>> images;

    public MultiTileReference(final MultiTileP pack) {
        super(pack.name, pack.id, null, pack.friction, pack.elasticity, pack.collidable, pack.sample);
        this.width = pack.width;
        this.height = pack.height;
        this.images = new ArrayList<>();
        this.images.add(pack.images);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage(final int index) {
        final List<BufferedImage> imageSet = images.get(0);
        return imageSet.get(index);
    }

    public BufferedImage getImage(final int set, final int index) {
        final ArrayList<BufferedImage> imageSet = images.get(set);
        return imageSet.get(index);
    }

    public void setImages(ArrayList<ArrayList<BufferedImage>> images) {
        this.images = images;
    }

    public void addSet(final ArrayList<BufferedImage> newSet) {
        images.add(newSet);
    }

    public class MultiTileP {

        public String name;
        public int id;
        public ArrayList<BufferedImage> images;
        public double friction;
        public double elasticity;
        public boolean collidable;
        public int width;
        public int height;
        public MultiTile sample;

    }

}
