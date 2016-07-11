package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.*;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.NodeHandler;
import com.niopullus.NioLib.scene.dynscene.tile.Tilemap;
import com.niopullus.app.Config;

import java.awt.*;
import java.util.List;

/**General container for DynamicScene related instance variables
 * Created by Owen on 4/13/2016.
 */
public class World {

    private String name;
    private Node universe;
    private PhysicsHandler physicsHandler;
    private Tilemap fgTilemap;
    private Tilemap bgTilemap;
    private Node camera;

    public Node getUniverse() {
        return universe;
    }

    public PhysicsHandler getPhysicsHandler() {
        return physicsHandler;
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

    public void setFgTilemap(final Tilemap fgTilemap) {
        this.fgTilemap = fgTilemap;
    }

    public void setBgTilemap(final Tilemap bgTilemap) {
        this.bgTilemap = bgTilemap;
    }

    public void setScene(final NodeHandler handler) {
        universe.setScene(handler);
    }

    public void setCamera(final Node camera) {
        this.camera = camera;
    }

    public static World loadWorld(final String fileName, final DynamicScene scene) {
        final World result = new World();
        final PhysicsHandler physicsHandler = new PhysicsHandler();
        final Tilemap fgMap;
        final Tilemap bgMap;
        String textData;
        final DataTree data;
        final Node universe;
        textData = Root.getTextFromFile("/worlds/" + fileName);
        if (textData == null) {
            textData = Data.getTextFromJar("/worlds/" + fileName);
        }
        data = DataTree.decompress(textData);
        universe = Node.uncrush(new DataTree(data.getF(3)), scene);
        fgMap = Tilemap.uncrush(new DataTree(data.getF(1)), universe.getChild(0), Config.TILESIZE);
        bgMap = Tilemap.uncrush(new DataTree(data.getF(2)), universe.getChild(0), Config.TILESIZE);
        physicsHandler.setTilemap(fgMap);
        result.setUniverse(universe);
        result.setFgTilemap(fgMap);
        result.setBgTilemap(bgMap);
        result.setName(data.getS(0));
        result.physicsHandler = physicsHandler;
        return result;
    }

    public static World loadWorld(final String fileName) {
        return loadWorld(fileName, null);
    }

    public static void saveWorld(final World world) {
        final DataTree data = new DataTree();
        final String name = world.getName();
        final Tilemap fg = world.getFgTilemap();
        final Tilemap bg = world.getBgTilemap();
        final Node universe = world.getUniverse();
        final String fileName = world.getName() + ".niolibworld";
        final String dir = "/worlds/" + fileName;
        final String textData;
        data.addData(name);
        data.addData(fg);
        data.addData(bg);
        data.addData(universe);
        textData = data.compress();
        Root.createFileFromFile("/worlds/", fileName);
        Root.writeToFileFromFile(dir, textData, true);
    }

    public static World generate(final String name) {
        final Node world = new Node("world");
        final Node universe = new Node("universe");
        final PhysicsHandler physicsHandler = new PhysicsHandler();
        final Background background = new ColorBackground(Color.WHITE, Main.Width(), Main.Height());
        final Tilemap fgtilemap = new Tilemap(Config.TILESIZE, Config.TILEREGIONSIZE, Config.TILEMAPRAD, Config.TILEMAPRAD);
        final Tilemap bgtilemap = new Tilemap(Config.TILESIZE, Config.TILEREGIONSIZE, Config.TILEMAPRAD, Config.TILEMAPRAD);
        final World storedWorld = new World();
        fgtilemap.setZ(Config.FGTILEMAPZ);
        bgtilemap.setZ(Config.BGTILEMAPZ);
        fgtilemap.setWorld(world);
        bgtilemap.setWorld(world);
        physicsHandler.setTilemap(fgtilemap);
        universe.addChild(world);
        universe.markUniverse();
        world.markWorld();
        storedWorld.setName(name);
        storedWorld.setUniverse(universe);
        storedWorld.setPhysicsHandler(physicsHandler);
        storedWorld.setFgTilemap(fgtilemap);
        storedWorld.setBgTilemap(bgtilemap);
        return storedWorld;
    }

}
