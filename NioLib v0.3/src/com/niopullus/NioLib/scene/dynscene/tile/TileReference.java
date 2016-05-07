package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.utilities.Utilities;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Owen on 4/10/2016.
 */
public class TileReference implements Comparable<TileReference> {

    private String name;
    private final int id;
    private final ArrayList<BufferedImage> images;
    private final double friction;
    private final double elasticity;
    private final boolean collidable;
    private final Tile tile;
    private static final ArrayList<TileReference> refs = new ArrayList<TileReference>();
    private static final ArrayList<TileReference> sortedRefs = new ArrayList<TileReference>();
    private static int curID = 1;
    private static final TileReference sampleRef = new TileReference(null, 0, null, 0, 0, false, null);

    public TileReference(String name, int id, BufferedImage image, double friction, double elasticity, boolean collidable, Tile tile) {
        this.id = id;
        this.images = new ArrayList<BufferedImage>();
        this.name = name;
        this.friction = friction;
        this.elasticity = elasticity;
        this.collidable = collidable;
        this.images.add(image);
        this.tile = tile;
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

    public BufferedImage getImage(int set) {
        return images.get(set);
    }

    public String getName() {
        return name;
    }

    public static int getCurID() {
        final int id = TileReference.curID;
        TileReference.curID++;
        return id;
    }

    public static TileReference getRef(final int id) {
        if (id >= 1 && id <= TileReference.getTileQuant()) {
            return TileReference.refs.get(id - 1);
        } else {
            return null;
        }
    }

    public static TileReference getRef(final String name) {
        TileReference.sampleRef.name = name;
        final int index = Collections.binarySearch(TileReference.sortedRefs, sampleRef);
        if (index >= 0) {
            return TileReference.sortedRefs.get(index);
        }
        return null;
    }

    public static void registerTile(final String name, final String image, final double friction, final double elasticity, final boolean collidable, final Tile tile) {
        final TileReference ref = new TileReference(name, getCurID(), Utilities.loadImage(image), friction, elasticity, collidable, tile);
        TileReference.refs.add(ref);
        TileReference.sortedRefs.add(ref);
        Collections.sort(sortedRefs);
        tile.setReference(TileReference.getRef(name));
    }

    public static void registerMultiTile(final String name, ArrayList<BufferedImage> images, double friction, double elasticity, boolean collidable, int width, int height, MultiTile tile) {
        final MultiTileReference ref = new MultiTileReference(name, getCurID(), images, friction, elasticity, collidable, width, height, tile);
        TileReference.refs.add(ref);
        TileReference.sortedRefs.add(ref);
        Collections.sort(sortedRefs);
        tile.setReference(TileReference.getRef(name));
    }

    public static void registerMultiTile(String name, String baseIMGName, double friction, double elasticity, boolean collidable, int width, int height, MultiTile tile) {
        final ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        final int parts = width * height;
        int dotPos = 0;
        for (int i = 0; i < baseIMGName.length(); i++) {
            if (baseIMGName.charAt(i) == '.') {
                dotPos = i;
                break;
            }
        }
        for (int i = 1; i <= parts; i++) {
            images.add(Utilities.loadImage(baseIMGName.substring(0, dotPos) + i + baseIMGName.substring(dotPos)));
        }
        registerMultiTile(name, images, friction, elasticity, collidable, width, height, tile);
    }

    public static int getTileQuant() {
        return TileReference.refs.size();
    }

    public int compareTo(final TileReference ref) {
        return name.compareTo(ref.getName());
    }

    public boolean getCollidable() {
        return collidable;
    }

    public Tile getSample() {
        return tile;
    }

    public void addImage(final BufferedImage image) {
        images.add(image);
    }

}
