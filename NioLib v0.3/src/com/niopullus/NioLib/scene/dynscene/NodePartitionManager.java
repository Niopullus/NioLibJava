package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.utilities.SignedContainer;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**Manages the various Node Partitions
 * Created by Owen on 3/23/2016.
 */
public class NodePartitionManager implements Serializable {

    private int size;
    private SignedContainer<NodePartition> partitions;
    private PhysicsHandler physicsHandler;

    public NodePartitionManager(final int partSize, final int width, final int height) {
        this.size = partSize;
        this.partitions = new SignedContainer<NodePartition>(width, height);
    }

    public void updateNode(final Node node) {
        final Point partPointMin = convertPointToPart(node.getCMinX(), node.getCMidY());
        final Point partPointMax = convertPointToPart(node.getCMaxX(), node.getCMaxY());
        final boolean s1 = partPointMin.x != node.getPartRangeX()[0];
        final boolean s2 = partPointMax.x != node.getPartRangeX()[1];
        final boolean s3 = partPointMin.y != node.getPartRangeY()[0];
        final boolean s4 = partPointMax.y != node.getPartRangeY()[1];
        if (s1 || s2 || s3 || s4) {
            removeNode(node);
            addNode(node);
        }
    }

    private void addNode(final Node node) {
        final Point partPointMin = convertPointToPart(node.getCMinX(), node.getCMidY());
        final Point partPointMax = convertPointToPart(node.getCMaxX(), node.getCMaxY());
        node.setPartRangeX(new int[]{partPointMin.x, partPointMax.x});
        node.setPartRangeY(new int[]{partPointMin.y, partPointMax.y});
        for (int i = partPointMin.x; i <= partPointMax.x; i++) {
            for (int j = partPointMin.y; j <= partPointMax.y; j++) {
                if (partitions.get(i, j) == null) {
                    NodePartition part = new NodePartition();
                    part.setPartitionManager(this);
                    partitions.set(i, j, part);;
                }
                if (partitions.isValidLoc(i, j)) {
                    partitions.get(i, j).addNode(node);
                }
            }
        }
    }

    private void removeNode(final Node node) {
        for (int i = node.getPartRangeX()[0]; i < node.getPartRangeX()[1]; i++) {
            for (int j = node.getPartRangeY()[0]; j < node.getPartRangeY()[1]; j++) {
                if (partitions.isValidLoc(i, j)) {
                    partitions.get(i, j).removeNode(node);
                }
            }
        }
    }

    public Point convertPointToPart(final int x, final int y) {
        return new Point(x / size, y / size);
    }

    public HalfCollision getHalfCollision(final Node node, final Dir dir) {
        Point p1 = null;
        Point p2 = null;
        int distance = NodePartition.AWARENESS_STANDARD;
        HalfCollision result = new HalfCollision(distance, null);
        boolean override = false;
        if (dir == Dir.E) {
            p1 = convertPointToPart(node.getCMaxX(), node.getCMinY());
            p2 = convertPointToPart((int) (node.getCMaxX() + node.getXv()), node.getCMaxY());
        } else if (dir == Dir.W) {
            p1 = convertPointToPart((int) (node.getCMinX() + node.getXv()), node.getCMinY());
            p2 = convertPointToPart(node.getCMinX(), node.getCMaxY());
        } else if (dir == Dir.N) {
            p1 = convertPointToPart(node.getCMinX(), node.getCMaxY());
            p2 = convertPointToPart(node.getCMaxX(), (int) (node.getCMaxY() + node.getYv()));
        } else if (dir == Dir.S) {
            p1 = convertPointToPart(node.getCMinX(), node.getCMinY());
            p2 = convertPointToPart(node.getCMaxX(), (int) (node.getCMinY() + node.getYv()));
        }
        for (int i = p1.x; i <= p2.x && !override; i++) {
            for (int j = p1.y; j <= p2.y && !override; j++) {
                NodePartition part = partitions.get(i, j);
                if (part != null) {
                    final HalfCollision hc = part.getHalfCollision(node, dir);
                    if (hc.getDist() < distance) {
                        distance = hc.getDist();
                        result = hc;
                        if (hc.getDist() == 0) {
                            override = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    public void setPhysicsHandler(final PhysicsHandler physicsHandler) {
        this.physicsHandler = physicsHandler;
    }

    public void addCollision(final Collision collision) {
        this.physicsHandler.addCollision(collision);
    }

    public ArrayList<Node> getNodesAt(final int x, final int y, final int width, final int height) {
        final ArrayList<Node> nodes = new ArrayList<Node>();
        final Point p1 = convertPointToPart(x, y);
        final Point p2 = convertPointToPart(x + width, y + height);
        for (int i = p1.x; i <= p2.x; i++) {
            for (int j = p1.y; j <= p2.y; j++) {
                if (partitions.get(i, j) != null) {
                    if (i == p1.x || j == p2.y || i == p2.x || j == p2.y) {
                        nodes.addAll(partitions.get(i, j).getNodes(new Rectangle(x, y, width, height)));
                    } else {
                        nodes.addAll(partitions.get(i, j).getNodes());
                    }
                }
            }
        }
        return nodes;
    }

}
