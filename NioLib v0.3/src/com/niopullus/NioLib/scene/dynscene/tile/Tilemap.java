package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.DataPath;
import com.niopullus.NioLib.DataTree;
import com.niopullus.NioLib.Draw;
import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.dynscene.DynamicScene;
import com.niopullus.NioLib.scene.dynscene.Node;
import com.niopullus.NioLib.scene.dynscene.tile.Tile;
import com.niopullus.NioLib.utilities.SignedContainer;
import com.niopullus.app.Config;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Owen on 3/23/2016.
 */
public class Tilemap implements Serializable {

    private SignedContainer<TileRegion> map;
    private int tileSize;
    private int width;
    private int height;
    private DynamicScene dynamicScene;
    private Node world;
    private int z;
    private int regSize;

    public Tilemap(int tileSize, int width, int height, int regSize) {
        this.tileSize = tileSize;
        this.width = width;
        this.height = height;
        this.regSize = regSize;
        int xRegions = (int) Math.ceil((double) width / this.regSize) + 3;
        int yRegions = (int) Math.ceil((double) height / this.regSize) + 3;
        this.map = new SignedContainer<TileRegion>(xRegions, yRegions);
    }

    public void setTile(Tile t, int x, int y) {
        int xReg = x / this.regSize;
        int yReg = y / this.regSize;
        int xinReg = Math.abs(x % this.regSize);
        int yinReg = Math.abs(y % this.regSize);
        if (x < 0) {
            xReg--;
        }
        if (y < 0) {
            yReg--;
        }
        TileRegion reg = this.map.get(xReg, yReg);
        if (reg == null) {
            this.map.set(xReg, yReg, new TileRegion(this.regSize));
            reg = this.map.get(xReg, yReg);
        }
        reg.set(t, xinReg, yinReg);
        if (t != null) {
            t.setTileMapPos(new Point(x, y));
            t.setRwPos(new Point(x * this.tileSize, y * this.tileSize));
            t.setTilemap(this);
            if (t instanceof MultiTilePart) {
                ((MultiTilePart) t).get().addPart((MultiTilePart) t);
                ((MultiTilePart) t).get().setTilemap(this);
            }
        }
    }

    private TileRegion getRegion(int x, int y) {
        int xReg = x / this.regSize;
        int yReg = y / this.regSize;
        if (x < 0) {
            xReg--;
        }
        if (y < 0) {
            yReg--;
        }
        return this.map.get(xReg, yReg);
    }

