package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.draw.Draw;
import com.niopullus.NioLib.scene.Background;
import com.niopullus.NioLib.scene.ColorBackground;
import com.niopullus.NioLib.scene.NodeHandler;
import com.niopullus.NioLib.scene.Scene;
import com.niopullus.NioLib.scene.dynscene.tile.MultiTile;
import com.niopullus.NioLib.scene.dynscene.tile.Tile;
import com.niopullus.NioLib.scene.dynscene.tile.Tilemap;
import com.niopullus.app.Config;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**A scene variant that is designed to support physics, tilemaps, and nodes
 * Created by Owen on 3/5/2016.
 */
public class DynamicScene extends Scene implements NodeHandler {

    private String name;
    private Node universe;
    private Node world;
    private PhysicsHandler physicsHandler;
    private Background background;
    private Tilemap fgTilemap;
    private Tilemap bgTilemap;
    private Node camera;

    public DynamicScene(final String name, final World world) {
        this.name = name;
        this.universe = world.getUniverse();
        this.physicsHandler = world.getPhysicsHandler();
        this.background = world.getBackground();
        this.fgTilemap = world.getFgTilemap();
        this.bgTilemap = world.getBgTilemap();
        this.camera = world.getCamera();
    }

    public DynamicScene(final World world) {
        this(world.getName(), world);
    }

    public int getPhysicsSize() {
        return physicsHandler.getPhysicsSize();
    }

    public Node getWorld() {
        return world;
    }

    public PhysicsHandler getPhysicsHandler() {
        return physicsHandler;
    }

    public String getName() {
        return name;
    }

    public int getNodeCount() {
        return universe.getChildCount();
    }

    public Point getMousePosInWorld() {
        final Point result = new Point();
        final Point mousePos = getMousePos();
        result.x = mousePos.x - world.getX();
        result.y = mousePos.y - world.getY();
        return result;
    }

    public void setGravitationalConstant(final double g) {
        physicsHandler.setGravitation(g);
    }

    public void setCamera(final Node node) {
        camera = node;
    }

    public void setWorldX(final int x) {
        world.setX(x);
    }

    public void setWorldY(final int y) {
        world.setY(y);
    }

    public void setBackground(final Background background) {
        this.background = background;
    }

    public void setMultiTileFG(final MultiTile multiTile, final int x, final int y) {
        fgTilemap.setMultiTile(multiTile, x, y);
    }

    public void setMultiTileBG(final MultiTile multiTile, final int x, final int y) {
        bgTilemap.setMultiTile(multiTile, x, y);
    }

    public void setWorldPosition(final int x, final int y) {
        setWorldX(x);
        setWorldY(y);
    }

    public void setBorderColor(final Color color) {
        if (background instanceof ColorBackground) {
            ColorBackground colorBackground = (ColorBackground) background;
            colorBackground.setColor(color);
        }
    }

    public void setWorld(final World world) {
        fgTilemap = world.getFgTilemap();
        bgTilemap = world.getBgTilemap();
        fgTilemap.setScene(this);
        bgTilemap.setScene(this);
        fgTilemap.setZ(Config.FGTILEMAPZ);
        bgTilemap.setZ(Config.BGTILEMAPZ);
        physicsHandler.setTilemap(fgTilemap);
    }

    public final void tick() { //Called at the same rate that the game loop runs
        final List<Collision> collisions = physicsHandler.getCollisions();
        if (physicsHandler != null) {
            physicsHandler.tick();
        }
        for (Collision collision : collisions) {
            oCollisionHandler(collision);
        }
        universe.update();
        if (camera != null) {
            final int x = -camera.getX() + Main.Width() / 2 - camera.getWidth() / 2;
            final int y = -camera.getY() + Main.Height() / 2 - camera.getHeight() / 2;
            world.setX(x);
            world.setY(y);
        }
        update();
    }

    public void update() {
        //To be overridden
    }

    public void keyPress(final KeyEvent key) {
        //To be overridden
    }

    public void keyReleased(final KeyEvent key) {
        //To be overridden
    }

    public final void draw() {
        final Scene subscene;
        background.draw(0, 0, 0, Draw.DrawMode.ORIGIN);
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

    public void addPhysicsNode(final Node node) {
        physicsHandler.addPhysicsNode(node);
    }

    public void removePhysicsNode(final Node node) {
        physicsHandler.removePhysicsNode(node);
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

    public void addChildInWorld(final Node node) {
        final Node world = getWorld();
        world.addChild(node);
    }

    private void oCollisionHandler(final Collision collision) {
        final CollideData causer = collision.getCauser();
        final CollideData victim = collision.getVictim();
        if (victim instanceof Trigger) {
            final Trigger trigger = (Trigger) victim;
            trigger.trigger(victim);
        } else {
            victim.victimCollision(collision);
            causer.causerCollision(collision);
        }
    }

    public void removeCamera() {
        camera = null;
    }

    public void mousePress() {
        final Point inWorld = getMousePosInWorld();
        final ArrayList<Node> nodes = physicsHandler.getNodesAt(inWorld.x, inWorld.y, 0, 0);
        if (nodes.size() != 0) {
            final Node node = nodes.get(0);
            node.clickedOn();
        } else {
            final Tile tile = fgTilemap.getTileRC(inWorld.x, inWorld.y);
            if (tile != null) {
                tile.clickedOn();
            }
        }
    }

}
