package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.scene.dynscene.Node;
import com.niopullus.NioLib.scene.dynscene.Reference;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Owen on 4/10/2016.
 */
public class TileReference extends Reference {

    private ArrayList<BufferedImage> images;
    private double friction;
    private double elasticity;
    private boolean collidable;

    public TileReference(final String name, final int id, BufferedImage image, final double friction, final double elasticity, final boolean collidable, final Tile tile) {
        super(name, id, tile);
        this.images = new ArrayList<BufferedImage>();
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
