package com.niopullus.app;


import com.niopullus.NioLib.Picture;
import com.niopullus.NioLib.scene.dynscene.ShapeNode;
import com.niopullus.NioLib.scene.dynscene.SketchNode;
import com.niopullus.NioLib.scene.dynscene.reference.Ref;
import com.niopullus.NioLib.scene.dynscene.reference.TileReference;
import com.niopullus.NioLib.scene.dynscene.tile.Tile;

import static com.niopullus.NioLib.scene.dynscene.reference.TileReference.TileReferencePack;

import java.awt.*;

/**Used to tweak aspects of one's NioLib project
 * Created by Owen on 4/7/2016.
 */
public final class Config {

    private Config() {
        //Blank Implementation
    }

    public static final String NIOLIBVERSION = "v0.04";
    public static final int IMGWIDTH = 1920;
    public static final int IMGHEIGHT = 1080;
    public static final double WINDOWSCALE = 0.5;
    public static final String WINDOWTITLE = "Window Title";
    public static boolean WINDOWRESIZABLE = true;
    public static final int TILESIZE = 70;
    public static final int NODEPARTSIZE = 100;
    public static final int TILEMAPRAD = 100;
    public static final int NODEPARTRAD = 100;
    public static final int TILEREGIONSIZE = 100;
    public static final String DIRNAME = "Default Directory";
    public static final int FGTILEMAPZ = 50;
    public static final int BGTILEMAPZ = 25;
    public static final String DEFAULTFONTNAME = "Bold";
    public static final int DEFAULTELEMENTGAPWIDTH = 20;
    public static final int DEFAULTELEMENTGAPHEIGHT = 20;
    public static final boolean CREATEFOLDER = true;
    public static final boolean PROMPTFOLDERDIRECTORY = false;
    public static final boolean ALLOWWORLDSAVESTOJAR = false;

    public static void init() {
        //Register tiles, nodes, program setup
        Picture.loadPictureFromJar("drphil.jpg", "drphil");
        Picture.loadPictureFromJar("jgwentworth.jpg", "jgwentworth");
        Picture.loadPictureFromJar("kfc.jpg", "kfc");
        Picture.loadPictureFromJar("metaltile.jpg", "metaltile");
        Picture.loadPictureFromJar("coolguy.png", "coolguy");
        Picture.loadPictureFromJar("bricks.jpg", "bricks");
        Ref.registerNode("the square", 1, 1, new ShapeNode("the square", Color.BLUE, 100, 100));
        Ref.registerNode("osama", 1, 1, new SketchNode("osama", Picture.getPicture("kfc"), 100, 100));
        final TileReferencePack pack = new TileReferencePack();
        pack.sample = new Tile("metal");
        pack.collidable = true;
        pack.elasticity = 0;
        pack.friction = 0.2;
        pack.image = Picture.getPicture("metaltile");
        pack.name = "metal";
        Ref.registerTile(pack);
        final TileReferencePack pack2 = new TileReferencePack();
        pack2.sample = new Tile("bricks");
        pack2.collidable = true;
        pack2.elasticity = 0;
        pack2.friction = 0.2;
        pack2.image = Picture.getPicture("bricks");
        pack2.name = "bricks";
        Ref.registerTile(pack2);
        Ref.registerNode("coolguy", 1, 1, new SketchNode("coolguy", Picture.getPicture("coolguy"), 100, 200));
    }

}
