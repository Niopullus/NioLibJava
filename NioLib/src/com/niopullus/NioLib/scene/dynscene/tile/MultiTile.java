package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.scene.dynscene.reference.MultiTileReference;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**Tile that takes up multiple tilemap spaces
 * Created by Owen on 4/11/2016.
 */
public class MultiTile extends Tile {

    private List<MultiTilePart> parts;
    private Point refTilePoint;
    private final int tileType;

    public MultiTile(final String refName, final Point refTP) {
        super(refName);
        this.parts = new ArrayList<>();
        this.refTilePoint = new Point();
        this.tileType = 1;
    }

    public int getTileType() {
        return tileType;
    }

    /**
     * @return the tile-coordinate location of the bottom left MultiTilePart
     */

    public Point getRefTilePoint() {
        return refTilePoint;
    }

    public MultiTileReference getReference() {
        return (MultiTileReference) super.getReference();
    }

    /**
     * @return the amount of MultiTileParts-wide the MultiTile is
     */

    public int getWidth() {
        final MultiTileReference ref = getReference();
        return ref.getWidth();
    }

    /**
     * @return the amount of MultiTileParts-tall the MultiTile is
     */

    public int getHeight() {
        final MultiTileReference ref = getReference();
        return ref.getHeight();
    }

    /**
     * @param relX x coordinate relative to the MultiTile
     * @param relY y coordinate relative to the MultiTile
     * @return the image of a particular part of the MultiTile
     */

    public BufferedImage getImage(final int relX, final int relY) {
        final MultiTileReference ref = getReference();
        return ref.getImage(relX, relY);
    }

    /**
     * @param index of the part
     * @return the MultiTilePart(0-[WIDTH * HEIGHT])
     */

    public MultiTilePart getPart(final int index) {
        return parts.get(index);
    }

    /**
     * @return the amount of MultiTileParts (Can also be calculated with
     * (WIDTH * HEIGHT))
     */

    public int getPartsCount() {
        return parts.size();
    }

    public Point getAnchor() {
        final MultiTileReference reference = getReference();
        return reference.getAnchor();
    }

    public Point getRWPos() {
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
        final int x = refTilePoint.x + ref.getWidth() / 2;
        final int y = refTilePoint.y + ref.getHeight() / 2;
        result.x = x;
        result.y = y;
        return result;
    }

    public void setRefTilePoint(final Point point) {
        refTilePoint = point;
    }

    public void addPart(final MultiTilePart part) {
        parts.add(part);
    }

    public void parcelDraw(final Canvas canvas) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                final BufferedImage image = getImage(i, j);
                if (image != null) {
                    canvas.o.image(image, i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize());
                }
            }
        }
    }

}
