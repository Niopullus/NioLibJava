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

    public TileReference(String name, int id, BufferedImage image, double friction, double elasticity, boolean collidable, Tile tile) {
        super(name, id, tile);
        this.images = new ArrayList<BufferedImage>();
        this.friction = friction;
        this.elasticity = elasticity;
        this.collidable = collidable;
        this.images.add(image);
    }

    public double getElasticity() {
        return this.elasticity;
    }

    public double getFriction() {
        return this.friction;
    }

    public BufferedImage getImage() {
        return this.images.get(0);
    }

    public BufferedImage getImage(int set) {
        return this.images.get(set);
    }

    public boolean getCollidable() {
        return this.collidable;
    }

    public void addImage(BufferedImage image) {
        this.images.add(image);
    }

    public Tile getSample() {
        return (Tile) super.getSample();
    }

}
