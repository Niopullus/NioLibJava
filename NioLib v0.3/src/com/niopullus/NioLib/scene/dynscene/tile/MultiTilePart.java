package com.niopullus.NioLib.scene.dynscene.tile;

import java.awt.image.BufferedImage;

/**Tile that stands in for a part of a MultiTile
 * Created by Owen on 4/11/2016.
 */
public class MultiTilePart extends Tile {

    private int part;
    private MultiTile multiTile;

    public MultiTilePart(final MultiTile multiTile, final int part) { //part: Index of part in the MultiTile
        super(multiTile.getName());
        this.multiTile = multiTile;
        this.part = part;
    }

    public MultiTile get() { //Gets the MultiTile that this part points to
        return multiTile;
    }

    public BufferedImage getImage() { //Gets the image of the multitile that corresponds with this part
        return multiTile.getImage(part);
    }

}
