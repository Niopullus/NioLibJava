package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.DataPath;
import com.niopullus.NioLib.DataTree;
import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.DynamicScene;
import com.niopullus.NioLib.scene.dynscene.Node;
import com.niopullus.NioLib.utilities.SignedContainer;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**Keeps track of tile regions
 * Created by Owen on 3/23/2016.
 */
public class Tilemap implements Serializable {

    private int tileSize;
    private int width;
    private int height;
    private int z;
    private int regSize;
    private SignedContainer<TileRegion> map;
    private Scene scene;
    private Node world;

    private Tilemap(final Scene scene, final Node world, final int tileSize, final int regSize, final int width, final int height, final int z) {
        this.tileSize = tileSize;
        this.width = width;
        this.height = height;
        this.regSize = regSize;
        this.world = world;
        this.scene = scene;
        this.z = z;
        int xRegions = (int) Math.ceil((double) width / this.regSize) + 3;
        int yRegions = (int) Math.ceil((double) height / this.regSize) + 3;
        this.map = new SignedContainer<TileRegion>(xRegions, yRegions);
    }

    public Tilemap(final DynamicScene scene, final int tileSize, final int regSize, final int width, final int height, final int z) {
        this(scene, scene.getWorld(), tileSize, regSize, width, height, z);
    }

    public Tilemap(final Scene scene, final int tileSize, final int regSize, final int width, final int height, final int z) {
        this(scene, new Node(), tileSize, regSize, width, height, z);
    }

