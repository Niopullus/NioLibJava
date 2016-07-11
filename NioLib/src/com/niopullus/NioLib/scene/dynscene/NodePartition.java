package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Utilities;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**Keeps track of nodes for a specific area
 * Created by Owen on 3/23/2016.
 */
public class NodePartition implements Parcel {

    private List<NodeEntry> nodes;
    private List<Node> collidableNodes;
    private NodePartitionManager partitionManager;
    public static final int AWARENESS_STANDARD = 100;

    public NodePartition() {
        nodes = new ArrayList<>();
        collidableNodes = new ArrayList<>();
    }

    public List<Node> getNodes() {
        final List<Node> _nodes = new ArrayList<>();
        for (NodeEntry entry : nodes) {
            _nodes.add(entry.node);
        }
        return _nodes;
    }

    public List<Node> getNodes(final Rectangle rect) {
        final List<Node> nodes = new ArrayList<>();
        for (Node node : nodes) {
            if (Utilities.rectIntersect(rect, node.getWRect())) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    public HalfCollision getHalfCollision(final Node node1, final Direction dir) {
        int distance = AWARENESS_STANDARD;
        CollideData data = null;
        boolean override = false;
        if (dir == Direction.E) {
            for (Node node2 : collidableNodes) {
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
        } else if (dir == Direction.W) {
            for (Node node2 : collidableNodes) {
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
        } else if (dir == Direction.N) {
            for (Node node2 : collidableNodes) {
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
        } else if (dir == Direction.S) {
            for (Node node2 : collidableNodes) {
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

    public void setPartitionManager(final NodePartitionManager _partitionManager) {
        partitionManager = _partitionManager;
    }

    public void addNode(final Node node) {
        final NodeEntry entry = new NodeEntry();
        entry.node = node;
        entry.collidable = node.getCollidableOut();
        nodes.add(entry);
        Collections.sort(nodes);
        if (entry.collidable) {
            collidableNodes.add(node);
            Collections.sort(collidableNodes);
        }
    }

    public void removeNode(final Node node) {
        int index;
        NodeEntry entry = null;
        final NodeEntry key = new NodeEntry();
        key.node = node;
        index = Collections.binarySearch(nodes, key);
        if (index != -1) {
            entry = nodes.remove(index);
        } else {
            System.out.println("ERROR: TRIED TO REMOVE A NODE THAT IS NOT PRESENT");
        }
        Collections.sort(nodes);
        if (entry.collidable) {
            index = Collections.binarySearch(collidableNodes, node);
            if (index != -1) {
                collidableNodes.remove(index);
            } else {
                System.out.println("ERROR: TRIED TO REMOVE A NODE THAT IS NOT PRESENT");
            }
        }
    }

    public void parcelDraw(final Canvas canvas) {
        for (NodeEntry entry : nodes) {
            final Node node = entry.node;
            canvas.o.parcel(node, node.getX(), node.getY(), node.getWidth(), node.getHeight(), node.getZ(), node.getAngle(), 1f);
        }
    }

    private class NodeEntry implements Comparable<NodeEntry> {

        public Node node;
        public boolean collidable;

        public int compareTo(final NodeEntry entry) {
            return node.compareTo(entry.node);
        }

    }

}
