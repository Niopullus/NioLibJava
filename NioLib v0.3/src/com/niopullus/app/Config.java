package com.niopullus.app;


import com.niopullus.NioLib.scene.dynscene.tile.MultiTileReference;
import com.niopullus.NioLib.scene.dynscene.tile.TileReference;
import com.niopullus.NioLib.utilities.Utilities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Owen on 4/7/2016.
 */
public class Config {

    public static final String NIOLIBVERSION = "v0.03";
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
    public static final String DIRNAME = "Default Dir";

    public static void init() {

    }

}
