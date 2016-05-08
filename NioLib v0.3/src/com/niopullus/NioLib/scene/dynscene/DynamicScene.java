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

/**A scene varient that is designed to support physics, tilemaps, and nodes
 * Created by Owen on 3/5/2016.
 */
public class DynamicScene extends Scene implements Serializable {

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

    public DynamicScene(final String name) {
        this.name = name;
        this.universe = new Node("universe");
        this.world = new Node("world");
        this.world.markWorld();
        this.universe.setDynamicScene(this);
        this.universe.addChild(world);
        this.universe.markUniverse();
        this.universe.setDynamicScene(this);
        this.background = new ColorBackground(0, 0, Main.Width(), Main.Height(), Color.WHITE);
        this.fgTilemap = new Tilemap(this, Config.TILESIZE, Config.TILEREGIONSIZE, Config.TILEMAPRAD, Config.TILEMAPRAD, Config.FGTILEMAPZ);
        this.bgTilemap = new Tilemap(this, Config.TILESIZE, Config.TILEREGIONSIZE, Config.TILEMAPRAD, Config.TILEMAPRAD, Config.BGTILEMAPZ);
        this.physicsHandler = new PhysicsHandler();
        this.physicsHandler.setTilemap(fgTilemap);
    }

    public final void tick() {
        if (physicsHandler != null) {
            physicsHandler.tick();
        }
        for (Collision collision : physicsHandler.getCollisions()) {
            oCollisionHandler(collision);
        }
        universe.update();
        if (camera != null) {
            final int x = -camera.getX() + Main.Width() / 2 - camera.getWidth() / 2;
            final int y = -camera.getY() + Main.Height() / 2 - camera.getHeight() / 2;
            world.setX(x);
            world.setY(y);
        }
        tock();
    }

    public void tock() {

    }

    public void keyPress(final KeyEvent key) {

    }

    public void keyReleased(final KeyEvent key) {

    }

    public final void draw() {
        Scene subscene;
        background.draw();
        fgTilemap.draw();
        bgTilemap.draw();
        universe.drawNode();
        subscene = getSubscene();
        if (subscene != null) {
            subscene.draw();
        }
    }

    public void addChild(final Node node) {
        universe.addChild(node);
    }

    public void removeChild(final Node node) {
        universe.removeChild(node);
    }

    public void enablePhysics() {
        physicsHandler = new PhysicsHandler();
    }

    public int getPhysicsSize() {
        return physicsHandler.getPhysicsSize();
    }

    public void addPhysicsNode(final Node node) {
        physicsHandler.addPhysicsNode(node);
    }

    public void removePhysicsNode(final Node node) {
        physicsHandler.removePhysicsNode(node);
    }

    public void setBorderColor(final Color color) {
        if (background instanceof ColorBackground) {
            ColorBackground colorBackground = (ColorBackground) background;
            colorBackground.setColor(color);
        }
    }

    public void fillTilesFG(final Tile t, final int x, final int y, final int width, final int height) {
        fgTilemap.fillTiles(x, y, width, height, t);
    }

    public void fillTilesBG(final Tile t, final int x, final int y, final int width, final int height) {
        bgTilemap.fillTiles(x, y, width, height, t);
    }

    public void pause() {
        physicsHandler.pause();
    }

    public void unpause() {
        physicsHandler.unpause();
    }

    public void togglePause() {
        physicsHandler.togglePause();
    }

    public void setBackground(final Background background) {
        this.background = background;
    }

    public void subSceneUpdate() {
        background.setX(getDx());
        background.setY(getDy());
        background.setWidth(getWidth());
        background.setHeight(getHeight());
    }

    public void setWorldX(final int x) {
        world.setX(x);
    }

    public void setWorldY(final int y) {
        world.setY(y);
    }

    public void setWorldPosition(final int x, final int y) {
        setWorldX(x);
        setWorldY(y);
    }

    public Node getWorld() {
        return world;
    }

    public void addChildInWorld(final Node node) {
        Node world = getWorld();
        world.addChild(node);
    }

    private void oCollisionHandler(final Collision collision) {
        final CollideData causer = collision.getCauser();
        final CollideData victim = collision.getVictim();
        if (victim instanceof Trigger) {
            Trigger trigger = (Trigger) victim;
            trigger.trigger();
        } else {
            victim.victimCollision(collision);
            causer.causerCollision(collision);
        }
    }

    public void setGravitationalConstant(final double g) {
        physicsHandler.setGravitation(g);
    }

    public void setCamera(final Node node) {
        camera = node;
    }

    public void removeCamera() {
        camera = null;
    }

    public void setMultiTileFG(final MultiTile multiTile, final int x, final int y) {
        fgTilemap.setMultiTile(multiTile, x, y);
    }

    public void setMultiTileBG(final MultiTile multiTile, final int x, final int y) {
        bgTilemap.setMultiTile(multiTile, x, y);
    }

    public void mousePress() {
        final int x = getMousePos().x - world.getX();
        final Point inWorld = new Point(x, Main.Height() - (this.getMousePos().y + this.world.getY()));
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
