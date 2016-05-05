package com.niopullus.NioLib.scene.dynscene.tile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Owen on 4/13/2016.
 */
public class TileRegion implements Serializable {

    private int size;
    private Tile[][] grid;
    private ArrayList<MultiTile> multiTiles;

    public TileRegion(int regSize) {
        this.size = regSize;
        this.grid = new Tile[regSize][regSize];
        this.multiTiles = new ArrayList<MultiTile>();
    }

    public Tile get(int x, int y) {
        return this.grid[x][y];
    }

    public void set(Tile t, int x, int y) {
        this.grid[x][y] = t;
    }

    public int getSize() {
        return this.size;
    }

    public void addMultiTile(MultiTile mt) {
        this.multiTiles.add(mt);
    }

    public MultiTile getMultiTile(int index) {
        return this.multiTiles.get(index);
    }

    public int getMultiTileQuant() {
        return this.multiTiles.size();
    }

}
