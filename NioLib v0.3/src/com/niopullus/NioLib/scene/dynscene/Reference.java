package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.scene.dynscene.tile.MultiTileReference;
import com.niopullus.NioLib.scene.dynscene.tile.Tile;
import com.niopullus.NioLib.scene.dynscene.tile.TileReference;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Owen on 4/26/2016.
 */
public class Reference implements Comparable<Reference> {

    private String name;
    private Object sample;
    private int id;
    private static ArrayList<TileReference> tilerefs = new ArrayList<TileReference>();
    private static ArrayList<NodeReference> noderefs = new ArrayList<NodeReference>();
    private static ArrayList<TileReference> sortedTileRefs = new ArrayList<TileReference>();
    private static ArrayList<NodeReference> sortedNodeRefs = new ArrayList<NodeReference>();
    private static int curIDTile = 1;
    private static int curIDNode = 0;
    private static Reference sampleRef = new TileReference(null, 0, null, 0, 0, false, null);

    public Reference(final String name, final int id, final Object sample) {
        this.name = name;
        this.sample = sample;
        this.id = id;
    }

    public Object getSample() {
        return sample;
    }

    public int getNode() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int compareTo(final Reference ref) {
        return name.compareTo(ref.name);
    }

    private static int getCurIDTile() {
        final int id = curIDTile;
        curIDTile++;
        return id;
    }

    private static int getCurIDNode() {
        final int id = curIDNode;
        curIDNode++;
        return id;
    }

    public static TileReference getTileRef(final int id) {
        if (id >= 1 && id <= getTileQuant()) {
            return tilerefs.get(id - 1);
        } else {
            return null;
        }
    }

    public static NodeReference getNodeRef(final int id) {
        if (id < getNodeQuant() && id >= 0) {
            return noderefs.get(id);
        } else {
            return null;
        }
    }

    public static TileReference getTileRef(final String name) {
        final int index;
        sampleRef.name = name;
        index = Collections.binarySearch(sortedTileRefs, sampleRef);
        if (index >= 0) {
            return sortedTileRefs.get(index);
        }
        return null;
    }

    public static NodeReference getNodeRef(final String name) {
        final int index;
        sampleRef.name = name;
        index = Collections.binarySearch(sortedNodeRefs, sampleRef);
        if (index >= 0) {
            return sortedNodeRefs.get(index);
        }
        return null;
    }

    public static void registerTile(final String name, final String image, final double friction, final double elasticity, final boolean collidable, final Tile tile) {
        final TileReference ref = new TileReference(name, getCurIDTile(), Utilities.loadImage(image), friction, elasticity, collidable, tile);
        tilerefs.add(ref);
        sortedTileRefs.add(ref);
        Collections.sort(sortedTileRefs);
        tile.setReference(getTileRef(name));
    }

    public static void registerMultiTile(final String name, final ArrayList<BufferedImage> images, final double friction, final double elasticity, final boolean collidable, final int width, final int height, final Tile tile) {
        final MultiTileReference ref = new MultiTileReference(name, getCurIDTile(), images, friction, elasticity, collidable, width, height, tile);
        Reference.tilerefs.add(ref);
        Reference.sortedTileRefs.add(ref);
        Collections.sort(sortedTileRefs);
        tile.setReference(Reference.getTileRef(name));
    }

    public static void registerNode(String name, double defaultXScale, double defaultYScale, Node sample) {
        final NodeReference ref = new NodeReference(name, defaultXScale, defaultYScale, getCurIDNode(), sample);
        Reference.noderefs.add(ref);
        Reference.sortedNodeRefs.add(ref);
        Collections.sort(sortedNodeRefs);
        sample.setReference(Reference.getNodeRef(name));
    }

    public static int getTileQuant() {
        return Reference.tilerefs.size();
    }

    public static int getNodeQuant() {
        return Reference.noderefs.size();
    }

}
