package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.tile.MultiTile;
import com.niopullus.NioLib.scene.dynscene.tile.Tile;
import com.niopullus.NioLib.scene.dynscene.tile.Tilemap;
import com.niopullus.app.Config;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Owen on 3/5/2016.
 */
public class DynamicScene extends Scene implements Serializable {
//test
    private String name;
    private Node universe;
    private Node world;
    private PhysicsHandler physicsHandler;
    private Background background;
    private Tilemap fgTilemap;
    private Tilemap bgTilemap;
    private Node camera;

    public DynamicScene(World world) {
        this.name = world.getName();
        this.universe = world.getUniverse();
        this.world = world.getWorld();
        this.physicsHandler = world.getPhysicsHandler();
        this.background = world.getBackground();
        this.fgTilemap = world.getFgTilemap();
        this.bgTilemap = world.getBgTilemap();
        this.camera = world.getCamera();
    }

    public DynamicScene() {
        this("Unnamed Scene");
    }

    public DynamicScene(String name) {
        this.name = name;
        this.universe = new Node("universe");
        this.world = new Node("world");
        this.world.markWorld();
        this.universe.setDynamicScene(this);
        this.universe.addChild(this.world);
        this.universe.markUniverse();
        this.universe.setDynamicScene(this);
        this.background = new ColorBackground(0, 0, Main.Width(), Main.Height(), Color.WHITE);
        this.fgTilemap = new Tilemap(Config.TILESIZE, Config.TILEMAPRAD, Config.TILEMAPRAD, Config.TILEREGIONSIZE);
        this.bgTilemap = new Tilemap(Config.TILESIZE, Config.TILEMAPRAD, Config.TILEMAPRAD, Config.TILEREGIONSIZE);
        this.physicsHandler = new PhysicsHandler();
        this.physicsHandler.setTilemap(this.fgTilemap);
        this.fgTilemap.setDynamicScene(this);
        this.bgTilemap.setDynamicScene(this);
        this.fgTilemap.setZ(2);
        this.bgTilemap.setZ(1);
    }

    public final void tick() {
        if (this.physicsHandler != null) {
            this.physicsHandler.tick();
        }
        for (Collision collision : this.physicsHandler.getCollisions()) {
            this.oCollisionHandler(collision);
        }
        this.universe.update();
        if (this.camera != null) {
            this.world.setX(-this.camera.getX() + Main.Width() / 2 - this.camera.getWidth() / 2);
            this.world.setY(-this.camera.getY() + Main.Height() / 2 - this.camera.getHeight() / 2);
        }
        this.tock();
    }

    public void tock() {

    }

    @Override
    public void keyPress(KeyEvent key) {

    }

    @Override
    public void keyReleased(KeyEvent key) {

    }

    public final void draw() {
        this.background.draw();
        this.fgTilemap.draw();
        this.bgTilemap.draw();
        this.universe.drawNode();
        if (this.getSubscene() != null) {
            this.getSubscene().draw();
        }
    }

    public void addChild(Node node) {
        this.universe.addChild(node);
    }

    public void removeChild(Node node) {
        this.universe.removeChild(node);
    }

    public void enablePhysics() {
        this.physicsHandler = new PhysicsHandler();
    }

    public int getPhysicsSize() {
        return this.physicsHandler.getPhysicsSize();
    }

    public void addPhysicsNode(Node node) {
        this.physicsHandler.addPhysicsNode(node);
    }

    public void removePhysicsNode(Node node) {
        this.physicsHandler.removePhysicsNode(node);
    }

    public void setBorderColor(Color color) {
        if (this.background instanceof ColorBackground) {
            ColorBackground colorBackground = (ColorBackground) this.background;
            colorBackground.setColor(color);
        }
    }

    public void fillTilesFG(Tile t, int x, int y, int width, int height) {
        this.fgTilemap.fillTiles(x, y, width, height, t);
    }

    public void fillTilesBG(Tile t, int x, int y, int width, int height) {
        this.bgTilemap.fillTiles(x, y, width, height, t);
    }

    public void pause() {
        this.physicsHandler.pause();
    }

    public void unpause() {
        this.physicsHandler.unpause();
    }

    public void togglePause() {
        this.physicsHandler.togglePause();
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void subSceneUpdate() {
        this.background.setX(this.getDx());
        this.background.setY(this.getDy());
        this.background.setWidth(this.getWidth());
        this.background.setHeight(this.getHeight());
    }

    public void setWorldX(int x) {
        this.world.setX(x);
    }

    public void setWorldY(int y) {
        this.world.setY(y);
    }

    public Node getWorld() {
        return this.world;
    }

    public void addChildInWorld(Node node) {
        this.getWorld().addChild(node);
    }

    private final void oCollisionHandler(Collision collision) {
        if (collision.getVictim() instanceof Trigger) {
            ((Trigger) collision.getVictim()).trigger();
        } else {
            collision.getVictim().victimCollision(collision);
            ((Node) collision.getCauser()).causerCollision(collision);
        }
    }

    public void setGravitationalConstant(double g) {
        this.physicsHandler.setGravitation(g);
    }

    public void setCamera(Node node) {
        this.camera = node;
    }

    public void removeCamera() {
        this.camera = null;
    }

    public void setMultiTileFG(MultiTile mt, int x, int y) {
        this.fgTilemap.setMultiTile(mt, x, y);
    }

    public void setMultiTileBG(MultiTile mt, int x, int y) {
        this.bgTilemap.setMultiTile(mt, x, y);
    }

    public void mousePress() {
        Point inWorld = new Point(getMousePos().x - this.world.getX(), Main.Height() - (this.getMousePos().y + this.world.getY()));
        ArrayList<Node> nodes = this.physicsHandler.getNodesAt(inWorld.x, inWorld.y, 0, 0);
        if (nodes.size() != 0) {
            nodes.get(0).clickedOn();
        } else {
            Tile tile = this.fgTilemap.getTileRC(inWorld.x, inWorld.y);
            if (tile != null) {
                tile.clickedOn();
            }
        }
    }

    public void setWorld(World world) {
        this.fgTilemap = world.getFgTilemap();
        this.fgTilemap.setDynamicScene(this);
        this.physicsHandler.setTilemap(this.fgTilemap);
        this.bgTilemap = world.getBgTilemap();
        this.bgTilemap.setDynamicScene(this);
        this.fgTilemap.setZ(100);
        this.bgTilemap.setZ(50);
    }

}
