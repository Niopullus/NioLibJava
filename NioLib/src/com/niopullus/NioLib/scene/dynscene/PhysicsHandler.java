package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.scene.dynscene.tile.Tile;
import com.niopullus.NioLib.scene.dynscene.tile.Tilemap;
import com.niopullus.NioLib.Utilities;
import com.niopullus.app.Config;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**Manages gravity, friction, and Node movement
 * Created by Owen on 3/10/2016.
 */
public class PhysicsHandler {

    private List<Node> nodes;
    private List<Collision> collisions;
    private Tilemap tilemap;
    private NodePartitionManager partition;
    private double gravitation;
    private boolean pause;

    public PhysicsHandler() {
        nodes = new ArrayList<>();
        gravitation = -10;
        pause = false;
    }

    public int getPhysicsSize() {
        return nodes.size();
    }

    public List<Collision> getCollisions() {
        return collisions;
    }

    public Tilemap getTilemap() {
        return tilemap;
    }

    public List<Node> getNodesAt(final int x, final int y, final int width, final int height) {
        return partition.getNodesAt(x, y, width, height);
    }

    public void setPartitionManager(final NodePartitionManager partitionManager) {
        partition = partitionManager;
        partitionManager.setPhysicsHandler(this);
    }

    public void setTilemap(final Tilemap _tilemap) {
        tilemap = _tilemap;
    }

    public void setGravitation(final double g) {
        gravitation = g;
    }

    public void addPhysicsNode(final Node node) {
        nodes.add(node);
        Collections.sort(nodes);
    }

    public void addCollision(final Collision collision) {
        collisions.add(collision);
    }

    public void removePhysicsNode(final Node node) {
        nodes.remove(node);
        Collections.sort(nodes);
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

    public void tick() {
        if (!pause) {
            collisions = new ArrayList<>();
            updateNodes();
            executeMoveCalcs();
            gravity();
            friction();
            moveNodes();
        }
    }

    private void updateNodes() {
        for (Node node : nodes) {
            partition.updateNode(node);
        }
    }

    private HalfCollision calcMoveDistNodes(final Node node, final Direction dir) {
        return partition.getHalfCollision(node, dir);
    }

    private HalfCollision calcMoveDist(final Node node, final Direction dir) {
        return Utilities.lesser(calcMoveDistNodes(node, dir), calcMoveDistTiles(node, dir));
    }

    private void executeMoveCalcs() {
        for (Node node : nodes) {
            node.setColEast(calcMoveDist(node, Direction.E));
            node.setColWest(calcMoveDist(node, Direction.W));
            node.setColNorth(calcMoveDist(node, Direction.N));
            node.setColSouth(calcMoveDist(node, Direction.S));
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

    private HalfCollision calcMoveDistTiles(final Node node, final Direction dir) {
        HalfCollision col = new HalfCollision(NodePartition.AWARENESS_STANDARD, null);
        if (dir == Direction.E) {
            final Point p1;
            final Point p2;
            int awareness = tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getXv());
            p1 = tilemap.convertPointToTileLoc(node.getCMaxX(), node.getCMinY());
            p2 = tilemap.convertPointToTileLoc(node.getCMaxX() + awareness, node.getCMaxY() - 1);
            for (int x = p1.x; x <= p2.x && cont; x++) {
                for (int y = p1.y; y <= p2.y; y++) {
                    final Tile tile = tilemap.getTile(x, y);
                    if (tile != null) {
                        final int dist = (int) Math.floor((double) (x * tilemap.getTileSize()) - node.getCMaxX());
                        if (tile.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, tile);
                        }
                        if (dist <= 0 && (x * tilemap.getTileSize()) > node.getMidX()) {
                            collisions.add(new Collision(node, tile, dir, !tile.getCollidable()));
                        }
                    }
                }
            }
        } else if (dir == Direction.W) {
            final Point p1;
            final Point p2;
            int awareness = -tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getXv());
            p1 = tilemap.convertPointToTileLoc(node.getCMinX(), node.getCMinY());
            p2 = tilemap.convertPointToTileLoc(node.getCMinX() + awareness, node.getCMaxY() - 1);
            for (int x = p1.x; x >= p2.x && cont; x--) {
                for (int y = p1.y; y <= p2.y; y++) {
                    final Tile tile = tilemap.getTile(x, y);
                    if (tile != null) {
                        final int dist = (int) Math.floor(node.getCMinX() - (double) ((x + 1) * tilemap.getTileSize()));
                        if (tile.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, tile);
                        }
                        if (col.getDist() <= 0 && ((x + 1) * tilemap.getTileSize()) < node.getMidX()) {
                            collisions.add(new Collision(node, tile, dir, !tile.getCollidable()));
                        }
                    }
                }
            }
        } else if (dir == Direction.N) {
            int awareness = tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getYv());
            final Point p1 = tilemap.convertPointToTileLoc(node.getCMinX(), node.getCMaxY());
            final Point p2 = tilemap.convertPointToTileLoc(node.getCMaxX() - 1, node.getCMaxY() + awareness);
            for (int y = p1.y; y <= p2.y && cont; y++) {
                for (int x = p1.x; x <= p2.x; x++) {
                    final Tile tile = tilemap.getTile(x, y);
                    if (tile != null) {
                        final int dist = (int) Math.floor((double) (y * tilemap.getTileSize()) - node.getCMaxY());
                        if (tile.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, tile);
                        }
                        if (col.getDist() <= 0 && (y * tilemap.getTileSize()) > node.getMidY()) {
                            collisions.add(new Collision(node, tile, dir, !tile.getCollidable()));
                        }
                    }
                }
            }
        } else if (dir == Direction.S) {
            int awareness = -tilemap.getTileSize() * 2;
            boolean cont = true;
            awareness += (int) Math.ceil(node.getYv());
            final Point p1 = tilemap.convertPointToTileLoc(node.getCMinX(), node.getCMinY());
            final Point p2 = tilemap.convertPointToTileLoc(node.getCMaxX() - 1, node.getCMinY() + awareness);
            for (int y = p1.y; y >= p2.y && cont; y--) {
                for (int x = p1.x; x <= p2.x; x++) {
                    final Tile tile = tilemap.getTile(x, y);
                    if (tile != null) {
                        int dist = (int) Math.floor(node.getCMinY() - (double) ((y + 1) * tilemap.getTileSize()));
                        if (tile.getCollidable()) {
                            cont = false;
                            col = new HalfCollision(dist, tile);
                        }
                        if (col.getDist() <= 0 && (y + 1) * tilemap.getTileSize() < node.getMidY()) {
                            collisions.add(new Collision(node, tile, dir, !tile.getCollidable()));
                        }
                    }
                }
            }
        }
        return col;
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

}
