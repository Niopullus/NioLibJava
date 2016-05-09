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

    public Reference(String name, int id, Object sample) {
        this.name = name;
        this.sample = sample;
        this.id = id;
    }

    public static int getCurIDTile() {
        int id = Reference.curIDTile;
        Reference.curIDTile++;
        return id;
    }

    public static int getCurIDNode() {
        int id = Reference.curIDNode;
        Reference.curIDNode++;
        return id;
    }

    public static TileReference getTileRef(int id) {
        if (id >= 1 && id <= Reference.getTileQuant()) {
            return Reference.tilerefs.get(id - 1);
        } else {
            return null;
        }
    }

    public static NodeReference getNodeRef(int id) {
        if (id < Reference.getNodeQuant() && id >= 0) {
            return Reference.noderefs.get(id);
        } else {
            return null;
        }
    }

    public static TileReference getTileRef(String name) {
        Reference.sampleRef.name = name;
        int index = Collections.binarySearch(Reference.sortedTileRefs, sampleRef);
        if (index >= 0) {
            return Reference.sortedTileRefs.get(index);
        }
        return null;
    }

    public static NodeReference getNodeRef(String name) {
        Reference.sampleRef.name = name;
        int index = Collections.binarySearch(Reference.sortedNodeRefs, sampleRef);
        if (index >= 0) {
            return Reference.sortedNodeRefs.get(index);
        }
        return null;
    }

    public static void registerTile(String name, String image, double friction, double elasticity, boolean collidable, Tile tile) {
        TileReference ref = new TileReference(name, getCurIDTile(), Utilities.loadImage(image), friction, elasticity, collidable, tile);
        Reference.tilerefs.add(ref);
        Reference.sortedTileRefs.add(ref);
        Collections.sort(sortedTileRefs);
        tile.setReference(Reference.getTileRef(name));
    }

    public static void registerMultiTile(String name, ArrayList<BufferedImage> images, double friction, double elasticity, boolean collidable, int width, int height, Tile tile) {
        MultiTileReference ref = new MultiTileReference(name, getCurIDTile(), images, friction, elasticity, collidable, width, height, tile);
        Reference.tilerefs.add(ref);
        Reference.sortedTileRefs.add(ref);
        Collections.sort(sortedTileRefs);
        tile.setReference(Reference.getTileRef(name));
    }

    public static void registerMultiTile(String name, String baseIMGName, double friction, double elasticity, boolean collidable, int width, int height, Tile tile) {
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        int dotPos = 0;
        for (int i = 0; i < baseIMGName.length(); i++) {
            if (baseIMGName.charAt(i) == '.') {
                dotPos = i;
                break;
            }
        }
        for (int i = 1; i <= width * height; i++) {
            images.add(Utilities.loadImage(baseIMGName.substring(0, dotPos) + i + baseIMGName.substring(dotPos)));
        }
        registerMultiTile(name, images, friction, elasticity, collidable, width, height, tile);
    }

    public static void registerNode(String name, double defaultXScale, double defaultYScale, Node sample) {
        NodeReference ref = new NodeReference(name, defaultXScale, defaultYScale, getCurIDNode(), sample);
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

    public Object getSample() {
        return this.sample;
    }

    public int compareTo(Reference ref) {
        return this.name.compareTo(ref.name);
    }

    public int getNode() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

}
