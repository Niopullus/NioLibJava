package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.Crushable;
import com.niopullus.NioLib.DataTree;

import java.awt.*;

/**Maps tiles, tracks MultiTiles
 * Created by Owen on 4/13/2016.
 */
public class TileRegion implements Crushable {

    private Tilemap tilemap;
    private Tile[][] grid;

    public TileRegion(final Tilemap _tilemap) {
        final int regSize;
        tilemap = _tilemap;
        grid = new Tile[getRegSize()][getRegSize()];
    }

    public Tile get(final int x, final int y) {
        return grid[x][y];
    }

    public int getRegSize() {
        return tilemap.getRegSize();
    }

    public void set(final Tile tile, final int x, final int y) {
        grid[x][y] = tile;
    }

    /**
     * Crush Diagram:
     * root {
     *     f - tileBundle {
     *         i - x
     *         i - y
     *         f - tile
     *     }
     *     ...
     * }
     * @see Crushable
     */
    public DataTree crush() {
        final DataTree data = new DataTree();
        for (int i = 0; i < getRegSize(); i++) {
            for (int j = 0; j < getRegSize(); j++) {
                final Tile tile = grid[i][j];
                if (tile != null) {
                    final DataTree tileData = tile.crush();
                    if (tileData != null) {
                        final int dir = data.addFolder();
                        data.addData(i, dir);
                        data.addData(j, dir);
                        data.addData(tileData, dir);
                    }
                }
            }
        }
        return data;
    }

    public static TileRegion uncrush(final DataTree data, final Tilemap tilemap, final Point pos) {
        final TileRegion region = new TileRegion(tilemap);
        final Tile sample = new Tile();
        final int tiles = data.getSize();
        for (int i = 0; i < tiles; i++) {
            final int x = data.getI(i, 0);
            final int y = data.getI(i, 1);
            final DataTree tileData = new DataTree(data.getF(i, 2));
            final Tile tile = sample.uncrush(tileData);
            tile.setTileMapPos(new Point(pos.x + x, pos.y + y));
            tile.setTilemap(tilemap);
            region.set(tile, x, y);
        }
        return region;
    }

}
