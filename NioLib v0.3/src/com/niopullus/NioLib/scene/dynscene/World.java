package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.*;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.NodeHandler;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.tile.Tilemap;
import com.niopullus.app.Config;
import com.sun.javaws.Launcher;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**General container for DynamicScene related instance variables
 * Created by Owen on 4/13/2016.
 */
public class World {

    private String name;
    private Node universe;
    private PhysicsHandler physicsHandler;
    private Background background;
    private Tilemap fgTilemap;
    private Tilemap bgTilemap;
    private Node camera;

    public Node getUniverse() {
        return universe;
    }

    public PhysicsHandler getPhysicsHandler() {
        return physicsHandler;
    }

    public Background getBackground() {
        return background;
    }

    public Tilemap getFgTilemap() {
        return fgTilemap;
    }

    public Tilemap getBgTilemap() {
        return bgTilemap;
    }

    public Node getCamera() {
        return camera;
    }

    public String getName() {
        if (name == null) {
            return "Unnamed World";
        } else {
            return name;
        }
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUniverse(final Node universe) {
        this.universe = universe;
    }

    public void setPhysicsHandler(final PhysicsHandler physicsHandler) {
        this.physicsHandler = physicsHandler;
    }

    public void setBackground(final Background background) {
        this.background = background;
    }

    public void setFgTilemap(final Tilemap fgTilemap) {
        this.fgTilemap = fgTilemap;
    }

    public void setBgTilemap(final Tilemap bgTilemap) {
        this.bgTilemap = bgTilemap;
    }

    public void setCamera(final Node camera) {
        this.camera = camera;
    }

    public static World loadWorld(final String fileName, final DynamicScene scene) {
        final World result = new World();
        String textData;
        final DataTree data;
        final Node universe;
        textData = Data.getTextFromFile("worlds/" + fileName);
        if (textData == null) {
            textData = Data.getTextFromJar("worlds/" + fileName);
        }
        data = DataTree.decompress(textData);
        universe = Node.uncrush(new DataTree(data.getF(2)), scene);
        result.setUniverse(universe);
        result.setFgTilemap(Tilemap.uncrush(new DataTree((List) data.get(0)), universe.getChild(0), Config.TILESIZE));
        result.setBgTilemap(Tilemap.uncrush(new DataTree((List) data.get(1)), universe.getChild(0), Config.TILESIZE));
        return result;
    }

    public static World loadWorld(final String fileName) {
        return loadWorld(fileName, null);
    }

    public static void saveWorld(final World world, Data.DataRoot root) {
        final DataTree data = new DataTree();
        final Tilemap fg = world.getFgTilemap();
        final Tilemap bg = world.getBgTilemap();
        final String textData;
        data.addData(fg);
        data.addData(bg);
        textData = data.compress();
        final String dir = "worlds/" + world.getName();
        if (root == Data.DataRoot.FILE) {
            Data.createFileFromFile(dir);
            Data.writeToFileFromFile(dir, textData);
        } else if (root == Data.DataRoot.JAR) {
            Data.createFileFromJar(dir);
            Data.writeToFileFromFile(dir, textData);
        }
    }

    public static World generateWorld(final String name, final NodeHandler scene) {
        final Node world = new Node("world");
        final Node universe = new Node("universe");
        final PhysicsHandler physicsHandler = new PhysicsHandler();
        final Background background = new ColorBackground(Color.WHITE, Main.Width(), Main.Height());
        final Tilemap fgtilemap = new Tilemap(Config.TILESIZE, Config.TILEREGIONSIZE, Config.TILEMAPRAD, Config.TILEMAPRAD);
        final Tilemap bgtilemap = new Tilemap(Config.TILESIZE, Config.TILEREGIONSIZE, Config.TILEMAPRAD, Config.TILEMAPRAD);
        final World storedWorld = new World();
        fgtilemap.setZ(Config.FGTILEMAPZ);
        bgtilemap.setZ(Config.BGTILEMAPZ);
        physicsHandler.setTilemap(fgtilemap);
        universe.setScene(scene);
        universe.addChild(world);
        universe.markUniverse();
        universe.setScene(scene);
        world.markWorld();
        storedWorld.setName(name);
        storedWorld.setUniverse(universe);
        storedWorld.setPhysicsHandler(physicsHandler);
        storedWorld.setBackground(background);
        storedWorld.setFgTilemap(fgtilemap);
        storedWorld.setBgTilemap(bgtilemap);
        return storedWorld;
    }

}
