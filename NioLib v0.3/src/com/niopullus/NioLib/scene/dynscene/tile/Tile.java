package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.DataTree;
import com.niopullus.NioLib.scene.dynscene.CollideData;
import com.niopullus.NioLib.scene.dynscene.Collision;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**Entry to a tilemap
 * Created by Owen on 3/23/2016.
 */
public class Tile implements CollideData, Cloneable {

    private final TileReference reference;
    private final DataTree data;
    private final Point tileMapPos;
    private Tilemap tilemap;

    public Tile(final String refName, final Tilemap tilemap, final DataTree data) {
        this.tilemap = tilemap;
        this.data = data;
        this.tileMapPos = new Point();
        TileReference ref = null;
        if (refName != null) {
            ref = TileReference.getRef(refName);
        }
        this.reference = ref;
    }

    public Tile(final String refName, final Tilemap tilemap) {
        this(refName, tilemap, new DataTree());
    }

    public double getElasticity() {
        return reference.getElasticity();
    }

    public double getFriction() {
        return reference.getFriction();
    }

    public String getName() {
        return reference.getName();
    }

    public int getId() {
        return reference.getId();
    }

    public BufferedImage getImage() {
        return reference.getImage();
    }

    public TileReference getReference() {
        return reference;
    }

    public boolean getCollidable() {
        return reference.getCollidable();
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public Point getTileMapPos() {
        return tileMapPos;
    }

    private boolean getMultiTile() {
        return this instanceof MultiTile;
    }

    public Point getRWPos() {
        final Point result = new Point();
        final int tileSize = tilemap.getTileSize();
        result.x = tileMapPos.x * tileSize;
        result.y = tileMapPos.y * tileSize;
        return result;
    }

    public void setTileMapPos(Point p) {
        tileMapPos.x = p.x;
        tileMapPos.y = p.y;
    }

    public void setTilemap(final Tilemap tilemap) {
        this.tilemap = tilemap;
    }

    public Tile clone() {
        try {
            return (Tile) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public DataTree compress() { //Converts the data into a DataTree
        DataTree result = new DataTree();
        result.addData(this.reference.getId());
        result.addData((ArrayList) this.data.get());
        return result;
    }

    public void clickedOn() {

    }

    public void victimCollision(Collision collision) {

    }

}