    public Tilemap(final Node world, final int tileSize, final int regSize, final int width, final int height, final int z) {
        this(null, world, tileSize, regSize, width, height, z);
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

    private Point getPointInRegion(final int x, final int y) {
        final int xinReg = Math.abs(x % regSize);
        final int yinReg = Math.abs(y % regSize);
        return new Point(xinReg, yinReg);
    }

    public Tile getTile(final int x, final int y) { //Gets a tile from the map in tile coordinates
        final Point pointInRegion = getPointInRegion(x, y);
        final TileRegion reg = getRegion(x, y);
        if (reg != null) {
            final Tile tile = reg.get(pointInRegion.x, pointInRegion.y);
            if (tile instanceof MultiTilePart) {
                MultiTilePart multiTilePart = (MultiTilePart) tile;
                return multiTilePart.get();
            }
            return tile;
        }
        return null;
    }

    public Tile getTileRC(final int x, final int y) { //Gets a tile in world coordinates
        final TileRegion region = getRegion(x, y);
        Tile tile = null;
        if (region != null) {
            final Point pointInRegion = getPointInRegion(x, y);
            tile = region.get(pointInRegion.x, pointInRegion.y);
        }
        return tile;
    }

    private TileRegion getRegion(final int x, final int y) { //Gets a region from the map in world coordinates
        int xReg = x / regSize;
        int yReg = y / regSize;
        if (x < 0) {
            xReg--;
        }
        if (y < 0) {
            yReg--;
        }
        return this.map.get(xReg, yReg);
    }

    public void setTile(final Tile tile, final int x, final int y) { //Sets a tile in tile coordinates
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
            map.set(xReg, yReg, new TileRegion(regSize));
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

    public Tile ogetTile(final int x, final int y) { //Gets a tile and ignores the MultiTile filter in tile coordinates
        final Point pointInRegion = getPointInRegion(x, y);
        final TileRegion reg = getRegion(x, y);
        if (reg != null) {
            return reg.get(pointInRegion.x, pointInRegion.y);
        } else {
            return null;
        }
    }

    public void setMultiTile(final MultiTile tile, final int x, final int y) { //Sets a MultiTile in tile coordinates
        int part = 0;
        final TileRegion reg = getRegion(x, y);
        reg.addMultiTile(tile);
        tile.setRefTilePoint(new Point(x, y));
        tile.setTilemap(this);
        for (int j = y; j < y + tile.getHeight(); j++) {
            for (int i = x; i < x + tile.getWidth(); i++) {
                setTile(new MultiTilePart(tile, part), i, j);
                part++;
            }
        }
    }

    public Point convertPointToTileLoc(final int x, final int y) {
        final int convertedX = ((int) Math.floor((double) x / tileSize));
        final int convertedY = ((int) Math.floor((double) y / tileSize));
        return new Point(convertedX, convertedY);
    }

    public int convertLengthToTileLength(final int length) {
        return (int) Math.floor((double) length / tileSize);
    }

    public void draw() {
        final int xMin = (int) Math.floor((double) -world.getX() / tileSize);
        final int xMax = (int) Math.ceil((double) (-world.getX() + Main.Width()) / tileSize) + 1;
        final int yMin = (int) Math.floor((double) -world.getY() / tileSize);
        final int yMax = (int) Math.ceil((double) (-world.getY() + Main.Height()) / tileSize) + 1;
        for (int i = xMin; i < xMax ; i++) {
            for (int j = yMin; j < yMax; j++) {
                final Tile tile = ogetTile(i, j);
                if (tile != null && tile.getImage() != null) {
                    final int x = i * tileSize + world.getX();
                    final int y = Main.Height() - ((j + 1) * tileSize) - world.getY();
                    Draw.image(x, y, z, tileSize, tileSize, tile.getImage());
                }
            }
        }
    }

    public void fillTiles(final int x, final int y, final int width, final int height, final Tile t) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                setTile(t.clone(), i, j);
            }
        }
    }

    private void setRegion(final TileRegion reg, final int x, final int y) {
        map.set(x, y, reg);
    }

     public DataTree compress() {
     //DATA TREE STRUCTURE:
     // ROOT -[
     //         Integer (Reg Size)
     //         Integer (Width)
     //         Integer (Height)
     //         Region - [
     //                      Integer regx
     //                      Integer regy
     //                      Tile Data - [
     //                              Integer (0 if single, 1 if multi)
     //                              Integer (x)
     //                              Integer (y)
     //                              Tile - [
     //                                      Integer (id)
     //                                      Data - [ STORED TILE DATA ]
     //         Region - [...]
     //         ...
         final DataTree data = new DataTree();
         data.addData(regSize);
         data.addData(width);
         data.addData(height);
         for (int i = -map.getWidth(); i < map.getWidth(); i++) {
             for (int j = -map.getHeight(); j < map.getHeight(); j++) {
                 final TileRegion reg = map.get(i, j);
                 if (reg != null) {
                     final int dir1 = data.addFolder(); //REG FOLDER
                     final int dir2;
                     data.addData(i, new DataPath(new int[]{dir1}));
                     data.addData(j, new DataPath(new int[]{dir1}));
                     dir2 = data.addFolder(new DataPath(new int[]{dir1})); //TILES FOLDER
                     for (int x = 0; x < reg.getSize(); x++) {
                         for (int y = 0; y < reg.getSize(); y++) {
                             final Tile tile = reg.get(x, y);
                             if (tile != null && !(tile instanceof MultiTilePart)) {
                                 final int dir3 = data.addFolder(new DataPath(new int[]{dir1, dir2})); //INDIVIDUAL TILE FOLDER
                                 data.addData(0, new DataPath(new int[]{dir1, dir2, dir3}));
                                 data.addData(x, new DataPath(new int[]{dir1, dir2, dir3}));
                                 data.addData(y, new DataPath(new int[]{dir1, dir2, dir3}));
                                 data.addData((ArrayList) tile.compress().get(), new DataPath(new int[]{dir1, dir2, dir3}));
                             }
                         }
                     }
                     for (int z = 0; z < reg.getMultiTileQuant(); z++) {
                         final int dir3 = data.addFolder(new DataPath(new int[]{dir1, dir2}));
                         final MultiTile mt = reg.getMultiTile(z);
                         data.addData(1, new DataPath(new int[]{dir1, dir2, dir3}));
                         data.addData(mt.getRefTilePoint().x, new DataPath(new int[]{dir1, dir2, dir3}));
                         data.addData(mt.getRefTilePoint().y, new DataPath(new int[]{dir1, dir2, dir3}));
                         data.addData((ArrayList) mt.compress().get(), new DataPath(new int[]{dir1, dir2, dir3}));
                     }
                 }
             }
        }
        return data;
     }

     public static Tilemap decompress(final Node world, final DataTree data, final int tileSize) {
         final int regSize = (Integer) data.get(new DataPath(new int[]{0}));
         final int width = (Integer) data.get(new DataPath(new int[]{1}));
         final int height = (Integer) data.get(new DataPath(new int[]{2}));
         final int dataSize = data.getSize();
         final Tilemap map = new Tilemap(world, tileSize, regSize, width, height, world.getZ());
         final ArrayList<MultiTile> multiTiles = new ArrayList<MultiTile>();
         for (int i = 3; i < dataSize; i++) {
             final int regx = (Integer) data.get(new DataPath(new int[]{i, 0}));
             final int regy = (Integer) data.get(new DataPath(new int[]{i, 1}));
             final TileRegion reg = new TileRegion(regSize);
             for (int j = 0; j < data.getSize(new DataPath(new int[]{i, 2})); j++) {
                 final int tiletype = (Integer) data.get(new DataPath(new int[]{i, 2, j, 0}));
                 final int x = (Integer) data.get(new DataPath(new int[]{i, 2, j, 1}));
                 final int y = (Integer) data.get(new DataPath(new int[]{i, 2, j, 2}));
                 final int id = (Integer) data.get(new DataPath(new int[]{i, 2, j, 3, 0}));
                 final ArrayList dataFolder = (ArrayList) data.get(new DataPath(new int[]{i, 2, j, 3, 1}));
                 final DataTree tileData = new DataTree(dataFolder);
                 final TileReference reference = TileReference.getRef(id);
                 if (reference != null) {
                     if (tiletype == 0) {
                         final Tile sample = reference.getSample();
                         final Tile tile = sample.clone(tileData);
                         reg.set(tile, x, y);
                     } else if (tiletype == 1) {
                         final MultiTile sample = (MultiTile) reference.getSample();
                         final MultiTile multiTile = (MultiTile) sample.clone(tileData);
                         multiTile.setRefTilePoint(new Point(x, y));
                         multiTiles.add(multiTile);
                     }
                 } else {
                    System.out.println("ERROR LOADING TILE: UNRECOGNIZED REFERENCE NAME");
                 }
             }
             map.setRegion(reg, regx, regy);
         }
         for (MultiTile multiTile : multiTiles) {
             final Point multiTilePoint = multiTile.getRefTilePoint();
             map.setMultiTile(multiTile, multiTilePoint.x, multiTilePoint.y);
         }
         return map;
     }

}
