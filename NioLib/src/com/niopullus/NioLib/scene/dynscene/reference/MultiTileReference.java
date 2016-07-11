package com.niopullus.NioLib.scene.dynscene.reference;

import com.niopullus.NioLib.Log;
import com.niopullus.NioLib.LogManager;
import com.niopullus.NioLib.Picture;
import com.niopullus.NioLib.scene.dynscene.tile.MultiTile;
import com.niopullus.NioLib.MDArrayList;

import java.awt.*;
import java.awt.image.BufferedImage;

/**Reference pertaining to a Multi Tile
 * Created by Owen on 4/11/2016.
 */
public class MultiTileReference extends TileReference {

    private int width;
    private int height;
    private MDArrayList<Picture> images; //Dim1: Set, Dim2: x, Dim3: y
    private Point tileAnchor;

    public MultiTileReference(final MultiTileReferencePack pack) {
        super(pack.derive());
        width = pack.images.getSize(0);
        height = pack.images.getSize(1);
        images = new MDArrayList<>(3);
        pack.images.layerCheck(2);
        images.add(pack.images);
        images.restrict(1, width);
        images.restrict(2, height);
        determineAnchor();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Picture getImage(final int x, final int y) {
        return images.get(0, x, y);
    }

    public Picture getImage(final int set, final int x, final int y) {
        return images.get(set, x, y);
    }

    /*
    Not to be used in a mutable context. If one wishes mutate the sample,
    One should use getSampleCopy()
     */
    public MultiTile getSample() {
        return (MultiTile) super.getSample();
    }

    public MultiTile getSampleCopy() {
        return (MultiTile) getSample().copy();
    }

    public Point getAnchor() {
        return tileAnchor;
    }

    public void setImages(final MDArrayList<Picture> _images) {
        images.layerCheck(this.images);
        images = _images;
    }

    public void setAnchorPoint(final Point anchor) {
        tileAnchor = anchor;
    }

    /**
     * Adds a set of images
     * @param newSet 2 dimensional MDArrayList of images
     */
    public void addSet(final MDArrayList<Picture> newSet) {
        newSet.layerCheck(2);
        images.add(newSet);
    }

    private void determineAnchor() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (images.get(i ,j) != null) {
                    tileAnchor = new Point(i, j);
                }
            }
        }
    }

    private void complain() {
        Log.doc("FOUND EMPTY IMAGE MAP FROM MULTITILEREFERENCE", "NioLib", LogManager.LogType.ERROR);
    }

    public static class MultiTileReferencePack {

        public String name;
        public int id;
        public MDArrayList<Picture> images; //2 Dimensional
        public double friction;
        public double elasticity;
        public boolean collidable;
        public MultiTile sample;

        public TileReferencePack derive() {
            final TileReferencePack pack = new TileReferencePack();
            pack.name = name;
            pack.id = id;
            pack.image = null;
            pack.elasticity = elasticity;
            pack.collidable = collidable;
            pack.sample = sample;
            return pack;
        }

    }

}
