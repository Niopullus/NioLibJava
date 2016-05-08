package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.scene.dynscene.tile.Tile;
import com.niopullus.NioLib.scene.dynscene.tile.Tilemap;
import com.niopullus.NioLib.utilities.Utilities;
import com.niopullus.app.Config;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**Manages gravity, friction, and Node movement
 * Created by Owen on 3/10/2016.
 */
public class PhysicsHandler implements Serializable {

    private ArrayList<Node> nodes;
    private double gravitation;
    private Tilemap tilemap;
    private NodePartitionManager partition;
    private boolean pause;
    private ArrayList<Collision> collisions;

    public PhysicsHandler() {
        this.nodes = new ArrayList<Node>();
        this.partition = new NodePartitionManager(Config.NODEPARTSIZE, Config.NODEPARTRAD, Config.NODEPARTRAD);
        this.gravitation = -10;
        this.pause = false;
        this.partition.setPhysicsHandler(this);
    }

    public void setTilemap(final Tilemap tilemap) {
        this.tilemap = tilemap;
    }

    public void tick() {
        if (!pause) {
            collisions = new ArrayList<Collision>();
            updateNodes();
            executeMoveCalcs();
            gravity();
            friction();
            moveNodes();
        }
    }

    public int getPhysicsSize() {
        return nodes.size();
    }

    public void addPhysicsNode(final Node node) {
        nodes.add(node);
        Collections.sort(nodes);
    }

    public void removePhysicsNode(final Node node) {
        nodes.remove(Collections.binarySearch(nodes, node));
        Collections.sort(nodes);
    }

    private void updateNodes() {
        for (Node node : nodes) {
            partition.updateNode(node);
        }
    }

    private void executeMoveCalcs() {
        for (Node node : nodes) {
            node.setColEast(calcMoveDist(node, Dir.E));
            node.setColWest(calcMoveDist(node, Dir.W));
            node.setColNorth(calcMoveDist(node, Dir.N));
            node.setColSouth(calcMoveDist(node, Dir.S));
        }
    }

    private void moveNodes() {
        for (Node node : nodes) {
            if (node.getXv() > 0) {
                if (node.getCollidableIn()) {
                    final HalfCollision calc = node.getColEast();
                    final int edist = calc.getDist();
                    if (edist > 0) {
                        if (edist >= Math.abs(node.getXv())) {
                            node.moveX();
                        } else {
                            node.moveX(edist);
                            node.setXv(node.getXv() * calc.getElasticity() * -1);
                        }
                    } else {
                        node.setXv(node.getXv() * calc.getElasticity() * -1);
                    }
                } else {
                    node.moveX();
                }

            } else if (node.getXv() < 0) {
                if (node.getCollidableIn()) {
                    final HalfCollision calc = node.getColWest();
                    final int wdist = calc.getDist();
                    if (wdist > 0) {
                        if (wdist >= Math.abs(node.getXv())) {
                            node.moveX();
                        } else {
                            node.moveX(-wdist);
                            node.setXv(node.getXv() * calc.getElasticity() * -1);
                        }
                    } else {
                        node.setXv(node.getXv() * calc.getElasticity() * -1);
                    }
                } else {
                    node.moveX();
                }
            }
            if (node.getYv() > 0) {
                if (node.getCollidableIn()) {
                    final HalfCollision calc = node.getColNorth();
                    final int ndist = calc.getDist();
                    if (ndist > 0) {
                        if (ndist >= Math.abs(node.getYv())) {
                            node.moveY();
                        } else {
                            node.moveY(ndist);
                            node.setYv(node.getYv() * calc.getElasticity() * -1);
                        }
                    } else {
                        node.setYv(node.getYv() * calc.getElasticity() * -1);
                    }
                } else {
                    node.moveY();
                }
            } else if (node.getYv() < 0) {
                if (node.getCollidableIn()) {
                    final HalfCollision calc = node.getColSouth();
                    final int sdist = calc.getDist();
                    if (sdist > 0) {
                        if (sdist >= Math.abs(node.getYv())) {
                            node.moveY();
                        } else {
                            node.moveY(-sdist);
                            node.setYv(node.getYv() * calc.getElasticity() * -1);
                        }
                    } else {
                        node.setYv(node.getYv() * calc.getElasticity() * -1);
                    }
                } else {
                    node.moveY();
                }
            }
        }
    }

