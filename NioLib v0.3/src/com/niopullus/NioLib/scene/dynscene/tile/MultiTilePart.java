package com.niopullus.NioLib.scene.dynscene.tile;

import java.awt.image.BufferedImage;

/**Tile that stands in for a part of a MultiTile
 * Created by Owen on 4/11/2016.
 */
public class MultiTilePart extends Tile {

    private int part;
    private MultiTile multiTile;

    /**
     * @param multiTile the MultiTile object that this object stands in
     *                  for
     * @param part Index of part in the MultiTile
     */

    public MultiTilePart(final MultiTile multiTile, final int part) {
        super(multiTile.getName());
        this.multiTile = multiTile;
        this.part = part;
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

    public BufferedImage getImage() {
        return multiTile.getImage(part);
    }

}
