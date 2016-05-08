package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.utilities.Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**Keeps track of nodes for a specific area
 * Created by Owen on 3/23/2016.
 */
public class NodePartition {

    private ArrayList<Node> nodes;
    private NodePartitionManager partitionManager;
    public static final int AWARENESS_STANDARD = 100; //Making this value too big could result in large amounts of lag

    public NodePartition(final int x, final int y) {
        this.nodes = new ArrayList<Node>();
    }

    public void addNode(final Node node) {
        nodes.add(node);
        Collections.sort(nodes);
    }

    public void removeNode(final Node node) {
        final int index = Collections.binarySearch(this.nodes, node);
        if (index != -1) {
            nodes.remove(index);
        } else {
            System.out.println("ERROR: TRIED TO REMOVE A NODE THAT IS NOT PRESENT");
        }
        Collections.sort(this.nodes);
    }

    public HalfCollision getHalfCollision(final Node node1, final Dir dir) {
        int distance = this.AWARENESS_STANDARD;
        CollideData data = null;
        boolean override = false;
        if (dir == Dir.E) {
            for (Node node2 : nodes) {
                if (override) {
                    break;
                }
                if (node2 != node1) {
                    if (node2.getCMinY() < node1.getCMaxY() && node2.getCMaxY() > node1.getCMinY()) {
                        final int diff = node2.getCMinX() - node1.getCMaxX();
                        if (node2.getCollidableOut() && diff >= 0 && diff < distance) {
                            distance = diff;
                            data = node2;
                            if (diff == 0) {
                                override = true;
                            }
                        }
                        if (diff <= 0 && node2.getCMinX() > node1.getCMidX()) {
                            partitionManager.addCollision(new Collision(node1, node2, dir, !node2.getCollidableOut()));
                        }
                    }
                }
            }
        } else if (dir == Dir.W) {
            for (Node node2 : nodes) {
                if (override) {
                    break;
                }
                if (node2 != node1) {
                    if (node2.getCMinY() < node1.getCMaxY() && node2.getCMaxY() > node1.getCMinY()) {
                        final int diff = node1.getCMinX() - node2.getCMaxX();
                        if (node2.getCollidableOut() && diff >= 0 && diff < distance) {
                            distance = diff;
                            data = node2;
                            if (diff == 0) {
                                override = true;
                            }
                        }
                        if (diff <= 0 && node2.getCMaxX() < node1.getCMidX()) {
                            partitionManager.addCollision(new Collision(node1, node2, dir, !node2.getCollidableOut()));
                        }
                    }
                }
            }
        } else if (dir == Dir.N) {
            for (Node node2 : nodes) {
                if (override) {
                    break;
                }
                if (node2 != node1) {
                    if (node2.getCMaxX() > node1.getCMinX() && node2.getCMinX() < node1.getCMaxX()) {
                        final int diff = node2.getCMinY() - node1.getCMaxY();
                        if (node2.getCollidableOut() && diff >= 0 && diff < distance) {
                            distance = diff;
                            data = node2;
                            if (diff == 0) {
                                override = true;
                            }
                        }
                        if (diff <= 0 && node2.getCMinY() > node1.getCMidY()) {
                            partitionManager.addCollision(new Collision(node1, node2, dir, !node2.getCollidableOut()));
                        }
                    }
                }
            }
        } else if (dir == Dir.S) {
            for (Node node2 : nodes) {
                if (override) {
                    break;
                }
                if (node2 != node1) {
                    if (node2.getCMaxX() > node1.getCMinX() && node2.getCMinX() < node1.getCMaxX()) {
                        final int diff = node1.getCMinY() - node2.getCMaxY();
                        if (node2.getCollidableOut() && diff >= 0 && diff <= distance) {
                            distance = diff;
                            data = node2;
                            if (diff == 0) {
                                override = true;
                            }
                        }
                        if (diff <= 0 && node2.getCMaxY() < node2.getCMidY()) {
                            partitionManager.addCollision(new Collision(node1, node2, dir, !node2.getCollidableOut()));
                        }
                    }
                }
            }
        }
        return new HalfCollision(distance, data);
    }

    public void setPartitionManager(final NodePartitionManager partitionManager) {
        this.partitionManager = partitionManager;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Node> getNodes(final Rectangle rect) {
        final ArrayList<Node> nodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (Utilities.rectIntersect(rect, node.getWRect())) {
                nodes.add(node);
            }
        }
        return nodes;
    }

}
