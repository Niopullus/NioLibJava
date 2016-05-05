package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.DataPath;
import com.niopullus.NioLib.DataTree;
import com.niopullus.NioLib.scene.dynscene.CollideData;
import com.niopullus.NioLib.scene.dynscene.Collision;
import com.niopullus.NioLib.scene.dynscene.DynamicScene;
import com.niopullus.app.InitScene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Owen on 3/23/2016.
 */
public class Tile implements CollideData, Cloneable, Serializable {

    private transient TileReference reference;
    private DataTree data;
    private Point tileMapPos;
    private Point rwPos;
    private Tilemap tilemap;
    private String refName;

    public Tile(String refName) {
        this.refName = refName;
        this.data = new DataTree();
        if (refName != null) {
            TileReference ref = TileReference.getRef(refName);
            if (ref != null) {
                this.reference = ref;
            }
        }
    }

    @Override
    public double getElasticity() {
        return this.reference.getElasticity();
    }

    @Override
    public double getFriction() {
        return this.reference.getFriction();
    }

    public String getName() {
        return this.reference.getName();
    }

    public int getId() {
        return this.reference.getId();
    }

    public BufferedImage getImage() {
        return this.reference.getImage();
    }

    public TileReference getReference() {
        return this.reference;
    }

    public boolean getCollidable() {
        return this.reference.getCollidable();
    }

    public void setReference(TileReference tileReference) {
        this.reference = tileReference;
    }

    public void clickedOn() {

    }

    public Tile clone() {
        try {
            return (Tile) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public void setData(DataTree dataTree) {
        this.data = dataTree;
    }

    private int getMultiTile() {
        return this instanceof MultiTile ? 1 : 0;
    }

    public DataTree compress() {
        DataTree result = new DataTree();
        result.addData(this.reference.getId());
        result.addData((ArrayList) this.data.get());
        return result;
    }

    public void victimCollision(Collision collision) {

    }

    public void setTileMapPos(Point p) {
        this.tileMapPos = p;
    }

    public void setRwPos(Point p) {
        this.rwPos = p;
    }

    public Point getTileMapPos() {
        return this.tileMapPos;
    }

    public Point getRwPos() {
        return this.rwPos;
    }

    public void setTilemap(Tilemap map) {
        this.tilemap = map;
    }

    public Tilemap getTilemap() {
        return this.tilemap;
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (this.refName != null) {
            TileReference ref = TileReference.getRef(this.refName);
            if (ref != null) {
                this.reference = ref;
            }
        }
    }

}
