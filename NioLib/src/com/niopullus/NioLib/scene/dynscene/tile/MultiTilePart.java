package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.DataTree;
import com.niopullus.NioLib.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;

/**Tile that stands in for a part of a MultiTile
 * Created by Owen on 4/11/2016.
 */
public class MultiTilePart extends Tile {

    private int relX;
    private int relY;
    private MultiTile multiTile;

    /**
     * @param multiTile the MultiTile object that this object stands in
     *                  for
     * @param relX x coordinate of this part relative to the MultiTile
     * @param relY y coordinate of this part relative to the MultiTile
     */

    public MultiTilePart(final MultiTile multiTile, final int relX, final int relY) {
        super(multiTile.getName());
        this.multiTile = multiTile;
        this.relX = relX;
        this.relY = relY;
    }

    /**
     * @return the MultiTile that this part points to
     */

    public MultiTile get() {
        return multiTile;
    }

    /**
     * @return the image of the multitile that corresponds with this part
     */

    public Picture getImage() {
        return multiTile.getImage(relX, relY);
    }

    public DataTree crush() {
        if (isAnchor()) {
            return multiTile.crush();
        } else {
            return null;
        }
    }

    public boolean isAnchor() {
        final Point anchor = multiTile.getAnchor();
        return anchor.equals(new Point(relX, relY));
    }

}
