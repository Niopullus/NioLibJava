package com.niopullus.NioLib.scene.dynscene.reference;

import com.niopullus.NioLib.Picture;
import com.niopullus.NioLib.scene.dynscene.tile.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**Stores information about a tile
 * that is common throughout tiles of a certain type
 * Necessary for use of a custom Tile
 * Created by Owen on 4/10/2016.
 */
public class TileReference extends Reference {

    private boolean collidable;
    private double friction;
    private double elasticity;
    private ArrayList<Picture> images;

    public TileReference(final TileReferencePack pack) {
        super(pack.name, pack.id, pack.sample);
        images = new ArrayList<>();
        friction = pack.friction;
        elasticity = pack.elasticity;
        collidable = pack.collidable;
        images.add(pack.image);
    }

    public TileReference() {
        this(new TileReferencePack());
    }

    public double getElasticity() {
        return elasticity;
    }

    public double getFriction() {
        return friction;
    }

    public Picture getImage() {
        return images.get(0);
    }

    public Picture getImage(final int set) {
        return images.get(set);
    }

    public boolean getCollidable() {
        return this.collidable;
    }

    /*
    Not to be used in a mutable context. If one wishes mutate the sample,
    One should use getSampleCopy()
     */
    public Tile getSample() {
        return (Tile) super.getSample();
    }

    public Tile getSampleCopy() {
        return getSample().copy();
    }

    public void addImage(final Picture image) {
        images.add(image);
    }

    public static class TileReferencePack {

        public String name;
        public int id;
        public Picture image;
        public double friction;
        public double elasticity;
        public boolean collidable;
        public Tile sample;

    }

}
