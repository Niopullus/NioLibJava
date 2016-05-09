package com.niopullus.NioLib.scene.dynscene.tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**Tile thats takes up multiple tilemap spaces
 * Created by Owen on 4/11/2016.
 */
public class MultiTile extends Tile {

    private ArrayList<MultiTilePart> parts;
    private Point refTilePoint;

    public MultiTile(final String refName, final Point refTP) {
        super(refName);
        this.parts = new ArrayList<MultiTilePart>();
        this.refTilePoint = new Point();
    }

    public Point getRefTilePoint() {
        return refTilePoint;
    }

    public MultiTileReference getReference() {
        return (MultiTileReference) super.getReference();
    }

    public int getWidth() {
        final MultiTileReference ref = getReference();
        return ref.getWidth();
    }

    public int getHeight() {
        final MultiTileReference ref = getReference();
        return ref.getHeight();
    }

    public BufferedImage getImage(final int part) { //Gets the image of a particular part of the MultiTile
        final MultiTileReference ref = getReference();
        return ref.getImage(part);
    }

    public MultiTilePart getPart(final int index) { //Gets the part of a certain index (0-[WIDTH * HEIGHT])
        return parts.get(index);
    }

    public int getPartsCount() { //Gets the amount of MultiTileParts (Can also be calculated with (WIDTH * HEIGHT))
        return parts.size();
    }

    public Point getRWPos() { //Gets the in-world position of the (x-center, y-center) of the MultiTile
        final MultiTileReference ref = getReference();
        final Tilemap tilemap = getTilemap();
        final int tileSize = tilemap.getTileSize();
        final int x = (refTilePoint.x + ref.getWidth() / 2) * tileSize;
        final int y = (refTilePoint.y + ref.getHeight() / 2) * tileSize;
        return new Point(x, y);
    }

    public Point getTileMapPos() {
        final Point result = new Point();
        final MultiTileReference ref = getReference();
        final Tilemap tilemap = getTilemap();
        final int tileSize = tilemap.getTileSize();
        final int x = refTilePoint.x + ref.getWidth() / 2;
        final int y = refTilePoint.y + ref.getHeight() / 2;
        result.x = x;
        result.y = y;
        return result;
    }

    public void setRefTilePoint(Point point) {
        refTilePoint.x = point.x;
        refTilePoint.y = point.y;
    }

    public void addPart(final MultiTilePart part) {
        parts.add(part);
    }

}
