package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.scene.dynscene.tile.Tile;
import com.niopullus.NioLib.scene.dynscene.tile.Tilemap;
import com.niopullus.NioLib.utilities.Utilities;
import com.niopullus.app.Config;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
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

    public void setTilemap(Tilemap tilemap) {
        this.tilemap = tilemap;
    }

    public void tick() {
        if (!pause) {
            this.collisions = new ArrayList<Collision>();
            this.updateNodes();
            this.executeMoveCalcs();
            this.gravity();
            this.friction();
            this.moveNodes();
        }
    }

    public int getPhysicsSize() {
        return this.nodes.size();
    }

    public void addPhysicsNode(Node node) {
        this.nodes.add(node);
        Collections.sort(this.nodes);
    }

    public void removePhysicsNode(Node node) {
        this.nodes.remove(Collections.binarySearch(this.nodes, node));
        Collections.sort(this.nodes);
    }

    private void updateNodes() {
        for (Node node : this.nodes) {
            this.partition.updateNode(node);
        }
    }

    private void executeMoveCalcs() {
        for (Node node : this.nodes) {
            node.setColEast(this.calcMoveDist(node, Dir.E));
            node.setColWest(this.calcMoveDist(node, Dir.W));
            node.setColNorth(this.calcMoveDist(node, Dir.N));
            node.setColSouth(this.calcMoveDist(node, Dir.S));
        }
    }

    private void moveNodes() {
        for (Node node : this.nodes) {
            if (node.getXv() > 0) {
                if (node.getCollidableIn()) {
                    HalfCollision calc = node.getColEast();
                    int edist = calc.getDist();
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
                    HalfCollision calc = node.getColWest();
                    int wdist = calc.getDist();
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
                    HalfCollision calc = node.getColNorth();
                    int ndist = calc.getDist();
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
                    HalfCollision calc = node.getColSouth();
                    int sdist = calc.getDist();
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

    private HalfCollision calcMoveDistTiles(Node node, Dir dir) {
        HalfCollision col = new HalfCollision(NodePartition.AWARENESS_STANDARD, null);
        if (dir == Dir.E) {
            int awareness = tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getXv());
            Point p1 = this.tilemap.convertPointToTileLoc(node.getCMaxX(), node.getCMinY());
            Point p2 = this.tilemap.convertPointToTileLoc(node.getCMaxX() + awareness, node.getCMaxY() - 1);
            for (int x = p1.x; x <= p2.x && cont; x++) {
                for (int y = p1.y; y <= p2.y; y++) {
                    Tile t = this.tilemap.getTile(x, y);
                    if (t != null) {
                        int dist = (int) Math.floor((double) (x * this.tilemap.getTileSize()) - node.getCMaxX());
                        if (t.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, t);
                        }
                        if (dist <= 0 && (x * this.tilemap.getTileSize()) > node.getMidX()) {
                            this.collisions.add(new Collision(node, t, dir, !t.getCollidable()));
                        }
                    }
                }
            }
        } else if (dir == Dir.W) {
            int awareness = -tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getXv());
            Point p1 = this.tilemap.convertPointToTileLoc(node.getCMinX(), node.getCMinY());
            Point p2 = this.tilemap.convertPointToTileLoc(node.getCMinX() + awareness, node.getCMaxY() - 1);
            for (int x = p1.x; x >= p2.x && cont; x--) {
                for (int y = p1.y; y <= p2.y; y++) {
                    Tile t = this.tilemap.getTile(x, y);
                    if (t != null) {
                        int dist = (int) Math.floor(node.getCMinX() - (double) ((x + 1) * this.tilemap.getTileSize()));
                        if (t.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, t);
                        }
                        if (col.getDist() <= 0 && ((x + 1) * this.tilemap.getTileSize()) < node.getMidX()) {
                            this.collisions.add(new Collision(node, t, dir, !t.getCollidable()));
                        }
                    }
                }
            }
        } else if (dir == Dir.N) {
            int awareness = tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getYv());
            Point p1 = this.tilemap.convertPointToTileLoc(node.getCMinX(), node.getCMaxY());
            Point p2 = this.tilemap.convertPointToTileLoc(node.getCMaxX() - 1, node.getCMaxY() + awareness);
            for (int y = p1.y; y <= p2.y && cont; y++) {
                for (int x = p1.x; x <= p2.x; x++) {
                    Tile t = this.tilemap.getTile(x, y);
                    if (t != null) {
                        int dist = (int) Math.floor((double) (y * this.tilemap.getTileSize()) - node.getCMaxY());
                        if (t.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, t);
                        }
                        if (col.getDist() <= 0 && (y * this.tilemap.getTileSize()) > node.getMidY()) {
                            this.collisions.add(new Collision(node, t, dir, !t.getCollidable()));
                        }
                    }
                }
            }
        } else if (dir == Dir.S) {
            int awareness = -tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getYv());
            Point p1 = this.tilemap.convertPointToTileLoc(node.getCMinX(), node.getCMinY());
            Point p2 = this.tilemap.convertPointToTileLoc(node.getCMaxX() - 1, node.getCMinY() + awareness);
            for (int y = p1.y; y >= p2.y && cont; y--) {
                for (int x = p1.x; x <= p2.x; x++) {
                    Tile t = this.tilemap.getTile(x, y);
                    if (t != null) {
                        int dist = (int) Math.floor(node.getCMinY() - (double) ((y + 1) * this.tilemap.getTileSize()));
                        if (t.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, t);
                        }
                        if (col.getDist() <= 0 && (y + 1) * this.tilemap.getTileSize() < node.getMidY()) {
                            this.collisions.add(new Collision(node, t, dir, !t.getCollidable()));
                        }
                    }
                }
            }
        }
        return col;
    }

    private HalfCollision calcMoveDistNodes(Node node, Dir dir) {
        return this.partition.getHalfCollision(node, dir);
    }

    private HalfCollision calcMoveDist(Node node, Dir dir) {
        return Utilities.lesser(calcMoveDistNodes(node, dir), calcMoveDistTiles(node, dir));
    }

    private void gravity() {
        for (Node node: this.nodes) {
            if (node.getDoGravity()) {
                if (node.getCollidableIn()) {
                    int dist = node.getColSouth().getDist();
                    if (dist > 0) {
                        node.accelerateY(this.gravitation * node.getGravityCoefficient() / 4);
                    }
                } else {
                    node.accelerateY(this.gravitation * node.getGravityCoefficient() / 4);
                }
            }
        }
    }

    private void friction() {
        for (Node node : this.nodes) {
            if (node.getXv() != 0) {
                if (node.getCollidableIn()) {
                    HalfCollision nd = node.getColNorth();
                    HalfCollision sd = node.getColSouth();
                    double gravity = 0;
                    if (node.getDoGravity()) {
                        gravity = node.getMass() * this.gravitation / -20 * -Utilities.absoluteSign(node.getXv());
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
                    HalfCollision wd = node.getColWest();
                    HalfCollision ed = node.getColEast();
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

    public void setGravitation(double g) {
        this.gravitation = g;
    }

    public void pause() {
        this.pause = true;
    }

    public void unpause() {
        this.pause = false;
    }

    public void togglePause() {
        this.pause = !this.pause;
    }

    public ArrayList<Collision> getCollisions() {
        return this.collisions;
    }

    public void addCollision(Collision collision) {
        this.collisions.add(collision);
    }

    public ArrayList<Node> getNodesAt(int x, int y, int width, int height) {
        return this.partition.getNodesAt(x, y, width, height);
    }

}
