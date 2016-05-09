package com.niopullus.NioLib.scene.dynscene.tile;

import java.util.ArrayList;

/**Maps tiles, tracks MultiTiles
 * Created by Owen on 4/13/2016.
 */
public class TileRegion {

    private int size;
    private Tile[][] grid;
    private ArrayList<MultiTile> multiTiles;

    public TileRegion(final int regSize) {
        this.size = regSize;
        this.grid = new Tile[regSize][regSize];
        this.multiTiles = new ArrayList<MultiTile>();
    }

    public Tile get(final int x, final int y) {
        return grid[x][y];
    }

    public int getSize() {
        return size;
    }

    public MultiTile getMultiTile(final int index) {
        return multiTiles.get(index);
    }

    public int getMultiTileCount() {
        return multiTiles.size();
    }

    public void set(final Tile tile, final int x, final int y) {
        grid[x][y] = tile;
    }

    public void addMultiTile(final MultiTile multiTile) {
        multiTiles.add(multiTile);
    }

}