    public Tile getTile(int x, int y) {
        int xinReg = Math.abs(x % this.regSize);
        int yinReg = Math.abs(y % this.regSize);
        TileRegion reg = this.getRegion(x, y);
        if (reg != null) {
            Tile tile = reg.get(xinReg, yinReg);
            if (this.map.isValidLoc(x / this.regSize, y / this.regSize)) {
                if (tile instanceof MultiTilePart) {
                    return ((MultiTilePart) tile).get();
                } else {
                    return tile;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Tile getOTile(int x, int y) {
        int xReg = x / this.regSize;
        int yReg = y / this.regSize;
        int xinReg = Math.abs(x % this.regSize);
        int yinReg = Math.abs(y % this.regSize);
        if (x < 0) {
            xReg--;
        }
        if (y < 0) {
            yReg--;
        }
        TileRegion reg = this.map.get(xReg, yReg);
        if (reg != null) {
            Tile tile = reg.get(xinReg, yinReg);
            return tile;
        } else {
            return null;
        }
    }

    public void setMultiTile(MultiTile tile, int x, int y) {
        int part = 0;
        TileRegion reg = this.getRegion(x, y);
        reg.addMultiTile(tile);
        tile.setPrimePosition(new Point(x, y));
        for (int j = y; j < y + tile.getHeight(); j++) {
            for (int i = x; i < x + tile.getWidth(); i++) {
                this.setTile(new MultiTilePart(tile, part), i, j);
                part++;
            }
        }
    }

    public Tile getTileRC(int x, int y) {
        Point p = convertPointToTileLoc(x, y);
        if (this.map.isValidLoc(p.x / this.regSize, p.y / this.regSize)) {
            return getTile(p.x, p.y);
        } else {
            return null;
        }
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public Point convertPointToTileLoc(int x, int y) {
        return new Point(((int) Math.floor((double) x / this.tileSize)), ((int) Math.floor((double) y / this.tileSize)));
    }

    public int convertLengthToTileLength(int length) {
        return (int) Math.floor((double) length / this.tileSize);
    }

    public void draw() {
        int xMin = (int) Math.floor((double) -this.world.getX() / this.tileSize);
        int xMax = (int) Math.ceil((double) (-this.world.getX() + Main.Width()) / this.tileSize) + 1;
        int yMin = (int) Math.floor((double) -this.world.getY() / this.tileSize);
        int yMax = (int) Math.ceil((double) (-this.world.getY() + Main.Height()) / this.tileSize) + 1;
        for (int i = xMin; i < xMax ; i++) {
            for (int j = yMin; j < yMax; j++) {
                Tile tile = this.getOTile(i, j);
                if (tile != null && tile.getImage() != null) {
                    Draw.image(i * this.tileSize + this.world.getX(), Main.Height() - ((j + 1) * this.tileSize) - this.world.getY(), this.z, this.tileSize, this.tileSize, tile.getImage());
                }
            }
        }
    }

    public void fillTiles(int x, int y, int width, int height, Tile t) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                this.setTile(t.clone(), i, j);
            }
        }
    }

    public void setDynamicScene(DynamicScene dynamicScene) {
        this.dynamicScene = dynamicScene;
        this.world = dynamicScene.getWorld();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setZ(int z) {
        this.z = z;
    }

    private void setRegion(TileRegion reg, int x, int y) {
        this.map.set(x, y, reg);
    }

    public void setWorld(Node world) {
        this.world = world;
    }

    public DynamicScene getDynamicScene() {
        return this.dynamicScene;
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
         DataTree data = new DataTree();
         data.addData(this.regSize);
         data.addData(this.width);
         data.addData(this.height);
         for (int i = -this.map.getWidth(); i < this.map.getWidth(); i++) {
             for (int j = -this.map.getHeight(); j < this.map.getHeight(); j++) {
                 TileRegion reg = this.map.get(i, j);
                 if (reg != null) {
                     int dir1 = data.addFolder(); //REG FOLDER
                     data.addData(i, new DataPath(new int[]{dir1}));
                     data.addData(j, new DataPath(new int[]{dir1}));
                     int dir2 = data.addFolder(new DataPath(new int[]{dir1})); //TILES FOLDER
                     for (int x = 0; x < reg.getSize(); x++) {
                         for (int y = 0; y < reg.getSize(); y++) {
                             Tile tile = reg.get(x, y);
                             if (tile != null && !(tile instanceof MultiTilePart)) {
                                 int dir3 = data.addFolder(new DataPath(new int[]{dir1, dir2})); //INDIVIDUAL TILE FOLDER
                                 data.addData(0, new DataPath(new int[]{dir1, dir2, dir3}));
                                 data.addData(x, new DataPath(new int[]{dir1, dir2, dir3}));
                                 data.addData(y, new DataPath(new int[]{dir1, dir2, dir3}));
                                 data.addData((ArrayList) tile.compress().get(), new DataPath(new int[]{dir1, dir2, dir3}));
                             }
                         }
                     }
                     for (int z = 0; z < reg.getMultiTileQuant(); z++) {
                         int dir3 = data.addFolder(new DataPath(new int[]{dir1, dir2}));
                         MultiTile mt = reg.getMultiTile(z);
                         data.addData(1, new DataPath(new int[]{dir1, dir2, dir3}));
                         data.addData(mt.getPrimePosition().x, new DataPath(new int[]{dir1, dir2, dir3}));
                         data.addData(mt.getPrimePosition().y, new DataPath(new int[]{dir1, dir2, dir3}));
                         data.addData((ArrayList) mt.compress().get(), new DataPath(new int[]{dir1, dir2, dir3}));
                     }
                 }
             }
        }
        return data;
     }

     public static Tilemap decompress(DataTree data, int tileSize) {
         int regSize = (Integer) data.get(new DataPath(new int[]{0}));
         int width = (Integer) data.get(new DataPath(new int[]{1}));
         int height = (Integer) data.get(new DataPath(new int[]{2}));
         int dataSize = data.getSize();
         Tilemap map = new Tilemap(tileSize, width, height, regSize);
         ArrayList<MultiTile> multiTiles = new ArrayList<MultiTile>();
         for (int i = 3; i < dataSize; i++) {
             int regx = (Integer) data.get(new DataPath(new int[]{i, 0}));
             int regy = (Integer) data.get(new DataPath(new int[]{i, 1}));
             TileRegion reg = new TileRegion(regSize);
             for (int j = 0; j < data.getSize(new DataPath(new int[]{i, 2})); j++) {
                 int tiletype = (Integer) data.get(new DataPath(new int[]{i, 2, j, 0}));
                 int x = (Integer) data.get(new DataPath(new int[]{i, 2, j, 1}));
                 int y = (Integer) data.get(new DataPath(new int[]{i, 2, j, 2}));
                 int id = (Integer) data.get(new DataPath(new int[]{i, 2, j, 3, 0}));
                 if (tiletype == 0) {
                     Tile tile = null;
                     tile = TileReference.getRef(id).getSample().clone();
                     DataTree tileData = new DataTree();
                     tileData.setData((ArrayList) data.get(new DataPath(new int[]{i, 2, j, 3, 1})));
                     tile.setData(tileData);
                     reg.set(tile, x, y);
                 } else if (tiletype == 1) {
                     MultiTile mt = (MultiTile) TileReference.getRef(id).getSample().clone();
                     mt.setPrimePosition(new Point(x, y));
                     DataTree mtData = new DataTree((ArrayList) data.get(new DataPath(new int[]{i, 2, j, 3, 1})));
                     multiTiles.add(mt);
                 }
             }
             map.setRegion(reg, regx, regy);
         }
         for (MultiTile mt : multiTiles) {
             map.setMultiTile(mt, mt.getPrimePosition().x, mt.getPrimePosition().y);
         }
         return map;
     }

}
