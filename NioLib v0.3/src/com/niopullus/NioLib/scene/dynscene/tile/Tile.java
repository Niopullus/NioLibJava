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
public class Tile implements CollideData {

    private DataTree data;
    private Point tileMapPos;
    private Tilemap tilemap;
    private TileReference reference;

    public Tile(final String refName, final DataTree data) {
        TileReference ref = null;
        this.data = data;
        this.tileMapPos = new Point();
        if (refName != null) {
            ref = TileReference.getTileRef(refName);
        }
        this.reference = ref;
    }

    public Tile(final String refName) {
        this(refName, new DataTree());
    }

    private Tile() { //For cloning
        this("unreferenced (clone in progress)");
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

    public void setTilemap(final Tilemap tilemap) {
        this.tilemap = tilemap;
    }

    public void setReference(final TileReference reference) {
        this.reference = reference;
    }

    public void setTileMapPos(final Point point) {
        tileMapPos = point;
    }

    public Tile clone(final DataTree data) {
        try {
            final Class<?> tileClass = getClass();
            final Tile tile = (Tile) tileClass.newInstance();
            tile.reference = reference;
            tile.tilemap = tilemap;
            tile.tileMapPos = tileMapPos;
            tile.data = data;
            return tile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Tile copy() {
        return clone(data);
    }

    public DataTree compress() { //Converts the data into a DataTree
        final DataTree result = new DataTree();
        result.addData(this.reference.getId());
        result.addData((ArrayList) this.data.get());
        return result;
    }

    public void clickedOn() {
        //To be overridden
    }

    public void victimCollision(Collision collision) {
        //To be overridden
    }

    public final void causerCollision(Collision collision) {
        //Blank Implementation
    }

}
