package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Main;
import com.niopullus.NioLib.SignedContainer;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**Manages the various Node Partitions
 * Created by Owen on 3/23/2016.
 */
public class NodePartitionManager implements Serializable, Parcel {

    private int size;
    private SignedContainer<NodePartition> partitions;
    private PhysicsHandler physicsHandler;
    private Node world;
    private List<Node> drawnNodes;

    public NodePartitionManager(final int partSize, final int width, final int height) {
        size = partSize;
        partitions = new SignedContainer<>(width, height);
        drawnNodes = new ArrayList<>();
    }

    public void updateNode(final Node node) {
        if (node.getPartRangeX() != null && node.getPartRangeY() != null) {
            final Point partPointMin = convertPointToPartFloor(node.getCMinX(), node.getCMidY());
            final Point partPointMax = convertPointToPartCeil(node.getCMaxX(), node.getCMaxY());
            final boolean s1 = partPointMin.x != node.getPartRangeX()[0];
            final boolean s2 = partPointMax.x != node.getPartRangeX()[1];
            final boolean s3 = partPointMin.y != node.getPartRangeY()[0];
            final boolean s4 = partPointMax.y != node.getPartRangeY()[1];
            if (s1 || s2 || s3 || s4) {
                removeNode(node);
                addNode(node);
            }
        } else {
            addNode(node);
        }
    }

    private void addNode(final Node node) {
        final Point partPointMin = convertPointToPartFloor(node.getCMinX(), node.getCMidY());
        final Point partPointMax = convertPointToPartCeil(node.getCMaxX(), node.getCMaxY());
        node.setPartRangeX(new int[]{partPointMin.x, partPointMax.x});
        node.setPartRangeY(new int[]{partPointMin.y, partPointMax.y});
        for (int i = partPointMin.x; i <= partPointMax.x; i++) {
            for (int j = partPointMin.y; j <= partPointMax.y; j++) {
                if (partitions.get(i, j) == null) {
                    final NodePartition partition = new NodePartition();
                    partition.setPartitionManager(this);
                    partitions.set(i, j, partition);
                }
                if (partitions.isValidLoc(i, j)) {
                    final NodePartition partition = partitions.get(i, j);
                    partition.addNode(node);
                }
            }
        }
    }

    public void setWorld(final Node _world) {
        world = _world;
    }

    public void removeNode(final Node node) {
        for (int i = node.getPartRangeX()[0]; i <= node.getPartRangeX()[1]; i++) {
            for (int j = node.getPartRangeY()[0]; j <= node.getPartRangeY()[1]; j++) {
                if (partitions.isValidLoc(i, j)) {
                    final NodePartition partition = partitions.get(i, j);
                    partition.removeNode(node);
                }
            }
        }
    }

    public Point convertPointToPartCeil(final int x, final int y) {
        final int partX = (int) Math.ceil((double) x / size);
        final int partY = (int) Math.ceil((double) y / size);
        return new Point(partX, partY);
    }

    public Point convertPointToPartFloor(final int x, final int y) {
        final int partX = (int) Math.floor((double) x / size);
        final int partY = (int) Math.floor((double) y / size);
        return new Point(partX, partY);
    }

    public HalfCollision getHalfCollision(final Node node, final Direction dir) {
        Point p1 = null;
        Point p2 = null;
        int distance = NodePartition.AWARENESS_STANDARD;
        HalfCollision result = new HalfCollision(distance, null);
        boolean override = false;
        if (dir == Direction.E) {
            p1 = convertPointToPartFloor(node.getCMaxX(), node.getCMinY());
            p2 = convertPointToPartCeil((int) (node.getCMaxX() + node.getXv()), node.getCMaxY());
        } else if (dir == Direction.W) {
            p1 = convertPointToPartFloor((int) (node.getCMinX() + node.getXv()), node.getCMinY());
            p2 = convertPointToPartCeil(node.getCMinX(), node.getCMaxY());
        } else if (dir == Direction.N) {
            p1 = convertPointToPartFloor(node.getCMinX(), node.getCMaxY());
            p2 = convertPointToPartCeil(node.getCMaxX(), (int) (node.getCMaxY() + node.getYv()));
        } else if (dir == Direction.S) {
            p1 = convertPointToPartFloor(node.getCMinX(), node.getCMinY());
            p2 = convertPointToPartCeil(node.getCMaxX(), (int) (node.getCMinY() + node.getYv()));
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

    public void setPhysicsHandler(final PhysicsHandler _physicsHandler) {
        physicsHandler = _physicsHandler;
    }

    public void addCollision(final Collision collision) {
        physicsHandler.addCollision(collision);
    }

    public List<Node> getNodesAt(final int x, final int y, final int width, final int height) {
        final List<Node> nodes = new ArrayList<>();
        final Point p1 = convertPointToPartFloor(x, y);
        final Point p2 = convertPointToPartCeil(x + width, y + height);
        for (int i = p1.x; i <= p2.x; i++) {
            for (int j = p1.y; j <= p2.y; j++) {
                final NodePartition partition = partitions.get(i, j);
                if (partition != null) {
                    if (i == p1.x || j == p1.y || i == p2.x || j == p2.y) {
                        nodes.addAll(partitions.get(i, j).getNodes(new Rectangle(x, y, width, height)));
                    } else {
                        nodes.addAll(partitions.get(i, j).getNodes());
                    }
                }
            }
        }
        return nodes;
    }

    public void parcelDraw(final Canvas canvas) {
        final Point p1 = convertPointToPartFloor(-world.getX(), -world.getY());
        final Point p2 = convertPointToPartCeil(-world.getX() + Main.Width(), -world.getY() + Main.Height());
        for (int i = p1.x; i <= p2.x + 2; i++) {
            for (int j = p1.y; j < p2.y + 2; j++) {
                final NodePartition partition = partitions.get(i, j);
                if (partition != null) {
                    canvas.o.parcel(partition, 0, 0, 0, 0);
                }
            }
        }
        resetDrawnStates();
    }

    public void addDrawnNode(final Node node) {
        drawnNodes.add(node);
    }

    private void resetDrawnStates() {
        for (Node node : drawnNodes) {
            node.resetDrawnState();
        }
        drawnNodes = new ArrayList<>();
    }

}
