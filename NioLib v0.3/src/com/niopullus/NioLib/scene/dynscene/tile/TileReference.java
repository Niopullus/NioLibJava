package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.scene.dynscene.Reference;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**Stores information about a tile
 * that is common throughout tiles of a certain type
 * Created by Owen on 4/10/2016.
 */
public class TileReference extends Reference {

    private boolean collidable;
    private double friction;
    private double elasticity;
    private ArrayList<BufferedImage> images;

    public TileReference(final String name, final int id, BufferedImage image, final double friction, final double elasticity, final boolean collidable, final Tile tile) {
        super(name, id, tile);
        this.images = new ArrayList<>();
        this.friction = friction;
        this.elasticity = elasticity;
        this.collidable = collidable;
        this.images.add(image);
    }

    public double getElasticity() {
        return elasticity;
    }

    public double getFriction() {
        return friction;
    }

    public BufferedImage getImage() {
        return images.get(0);
    }

    public BufferedImage getImage(final int set) {
        return images.get(set);
    }

    public boolean getCollidable() {
        return this.collidable;
    }

    public Tile getSample() {
        return (Tile) super.getSample();
    }

    public void addImage(final BufferedImage image) {
        this.images.add(image);
    }

}
