package com.niopullus.app;

/**Used to tweak aspects of one's NioLib project
 * Created by Owen on 4/7/2016.
 */
public final class Config {

    private Config() {
        //Blank Implementation
    }

    public static final String NIOLIBVERSION = "v0.05";
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
    }

}
