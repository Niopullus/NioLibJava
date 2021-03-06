package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.*;
import com.niopullus.NioLib.draw.Canvas;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.scene.dynscene.CollideData;
import com.niopullus.NioLib.scene.dynscene.Collision;
import com.niopullus.NioLib.scene.dynscene.reference.Ref;
import com.niopullus.NioLib.scene.dynscene.reference.TileReference;
import com.niopullus.app.Config;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**Entry to a tilemap
 * It is highly discouraged to create instance variables of sub-classes of this class
 * If this is done, their values will not be saved when the object is uncrushed
 * In order to subvert this, enter data into the data object of this class
 * Created by Owen on 3/23/2016.
 */
public class Tile implements CollideData, Crushable, Parcel {

    private DataTree data;
    private Point tileMapPos;
    private Tilemap tilemap;
    private TileReference reference;
    private int tileType;

    public Tile(final String refName, final DataTree _data) {
        final TileReference ref = refName != null ? Ref.getTileRef(refName) : null;
        data = _data;
        tileMapPos = new Point();
        reference = ref;
        tileType = 0;
    }

    public Tile(final String refName) {
        this(refName, new DataTree());
    }

    public Tile() {
        this("created by clone init");
    }

    public int getTileType() {
        return tileType;
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

    public Picture getImage() {
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

    /**
     * Gets the in-world position of the (x-center, y-center) of
     * the MultiTile
     * @return Position of the given multiTile in real-world coordinates
     */
    public Point getRWPos() {
        final Point result = new Point();
        final int tileSize = tilemap.getTileSize();
        result.x = tileMapPos.x * tileSize;
        result.y = tileMapPos.y * tileSize;
        return result;
    }

    public int getTileSize() {
        if (tilemap != null) {
            return tilemap.getTileSize();
        } else {
            return Config.TILESIZE;
        }
    }

    public void setTilemap(final Tilemap _tilemap) {
        tilemap = _tilemap;
    }

    public void setReference(final TileReference _reference) {
        reference = _reference;
    }

    public void setTileMapPos(final Point point) {
        tileMapPos = point;
    }

    public void integrate(final Tile tile) {
        reference = tile.reference;
        tilemap = tile.tilemap;
        tileMapPos = tile.tileMapPos;
        data = tile.data;
    }

    public Tile copy(final DataTree data) {
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
        return copy(data);
    }

    /**
     * Crush Diagram:
     * root {
     *     i - tiletype
     *     i - id
     *     f - data
     * }
     * @see Crushable
     */
    public DataTree crush() {
        final DataTree result = new DataTree();
        result.addData(getTileType());
        result.addData(reference.getId());
        result.addData(data);
        return result;
    }

    public Tile uncrush(final DataTree data) {
        final int tiletype = data.getI(0);
        final int id = data.getI(1);
        final List dataFolder = data.getF(2);
        final DataTree tileData = new DataTree(dataFolder);
        final TileReference reference = Ref.getTileRef(id);
        if (reference != null) {
            if (tiletype == 0) {
                final Tile sample = reference.getSample();
                return sample.copy(tileData);
            } else if (tiletype == 1) {
                final MultiTile sample = (MultiTile) reference.getSample();
                return sample.copy(tileData);
            }
        } else {
            complain();
        }
        return null;
    }

    private static void complain() {
        Log.doc("LOADING TILE: UNRECOGNIZED REFERENCE NAME", "NioLib", LogManager.LogType.ERROR);
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

    public void update() {
        //To be overridden
    }

    public void parcelDraw(final Canvas canvas) {
        final int tileSize = getTileSize();
        canvas.o.sketch(getImage(), 0, 0, tileSize, tileSize, 0);
    }

}
