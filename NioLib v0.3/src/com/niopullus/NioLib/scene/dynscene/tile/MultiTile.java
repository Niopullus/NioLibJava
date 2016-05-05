package com.niopullus.NioLib.scene.dynscene.tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Owen on 4/11/2016.
 */
public class MultiTile extends Tile {

    private ArrayList<MultiTilePart> parts;
    private Point primePosition;

    public MultiTile(String refName) {
        super(refName);
        this.parts = new ArrayList<MultiTilePart>();
    }

    public void setPrimePosition(Point p) {
        this.primePosition = p;
    }

    public Point getPrimePosition() {
        return this.primePosition;
    }

    public int getWidth() {
        MultiTileReference ref = (MultiTileReference) this.getReference();
        return ref.getWidth();
    }

    public int getHeight() {
        MultiTileReference ref = (MultiTileReference) this.getReference();
        return ref.getHeight();
    }

    public BufferedImage getImage(int part) {
        return this.getReference().getImage(part);
    }

    public void addPart(MultiTilePart part) {
        this.parts.add(part);
    }
/**
    public Point getRwPos() {
        return new Point((this.parts.get(0).getRwPos().x + this.parts.get(this.getWidth() * this.getHeight() - 1).getRwPos().x) / 2, (this.parts.get(0).getRwPos().y + this.parts.get(this.getWidth() * this.getHeight() - 1).getRwPos().y) / 2);
    }
**/

    public Point getRwPos() {
        MultiTileReference ref = (MultiTileReference) this.getReference();
        return new Point((this.primePosition.x + ref.getWidth() / 2) * this.getTilemap().getTileSize(), (this.primePosition.y + ref.getHeight() / 2) * this.getTilemap().getTileSize());
    }

    public MultiTilePart getPart(int index) {
        return this.parts.get(index);
    }

    public int getPartQuant() {
        return this.parts.size();
    }

}
