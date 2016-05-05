package com.niopullus.NioLib.scene.dynscene.tile;

import com.niopullus.NioLib.utilities.Utilities;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Owen on 4/10/2016.
 */
public class TileReference implements Comparable<TileReference>, Serializable {

    private String name;
    private int id;
    private ArrayList<BufferedImage> images;
    private double friction;
    private double elasticity;
    private boolean collidable;
    private Tile tile;
    private static ArrayList<TileReference> refs = new ArrayList<TileReference>();
    private static ArrayList<TileReference> sortedRefs = new ArrayList<TileReference>();
    private static int curID = 1;
    private static TileReference sampleRef = new TileReference(null, 0, null, 0, 0, false, null);

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
        return this.elasticity;
    }

    public double getFriction() {
        return this.friction;
    }

    public int getId() {
        return this.id;
    }

    public BufferedImage getImage() {
        return this.images.get(0);
    }

    public BufferedImage getImage(int set) {
        return this.images.get(set);
    }

    public String getName() {
        return this.name;
    }

    public static int getCurID() {
        int id = TileReference.curID;
        TileReference.curID++;
        return id;
    }

    public static TileReference getRef(int id) {
        if (id >= 1 && id <= TileReference.getTileQuant()) {
            return TileReference.refs.get(id - 1);
        } else {
            return null;
        }
    }

    public static TileReference getRef(String name) {
        TileReference.sampleRef.name = name;
        int index = Collections.binarySearch(TileReference.sortedRefs, sampleRef);
        if (index >= 0) {
            return TileReference.sortedRefs.get(index);
        }
        return null;
    }

    public static void registerTile(String name, String image, double friction, double elasticity, boolean collidable, Tile tile) {
        TileReference ref = new TileReference(name, getCurID(), Utilities.loadImage(image), friction, elasticity, collidable, tile);
        TileReference.refs.add(ref);
        TileReference.sortedRefs.add(ref);
        Collections.sort(sortedRefs);
        tile.setReference(TileReference.getRef(name));
    }

    public static void registerMultiTile(String name, ArrayList<BufferedImage> images, double friction, double elasticity, boolean collidable, int width, int height, Tile tile) {
        MultiTileReference ref = new MultiTileReference(name, getCurID(), images, friction, elasticity, collidable, width, height, tile);
        TileReference.refs.add(ref);
        TileReference.sortedRefs.add(ref);
        Collections.sort(sortedRefs);
        tile.setReference(TileReference.getRef(name));
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

    public static int getTileQuant() {
        return TileReference.refs.size();
    }

    public int compareTo(TileReference ref) {
        return this.name.compareTo(ref.getName());
    }

    public boolean getCollidable() {
        return this.collidable;
    }

    public Tile getSample() {
        return this.tile;
    }

    public void addImage(BufferedImage image) {
        this.images.add(image);
    }

}
