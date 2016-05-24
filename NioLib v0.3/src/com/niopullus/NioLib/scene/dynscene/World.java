package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.DataPath;
import com.niopullus.NioLib.DataTree;
import com.niopullus.NioLib.Main;
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
        File worldFile;
        String textData = "";
        worldFile = new File("C:\\" + Config.DIRNAME + "\\worlds\\" + fileName);
        if (!worldFile.exists()) {
            try {
                final String path = "builtinworlds";
                String jarPath = World.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                jarPath = jarPath.replace('/', '\\');
                jarPath = jarPath.replace("%20", " ");
                final File jarFile = new File(jarPath.substring(1));
                if (jarFile.isFile()) {  // Run with JAR file
                    InputStream in = (new Node("temp")).getClass().getClassLoader().getResourceAsStream(path + "/" + fileName);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        textData += line;
                    }
                    reader.close();
                } else { // Run with IDE
                    worldFile = new File("C:\\" + Config.DIRNAME + "\\worlds\\" + fileName);
                    if (!worldFile.exists()) {
                        worldFile = new File("Resources\\" + path + "\\" + fileName);
                    }
                    BufferedReader reader = new BufferedReader(new FileReader(worldFile));
                    List<String> lines = Files.readAllLines(worldFile.toPath());
                    reader.close();
                    for (String s : lines) {
                        textData += s;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(worldFile));
                List<String> lines = Files.readAllLines(worldFile.toPath());
                reader.close();
                for (String s : lines) {
                    textData += s;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final DataTree data = DataTree.decompress(textData);
        final Node universe = Node.uncrush(new DataTree(data.getF(2)));
        result.setUniverse(universe);
        result.setFgTilemap(Tilemap.decompress(new DataTree((ArrayList) data.get(new DataPath(new int[]{0}))), universe.getChild(0), Config.TILESIZE));
        result.setBgTilemap(Tilemap.decompress(new DataTree((ArrayList) data.get(new DataPath(new int[]{1}))), universe.getChild(0), Config.TILESIZE));
        return result;
    }

    public static World loadWorld(final String fileName) {
        return loadWorld(fileName, null);
    }

    public static void saveWorld(World world) {
        DataTree data = new DataTree();
        Tilemap fg = world.getFgTilemap();
        Tilemap bg = world.getBgTilemap();
        data.addData((ArrayList) fg.compress().get());
        data.addData((ArrayList) bg.compress().get());
        String textData = data.compress();
        BufferedWriter writer = null;
        try {
            File worldFile = new File("C:\\" + Config.DIRNAME + "\\worlds\\" + world.getName() + ".niolibworld");
            writer = new BufferedWriter(new FileWriter(worldFile));
            writer.write(textData);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static World generateWorld(final String name, final NodeHandler scene) {
        final Node world = new Node("world");
        final Node universe = new Node("universe");
        final PhysicsHandler physicsHandler = new PhysicsHandler();
        final Background background = new ColorBackground(0, 0, Main.Width(), Main.Height(), Color.WHITE);
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
