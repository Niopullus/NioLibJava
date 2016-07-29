package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.*;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.Node;
import com.niopullus.NioLib.SignedContainer;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;

/**Keeps track of tile regions
 * Created by Owen on 3/23/2016.
 */
public class Tilemap implements Crushable, Parcel {

    private int tileSize;
    private int width;
    private int height;
    private int z;
    private int regSize;
    private Node world;
    private Scene scene;
    private SignedContainer<TileRegion> map;

    public Tilemap(final Node _world, final int _tileSize, final int _regSize, final int _width, final int _height) {
        final int xRegions;
        final int yRegions;
        tileSize = _tileSize;
        width = _width;
        height = _height;
        regSize = _regSize;
        world = _world;
        xRegions = (int) Math.ceil((double) width / regSize) + 3;
        yRegions = (int) Math.ceil((double) height / regSize) + 3;
        map = new SignedContainer<>(xRegions, yRegions);
    }

    public Tilemap(final int tileSize, final int regSize, final int width, final int height) {
        this(null, tileSize, regSize, width, height);
    }

    public Scene getScene() {
        return scene;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRegSize() {
        return regSize;
    }

    public int getZ() {
        return z;
    }

    /**
     * Precondition: x - [0-(REGSIZE - 1)], y - [0-(REGSIZE - 1)]
     * @param x in Tile coordinates
     * @param y in Tile coordinates
     * @return a Point in Region coordinates
     */
    private Point getPointInRegion(final int x, final int y) {
        final int xinReg = Math.abs(x % regSize);
        final int yinReg = Math.abs(y % regSize);
        return new Point(xinReg, yinReg);
    }

    /**
     * @param x in tile-coordinates
     * @param y in tile-coordinates
     * @return a tile from the map
     */
    public Tile getTile(final int x, final int y) {
        final Point pointInRegion = getPointInRegion(x, y);
        final TileRegion reg = getRegion(x, y);
        if (reg != null) {
            final Tile tile = reg.get(pointInRegion.x, pointInRegion.y);
            if (tile instanceof MultiTilePart) {
                final MultiTilePart multiTilePart = (MultiTilePart) tile;
                return multiTilePart.get();
            }
            return tile;
        }
        return null;
    }

    /**
     * Gets a Tile in World coordinates
     * @param x in World coordinates
     * @param y in World coordinates
     * @return the requested Tile
     */
    public Tile getTileRC(final int x, final int y) {
        final TileRegion region = getRegion(x, y);
        Tile tile = null;
        if (region != null) {
            final Point pointInRegion = getPointInRegion(x, y);
            tile = region.get(pointInRegion.x, pointInRegion.y);
        }
        return tile;
    }

    private TileRegion getRegion(final int x, final int y) {
        int xReg = x / regSize;
        int yReg = y / regSize;
        if (x < 0) {
            xReg--;
        }
        if (y < 0) {
            yReg--;
        }
        return map.get(xReg, yReg);
    }

    public Tile ogetTile(final int x, final int y) {
        final Point pointInRegion = getPointInRegion(x, y);
        final TileRegion reg = getRegion(x, y);
        if (reg != null) {
            return reg.get(pointInRegion.x, pointInRegion.y);
        } else {
            return null;
        }
    }

    public void setScene(final Scene _scene) {
        scene = _scene;
    }

    public void setZ(final int _z) {
        z = _z;
    }

    private void setRegion(final TileRegion reg, final int x, final int y) {
        map.set(x, y, reg);
    }

    public void setWorld(final Node _world) {
        world = _world;
    }

    /**
     * @param tile is the tile to be placed into the tilemap
     * @param x is the x tile coordinate
     * @param y is the y tile coordainte
     */
    public void setTile(final Tile tile, final int x, final int y) {
        int xReg = x / regSize;
        int yReg = y / regSize;
        final Point pointInRegion = getPointInRegion(x, y);
        TileRegion reg;
        if (x < 0) {
            xReg--;
        }
        if (y < 0) {
            yReg--;
        }
        reg = map.get(xReg, yReg);
        if (reg == null) {
            map.set(xReg, yReg, new TileRegion(this));
            reg = map.get(xReg, yReg);
        }
        reg.set(tile, pointInRegion.x, pointInRegion.y);
        if (tile != null) {
            tile.setTileMapPos(new Point(x, y));
            tile.setTilemap(this);
            if (tile instanceof MultiTilePart) {
                MultiTilePart multiTilePart = (MultiTilePart) tile;
                MultiTile multiTile = multiTilePart.get();
                multiTile.setTilemap(this);
            }
        }
    }

    public void setMultiTile(final MultiTile multiTile, final int x, final int y) {
        for (int i = 0; i < multiTile.getWidth(); i++) {
            for (int j = 0; j < multiTile.getHeight(); j++) {
                if (multiTile.getImage(i, j) != null) {
                    setTile(new MultiTilePart(multiTile, i, j), x + i, y + j);
                }
            }
        }
    }

    /**
     * Only to be called after uncrushing the tilemap
     */
    private void expandMultiTiles() {
        for (int i = -map.getWidth(); i < map.getWidth(); i++) {
            for (int j = -map.getHeight(); j < map.getHeight(); j++) {
                final TileRegion region = map.get(i, j);
                if (region != null) {
                    for (int k = 0; k < regSize; k++) {
                        for (int l = 0; l < regSize; l++) {
                            final Tile tile = region.get(k, l);
                            if (tile != null && tile instanceof MultiTile) {
                                final MultiTile multiTile = (MultiTile) tile;
                                final Point anchor = multiTile.getAnchor();
                                final int xInReg = i < 0 ? (regSize - k) : k;
                                final int yInReg = j < 0 ? (regSize - l) : l;
                                region.set(null, k, l);
                                setMultiTile(multiTile, i * regSize + xInReg - anchor.x, j * regSize + yInReg - anchor.y);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Converts a Point from World coordinates to Tile coordinates
     * @param x in World coordinates
     * @param y in World coordinates
     * @return a Point in Tile coordinates
     */
    public Point convertPointToTileLoc(final int x, final int y) {
        final int convertedX = ((int) Math.floor((double) x / tileSize));
        final int convertedY = ((int) Math.floor((double) y / tileSize));
        return new Point(convertedX, convertedY);
    }

    public int convertLengthToTileLength(final int length) {
        return (int) Math.floor((double) length / tileSize);
    }

    public void parcelDraw(final Canvas canvas) {
        final int xMin = (int) Math.floor((double) -world.getX() / tileSize);
        final int xMax = (int) Math.ceil((double) (-world.getX() + Main.Width()) / tileSize) + 1;
        final int yMin = (int) Math.floor((double) -world.getY() / tileSize);
        final int yMax = (int) Math.ceil((double) (-world.getY() + Main.Height()) / tileSize) + 1;
        for (int i = xMin; i < xMax ; i++) {
            for (int j = yMin; j < yMax; j++) {
                final Tile tile = ogetTile(i, j);
                if (tile != null && tile.getImage() != null) {
                    final int x = i * tileSize + world.getX();
                    final int y = j * tileSize + world.getY();
                    canvas.o.parcel(tile, x, y, 0, 0);
                }
            }
        }
    }

    /**
     * Fills tiles from a point outward
     * @param x of the bottom left point of where to fill
     * @param y of the bottom left point of where to fill
     * @param width of the rectangle to fill
     * @param height of the rectangle to fill
     * @param tile to fill
     */
    public void fillTiles(final int x, final int y, final int width, final int height, final Tile tile) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                setTile(tile.copy(), i, j);
            }
        }
    }

    /**
     * Crush Diagram:
     * root {
     *     i - region size
     *     i - width
     *     i - height
     *     f - regionBundle {
     *         i - x
     *         i - y
     *         f - region
     *     }
     *     ...
     * }
     * @see Crushable
     */
    public DataTree crush() {
         final DataTree data = new DataTree();
         data.addData(regSize);
         data.addData(width);
         data.addData(height);
         for (int i = -map.getWidth(); i < map.getWidth(); i++) {
             for (int j = -map.getHeight(); j < map.getHeight(); j++) {
                 final TileRegion reg = map.get(i, j);
                 if (reg != null) {
                     final int dir = data.addFolder();
                     data.addData(i, dir);
                     data.addData(j, dir);
                     data.addData(reg, dir);
                 }
             }
        }
        return data;
    }

     public static Tilemap uncrush(final DataTree data, final Node world, final int tileSize) {
         final int regSize = data.getI(0);
         final int width = data.getI(1);
         final int height = data.getI(2);
         final int dataSize = data.getSize();
         final Tilemap map = new Tilemap(tileSize, regSize, width, height);
         map.setZ(world.getZ());
         map.setWorld(world);
         for (int i = 3; i < dataSize; i++) {
             final int x = data.getI(i, 0);
             final int y = data.getI(i, 1);
             final DataTree regionData = new DataTree(data.getF(i, 2));
             final Point pos = new Point(x * regSize + (x < 0 ? -1 : 0), y * regSize + (y < 0 ? -1 : 0));
             final TileRegion region = TileRegion.uncrush(regionData, map, pos);
             map.setRegion(region, x, y);
         }
         map.expandMultiTiles();
         return map;
     }

}
