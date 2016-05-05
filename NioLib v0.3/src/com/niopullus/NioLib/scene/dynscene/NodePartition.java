package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Owen on 3/23/2016.
 */
public class NodePartition {

    private ArrayList<Node> nodes;
    private int x;
    private int y;
    private NodePartitionManager partitionManager;
    public static final int AWARENESS_STANDARD = 100;

    public NodePartition(int x, int y) {
        this.x = x;
        this.y = y;
        this.nodes = new ArrayList<Node>();
    }

    public void addNode(Node node) {
        this.nodes.add(node);
        Collections.sort(this.nodes);
    }

    public void removeNode(Node node) {
        this.nodes.remove(Collections.binarySearch(this.nodes, node));
        Collections.sort(this.nodes);
    }

    public HalfCollision getHalfCollision(Node node1, Dir dir) {
        int distance = this.AWARENESS_STANDARD;
        CollideData data = null;
        boolean override = false;
        if (dir == Dir.E) {
            for (Node node2 : this.nodes) {
                if (override) {
                    break;
                }
                if (node2 != node1) {
                    if (node2.getCMinY() < node1.getCMaxY() && node2.getCMaxY() > node1.getCMinY()) {
                        int diff = node2.getCMinX() - node1.getCMaxX();
                        if (node2.getCollidableOut() && diff >= 0 && diff < distance) {
                            distance = diff;
                            data = node2;
                            if (diff == 0) {
                                override = true;
                            }
                        }
                        if (diff <= 0 && node2.getCMinX() > node1.getCMidX()) {
                            this.partitionManager.addCollision(new Collision(node1, node2, dir, !node2.getCollidableOut()));
                        }
                    }
                }
            }
        } else if (dir == Dir.W) {
            for (Node node2 : this.nodes) {
                if (override) {
                    break;
                }
                if (node2 != node1) {
                    if (node2.getCMinY() < node1.getCMaxY() && node2.getCMaxY() > node1.getCMinY()) {
                        int diff = node1.getCMinX() - node2.getCMaxX();
                        if (node2.getCollidableOut() && diff >= 0 && diff < distance) {
                            distance = diff;
                            data = node2;
                            if (diff == 0) {
                                override = true;
                            }
                        }
                        if (diff <= 0 && node2.getCMaxX() < node1.getCMidX()) {
                            this.partitionManager.addCollision(new Collision(node1, node2, dir, !node2.getCollidableOut()));
                        }
                    }
                }
            }
        } else if (dir == Dir.N) {
            for (Node node2 : this.nodes) {
                if (override) {
                    break;
                }
                if (node2 != node1) {
                    if (node2.getCMaxX() > node1.getCMinX() && node2.getCMinX() < node1.getCMaxX()) {
                        int diff = node2.getCMinY() - node1.getCMaxY();
                        if (node2.getCollidableOut() && diff >= 0 && diff < distance) {
                            distance = diff;
                            data = node2;
                            if (diff == 0) {
                                override = true;
                            }
                        }
                        if (diff <= 0 && node2.getCMinY() > node1.getCMidY()) {
                            this.partitionManager.addCollision(new Collision(node1, node2, dir, !node2.getCollidableOut()));
                        }
                    }
                }
            }
        } else if (dir == Dir.S) {
            for (Node node2 : this.nodes) {
                if (override) {
                    break;
                }
                if (node2 != node1) {
                    if (node2.getCMaxX() > node1.getCMinX() && node2.getCMinX() < node1.getCMaxX()) {
                        int diff = node1.getCMinY() - node2.getCMaxY();
                        if (node2.getCollidableOut() && diff >= 0 && diff <= distance) {
                            distance = diff;
                            data = node2;
                            if (diff == 0) {
                                override = true;
                            }
                        }
                        if (diff <= 0 && node2.getCMaxY() < node2.getCMidY()) {
                            this.partitionManager.addCollision(new Collision(node1, node2, dir, !node2.getCollidableOut()));
                        }
                    }
                }
            }
        }
        return new HalfCollision(distance, data);
    }

    public void setPartitionManager(NodePartitionManager partitionManager) {
        this.partitionManager = partitionManager;
    }

    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    public ArrayList<Node> getNodes(Rectangle rect) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Node node : this.nodes) {
            if (Utilities.rectIntersect(rect, node.getWRect())) {
                nodes.add(node);
            }
        }
        return nodes;
    }

}