    private HalfCollision calcMoveDistTiles(final Node node, final Dir dir) {
        HalfCollision col = new HalfCollision(NodePartition.AWARENESS_STANDARD, null);
        if (dir == Dir.E) {
            int awareness = tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getXv());
            final Point p1 = tilemap.convertPointToTileLoc(node.getCMaxX(), node.getCMinY());
            final Point p2 = tilemap.convertPointToTileLoc(node.getCMaxX() + awareness, node.getCMaxY() - 1);
            for (int x = p1.x; x <= p2.x && cont; x++) {
                for (int y = p1.y; y <= p2.y; y++) {
                    final Tile t = tilemap.getTile(x, y);
                    if (t != null) {
                        final int dist = (int) Math.floor((double) (x * tilemap.getTileSize()) - node.getCMaxX());
                        if (t.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, t);
                        }
                        if (dist <= 0 && (x * tilemap.getTileSize()) > node.getMidX()) {
                            collisions.add(new Collision(node, t, dir, !t.getCollidable()));
                        }
                    }
                }
            }
        } else if (dir == Dir.W) {
            int awareness = -tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getXv());
            final Point p1 = tilemap.convertPointToTileLoc(node.getCMinX(), node.getCMinY());
            final Point p2 = tilemap.convertPointToTileLoc(node.getCMinX() + awareness, node.getCMaxY() - 1);
            for (int x = p1.x; x >= p2.x && cont; x--) {
                for (int y = p1.y; y <= p2.y; y++) {
                    final Tile t = tilemap.getTile(x, y);
                    if (t != null) {
                        final int dist = (int) Math.floor(node.getCMinX() - (double) ((x + 1) * tilemap.getTileSize()));
                        if (t.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, t);
                        }
                        if (col.getDist() <= 0 && ((x + 1) * tilemap.getTileSize()) < node.getMidX()) {
                            collisions.add(new Collision(node, t, dir, !t.getCollidable()));
                        }
                    }
                }
            }
        } else if (dir == Dir.N) {
            int awareness = tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getYv());
            final Point p1 = tilemap.convertPointToTileLoc(node.getCMinX(), node.getCMaxY());
            final Point p2 = tilemap.convertPointToTileLoc(node.getCMaxX() - 1, node.getCMaxY() + awareness);
            for (int y = p1.y; y <= p2.y && cont; y++) {
                for (int x = p1.x; x <= p2.x; x++) {
                    final Tile t = tilemap.getTile(x, y);
                    if (t != null) {
                        final int dist = (int) Math.floor((double) (y * tilemap.getTileSize()) - node.getCMaxY());
                        if (t.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, t);
                        }
                        if (col.getDist() <= 0 && (y * tilemap.getTileSize()) > node.getMidY()) {
                            collisions.add(new Collision(node, t, dir, !t.getCollidable()));
                        }
                    }
                }
            }
        } else if (dir == Dir.S) {
            int awareness = -tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getYv());
            final Point p1 = tilemap.convertPointToTileLoc(node.getCMinX(), node.getCMinY());
            final Point p2 = tilemap.convertPointToTileLoc(node.getCMaxX() - 1, node.getCMinY() + awareness);
            for (int y = p1.y; y >= p2.y && cont; y--) {
                for (int x = p1.x; x <= p2.x; x++) {
                    final Tile t = tilemap.getTile(x, y);
                    if (t != null) {
                        int dist = (int) Math.floor(node.getCMinY() - (double) ((y + 1) * tilemap.getTileSize()));
                        if (t.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, t);
                        }
                        if (col.getDist() <= 0 && (y + 1) * tilemap.getTileSize() < node.getMidY()) {
                            collisions.add(new Collision(node, t, dir, !t.getCollidable()));
                        }
                    }
                }
            }
        }
        return col;
    }

    private HalfCollision calcMoveDistNodes(final Node node, final Dir dir) {
        return partition.getHalfCollision(node, dir);
    }

    private HalfCollision calcMoveDist(final Node node, final Dir dir) {
        return Utilities.lesser(calcMoveDistNodes(node, dir), calcMoveDistTiles(node, dir));
    }

    private void gravity() {
        for (Node node: nodes) {
            if (node.getDoGravity()) {
                if (node.getCollidableIn()) {
                    final HalfCollision collision = node.getColSouth();
                    final int dist = collision.getDist();
                    if (dist > 0) {
                        node.accelerateY(gravitation * node.getGravityCoefficient() / 4);
                    }
                } else {
                    node.accelerateY(gravitation * node.getGravityCoefficient() / 4);
                }
            }
        }
    }

    private void friction() {
        for (Node node : nodes) {
            if (node.getXv() != 0) {
                if (node.getCollidableIn()) {
                    final HalfCollision nd = node.getColNorth();
                    final HalfCollision sd = node.getColSouth();
                    double gravity = 0;
                    if (node.getDoGravity()) {
                        gravity = node.getMass() * gravitation / -20 * -Utilities.absoluteSign(node.getXv());
                    }
                    if (nd.getDist() == 0) {
                        if (Math.abs(node.getXv()) >= 0.5 * nd.getFriction() + Math.abs(gravity)) {
                            node.accelerateX(0.5 * nd.getFriction() * -Utilities.absoluteSign(node.getXv()) + gravity);
                        } else {
                            node.setXv(0);
                        }
                    }
                    if (sd.getDist() == 0) {
                        if (Math.abs(node.getXv()) >= 0.5 * sd.getFriction() + Math.abs(gravity)) {
                            node.accelerateX(0.5 * sd.getFriction() * -Utilities.absoluteSign(node.getXv()) + gravity);
                        } else {
                            node.setXv(0);
                        }
                    }
                }
            }
            if (node.getYv() != 0) {
                if (node.getCollidableIn()) {
                    final HalfCollision wd = node.getColWest();
                    final HalfCollision ed = node.getColEast();
                    if (ed.getDist() == 0) {
                        if (Math.abs(node.getYv()) >= 0.5 * ed.getFriction()) {
                            node.accelerateY(0.5 * ed.getFriction() * -Utilities.absoluteSign(node.getYv()));
                        } else {
                            node.setYv(0);
                        }
                    }
                    if (wd.getDist() == 0) {
                        if (Math.abs(node.getYv()) >= 0.5 * wd.getFriction()) {
                            node.accelerateY(0.5 * wd.getFriction() * -Utilities.absoluteSign(node.getYv()));
                        } else {
                            node.setYv(0);
                        }
                    }
                }
            }
        }
    }

    public void setGravitation(final double g) {
        gravitation = g;
    }

    public void pause() {
        pause = true;
    }

    public void unpause() {
        pause = false;
    }

    public void togglePause() {
        pause = !pause;
    }

    public ArrayList<Collision> getCollisions() {
        return collisions;
    }

    public void addCollision(final Collision collision) {
        collisions.add(collision);
    }

    public ArrayList<Node> getNodesAt(final int x, final int y, final int width, final int height) {
        return partition.getNodesAt(x, y, width, height);
    }

}
