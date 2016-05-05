package com.niopullus.NioLib.scene.dynscene.tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Owen on 4/11/2016.
 */
public class MultiTilePart extends Tile {

    private MultiTile tile;
    private int part;

    public MultiTilePart(MultiTile tile, int part) {
        super(tile.getName());
        this.tile = tile;
        this.part = part;
    }

    public MultiTile get() {
        return this.tile;
    }

    public BufferedImage getImage() {
        return this.tile.getImage(this.part);
    }

}
