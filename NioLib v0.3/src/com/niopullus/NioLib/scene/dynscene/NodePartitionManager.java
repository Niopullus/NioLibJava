package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.utilities.SignedContainer;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Owen on 3/23/2016.
 */
public class NodePartitionManager implements Serializable {

    private int size;
    private SignedContainer<NodePartition> partitions;
    private PhysicsHandler physicsHandler;

    public NodePartitionManager(int partSize, int width, int height) {
        this.size = partSize;
        this.partitions = new SignedContainer(width, height);
    }

    public void updateNode(Node node) {
        Point partPointMin = this.convertPointToPart(node.getCMinX(), node.getCMidY());
        Point partPointMax = this.convertPointToPart(node.getCMaxX(), node.getCMaxY());
        if (partPointMin.x != node.getPartRangeX()[0] || partPointMax.x != node.getPartRangeX()[1] || partPointMin.y != node.getPartRangeY()[0] || partPointMax.y != node.getPartRangeY()[1]) {
            this.removeNode(node);
            this.addNode(node);
        }
    }

    private void addNode(Node node) {
        Point partPointMin = this.convertPointToPart(node.getCMinX(), node.getCMidY());
        Point partPointMax = this.convertPointToPart(node.getCMaxX(), node.getCMaxY());
        node.setPartRangeX(new int[]{partPointMin.x, partPointMax.x});
        node.setPartRangeY(new int[]{partPointMin.y, partPointMax.y});
        for (int i = partPointMin.x; i <= partPointMax.x; i++) {
            for (int j = partPointMin.y; j <= partPointMax.y; j++) {
                if (this.partitions.get(i, j) == null) {
                    NodePartition part = new NodePartition(i, j);
                    part.setPartitionManager(this);
                    this.partitions.set(i, j, part);;
                }
                if (this.partitions.isValidLoc(i, j)) {
                    this.partitions.get(i, j).addNode(node);
                }
            }
        }
    }

    private void removeNode(Node node) {
        for (int i = node.getPartRangeX()[0]; i < node.getPartRangeX()[1]; i++) {
            for (int j = node.getPartRangeY()[0]; j < node.getPartRangeY()[1]; j++) {
                if (this.partitions.isValidLoc(i, j)) {
                    this.partitions.get(i, j).removeNode(node);
                }
            }
        }
    }

    public Point convertPointToPart(int x, int y) {
        return new Point(x / size, y / size);
    }

    public HalfCollision getHalfCollision(Node node, Dir dir) {
        Point p1 = null;
        Point p2 = null;
        int distance = NodePartition.AWARENESS_STANDARD;
        HalfCollision result = new HalfCollision(distance, null);
        boolean override = false;
        if (dir == Dir.E) {
            p1 = this.convertPointToPart(node.getCMaxX(), node.getCMinY());
            p2 = this.convertPointToPart((int) (node.getCMaxX() + node.getXv()), node.getCMaxY());
        } else if (dir == Dir.W) {
            p1 = this.convertPointToPart((int) (node.getCMinX() + node.getXv()), node.getCMinY());
            p2 = this.convertPointToPart(node.getCMinX(), node.getCMaxY());
        } else if (dir == Dir.N) {
            p1 = this.convertPointToPart(node.getCMinX(), node.getCMaxY());
            p2 = this.convertPointToPart(node.getCMaxX(), (int) (node.getCMaxY() + node.getYv()));
        } else if (dir == Dir.S) {
            p1 = this.convertPointToPart(node.getCMinX(), node.getCMinY());
            p2 = this.convertPointToPart(node.getCMaxX(), (int) (node.getCMinY() + node.getYv()));
        }
        for (int i = p1.x; i <= p2.x && !override; i++) {
            for (int j = p1.y; j <= p2.y && !override; j++) {
                NodePartition part = this.partitions.get(i, j);
                if (part != null) {
                    HalfCollision hc = part.getHalfCollision(node, dir);
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

    public void setPhysicsHandler(PhysicsHandler physicsHandler) {
        this.physicsHandler = physicsHandler;
    }

    public void addCollision(Collision collision) {
        this.physicsHandler.addCollision(collision);
    }

    public ArrayList<Node> getNodesAt(int x, int y, int width, int height) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Point p1 = convertPointToPart(x, y);
        Point p2 = convertPointToPart(x + width, y + height);
        for (int i = p1.x; i <= p2.x; i++) {
            for (int j = p1.y; j <= p2.y; j++) {
                if (this.partitions.get(i, j) != null) {
                    if (i == p1.x || j == p2.y || i == p2.x || j == p2.y) {
                        nodes.addAll(this.partitions.get(i, j).getNodes(new Rectangle(x, y, width, height)));
                    } else {
                        nodes.addAll(this.partitions.get(i, j).getNodes());
                    }
                }
            }
        }
        return nodes;
    }

}
