package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.utilities.Utilities;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**Stores common data from tiles
 * Created by Owen on 4/10/2016.
 */
public class TileReference implements Comparable<TileReference>, Serializable {

    private String name;
    private final int id;
    private final ArrayList<BufferedImage> images;
    private final double friction;
    private final double elasticity;
    private final boolean collidable;
    private final Tile sample;
    private static final ArrayList<TileReference> refs = new ArrayList<TileReference>();
    private static final ArrayList<TileReference> sortedRefs = new ArrayList<TileReference>();
    private static int curID = 1;
    private static final TileReference sampleRef = new TileReference(null, 0, null, 0, 0, false, null);

    public TileReference(final String name, final int id, final BufferedImage image, final double friction, final double elasticity, final boolean collidable, final Tile sample) {
        this.id = id;
        this.images = new ArrayList<BufferedImage>();
        this.name = name;
        this.friction = friction;
        this.elasticity = elasticity;
        this.collidable = collidable;
        this.images.add(image);
        this.sample = sample;
    }

    public double getElasticity() {
        return elasticity;
    }

    public double getFriction() {
        return friction;
    }

    public int getId() {
        return id;
    }

    public BufferedImage getImage() {
        return images.get(0);
    }

    public BufferedImage getImage(final int set) {
        return images.get(set);
    }

    public String getName() {
        return name;
    }

    public boolean getCollidable() {
        return collidable;
    }

    public Tile getSample() {
        return sample;
    }

    public static int getCurID() {
        final int id = curID;
        curID++;
        return id;
    }

    public static TileReference getRef(int id) {
        if (id >= 1 && id <= TileReference.getTileQuant()) {
            return TileReference.refs.get(id - 1);
        } else {
            return null;
        }
    }

    public static TileReference getRef(final String name) {
        sampleRef.name = name;
        final int index = Collections.binarySearch(sortedRefs, sampleRef);
        if (index >= 0) {
            return sortedRefs.get(index);
        }
        return null;
    }

    public static void registerTile(final String name, final String image, final double friction, final double elasticity, final boolean collidable, final Tile tile) {
        final TileReference ref = new TileReference(name, getCurID(), Utilities.loadImage(image), friction, elasticity, collidable, tile);
        refs.add(ref);
        sortedRefs.add(ref);
        Collections.sort(sortedRefs);
        tile.setReference(TileReference.getRef(name));
    }

    public static void registerMultiTile(String name, ArrayList<BufferedImage> images, double friction, double elasticity, boolean collidable, int width, int height, MultiTile tile) {
        final MultiTileReference ref = new MultiTileReference(name, getCurID(), images, friction, elasticity, collidable, width, height, tile);
        refs.add(ref);
        sortedRefs.add(ref);
        Collections.sort(sortedRefs);
        tile.setReference(TileReference.getRef(name));
    }

    public static int getTileQuant() {
        return refs.size();
    }

    public int compareTo(final TileReference reference) {
        return name.compareTo(reference.getName());
    }

    public void addImage(final BufferedImage image) {
        images.add(image);
    }

}
