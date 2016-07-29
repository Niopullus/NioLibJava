package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.*;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.scene.NodeHandler;
import com.niopullus.NioLib.scene.dynscene.reference.NodeReference;
import com.niopullus.NioLib.scene.dynscene.reference.Ref;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**Polymorphic sprite designed for a Dynamic Scene.
 * All significant instance variables should be registered by overriding
 * the integrate() method. When overriding a integrate, always call super.
 * Inside that method, manually copy all instance variables over from
 * 'this' to the node passed in as a parameter.
 * Modify saving/loading by overriding the crush() and uncrush(DataTree)
 * methods.
 * Created by Owen on 3/5/2016.
 */
public class Node implements Comparable<Node>, CollideData, Boundable, Crushable, Parcel {

    private UUID id;
    private int x;
    private int y;
    private int z;
    private List<Node> children;
    private Node parent;
    private boolean isUniverse;
    private boolean isWorld;
    private NodeHandler scene;
    private PhysicsData physicsData;
    private int[] partRangeX;
    private int[] partRangeY;
    private int width;
    private int height;
    private double xScale;
    private double yScale;
    private int oWidth;
    private int oHeight;
    private int cx;
    private int cy;
    private int cwidth;
    private int cheight;
    private Point movePos;
    private int moveSpeed;
    private double angle;
    private NodeReference reference;
    private DataTree data;
    private NodePartitionManager partitionManager;
    private boolean drawn;

    public Node() {
        this("unnamedNode");
    }

    public Node(final String name) {
        this(name, 0, 0);
    }

    public Node(final String name, final int _width, final int _height) {
        children = new ArrayList<>();
        id = new UUID(name);
        physicsData = new PhysicsData();
        width = _width;
        height = _height;
        partRangeX = null;
        partRangeY = null;
        cx = 0;
        cy = 0;
        cwidth = 0;
        cheight = 0;
        reference = null;
        data = new DataTree();
        drawn = false;
    }

    public NodeReference getReference() {
        return reference;
    }

    public UUID getId() {
        return id;
    }

    public Node getParent() {
        return parent;
    }

    public int ogetChildrenCount() {
        return children.size();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean isEnablePhysics() {
        return physicsData.getEnablePhysics();
    }

    public double getXv() {
        return physicsData.getXv();
    }

    public double getYv() {
        return physicsData.getYv();
    }

    public double getElasticity() {
        return physicsData.getElasticity();
    }

    public double getFriction() {
        return physicsData.getFriction();
    }

    public double getMass() {
        return physicsData.getMass();
    }

    public double getxStrength() {
        return physicsData.getxStrength();
    }

    public double getyStrength() {
        return physicsData.getyStrength();
    }

    public double getxSpeedLim() {
        return physicsData.getxSpeedLim();
    }

    public double getySpeedLim() {
        return physicsData.getySpeedLim();
    }

    public boolean getDoGravity() {
        return physicsData.getDoGravity();
    }

    public double getGravityCoefficient() {
        return physicsData.getGravityCoefficient();
    }

    public HalfCollision getColEast() {
        return physicsData.getColEast();
    }

    public HalfCollision getColWest() {
        return physicsData.getColWest();
    }

    public HalfCollision getColNorth() {
        return physicsData.getColNorth();
    }

    public HalfCollision getColSouth() {
        return physicsData.getColSouth();
    }

    public int getTMinX() {
        return getTX();
    }

    public int getTMinY() {
        return getTY();
    }

    public int getTMaxX() {
        return getTX() + getWidth();
    }

    public int getTMaxY() {
        return getTY() + getHeight();
    }

    public int getTMidX() {
        return getTX() + getWidth() / 2;
    }

    public int getTMidY() {
        return getTY() + getHeight() / 2;
    }

    public boolean getCollidableOut() {
        return physicsData.getCollidableOut();
    }

    public int[] getPartRangeX() {
        return partRangeX;
    }

    public int[] getPartRangeY() {
        return partRangeY;
    }

    public boolean getCollidableIn() {
        return physicsData.getCollidableIn();
    }

    public double getXScale() {
        return xScale;
    }

    public double getYScale() {
        return yScale;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int ogetWidth() {
        return oWidth;
    }

    public int ogetHeight() {
        return oHeight;
    }

    public Point getPosition() {
        return new Point(getX(), getY());
    }

    public Point ogetPosition() {
        return new Point(x, y);
    }

    public String getName() {
        return id.getName();
    }

    public int getCx() {
        return getWX() + cx;
    }

    public int getCy() {
        return getWY() + cy;
    }

    public int getCwidth() {
        return width + cwidth;
    }

    public int getCheight() {
        return height + cheight;
    }

    public int getCMinX() {
        return getCx();
    }

    public int getCMinY() {
        return getCy();
    }

    public int getCMaxX() {
        return getCx() + getCwidth();
    }

    public int getCMaxY() {
        return getCy() + getCheight();
    }

    public int getCMidX() {
        return getCx() + getCwidth() / 2;
    }

    public int getCMidY() {
        return getCy() + getCheight() / 2;
    }

    public double getAngle() {
        return angle;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getCRect() {
        return new Rectangle(getCx(), getCy(), getCwidth(), getCheight());
    }

    public Rectangle getWRect() {
        return new Rectangle(getWX(), getWY(), getWidth(), getHeight());
    }

    public boolean isUniverse() {
        return isUniverse;
    }

    public NodeHandler getScene() {
        if (parent == null) {
            return scene;
        } else {
            return parent.getScene();
        }
    }

    public Node getChild(final int index) {
        return children.get(index);
    }

    public int getChildCount() {
        int count = 0;
        for (Node child : children) {
            count++;
            count += child.getChildCount();
        }
        return count;
    }

    public int getTX() {
        if (isUniverse) {
            return x;
        } else {
            return x + parent.getTX();
        }
    }

    public int getTY() {
        if (isUniverse) {
            return y;
        } else {
            return y + parent.getTY();
        }
    }

    public int getWX() {
        if (isWorld || isUniverse()) {
            return 0;
        } else {
            return parent.getWX() + x;
        }
    }

    public int getWY() {
        if (isWorld || isUniverse) {
            return 0;
        } else {
            return parent.getWY() + y;
        }
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setCX(final int _cx) {
        cx = _cx;
        updateNode();
    }

    public void setCY(final int _cy) {
        cy = _cy;
        updateNode();
    }

    public void setCWidth(final int _cwidth) {
        cwidth = _cwidth;
        updateNode();
    }

    public void setCHeight(final int _cheight) {
        cheight = _cheight;
        updateNode();
    }

    public void setX(final int _x) {
        x = _x;
        updateNode();
    }

    public void setY(final int _y) {
        y = _y;
        updateNode();
    }

    public void setZ(final int _z) {
        z = _z;
    }

    public void setPartitionManager(final NodePartitionManager manager) {
        partitionManager = manager;
    }

    public void setXv(final double xv) {
        physicsData.setXv(xv);
    }

    public void setYv(final double yv) {
        physicsData.setYv(yv);
    }

    public void setScene(final NodeHandler _scene) {
        scene = _scene;
    }

    public void setGravityCoefficient(final double coefficient) {
        physicsData.setGravityCoefficient(coefficient);
    }

    public void setElasticity(final double elasticity) {
        physicsData.setElasticity(elasticity);
    }

    public void setFriction(final double friction) {
        physicsData.setFriction(friction);
    }

    public void setMass(final double mass) {
        physicsData.setMass(mass);
    }

    public void setxStrength(final double xStrength) {
        physicsData.setxStrength(xStrength);
    }

    public void setyStrength(final double yStrength) {
        physicsData.setyStrength(yStrength);
    }

    public void setxSpeedLim(final double xSpeedLim) {
        physicsData.setxSpeedLim(xSpeedLim);
    }

    public void setySpeedLim(final double ySpeedLim) {
        physicsData.setySpeedLim(ySpeedLim);
    }

    public void disableGravity() {
        physicsData.disableGravity();
    }

    public void enableGravity() {
        physicsData.enableGravity();
    }

    public void setColEast(final HalfCollision halfCollision) {
        physicsData.setColEast(halfCollision);
    }

    public void setColWest(final HalfCollision halfCollision) {
        physicsData.setColWest(halfCollision);
    }

    public void setColNorth(final HalfCollision halfCollision) {
        physicsData.setColNorth(halfCollision);
    }

    public void setColSouth(final HalfCollision halfCollision) {
        physicsData.setColSouth(halfCollision);
    }

    public void setPartRangeX(final int[] rangeX) {
        partRangeX = rangeX;
    }

    public void setPartRangeY(final int[] rangeY) {
        partRangeY = rangeY;
    }

    public void enableCollisionIn() {
        physicsData.enableCollisionIn();
    }

    public void disableCollisionIn() {
        physicsData.disableCollisionIn();
    }

    public void enableCollisionOut() {
        physicsData.enableCollisionOut();
    }

    public void disableCollisionOut() {
        physicsData.disableCollisionOut();
    }

    public void setWidth(final int _width) {
        width = _width;
    }

    public void setHeight(final int _height) {
        height = _height;
    }

    public void csetXScale(final double _xScale) {
        xScale = _xScale;
    }

    public void csetYScale(final double _yScale) {
        yScale = _yScale;
    }

    public void osetWidth(final int width) {
        oWidth = width;
    }

    public void osetHeight(final int height) {
        oHeight = height;
    }

    public void removeParent() {
        parent = null;
    }

    public void markUniverse() {
        isUniverse = true;
    }

    public void markWorld() {
        isWorld = true;
    }

    public void markDrawn() {
        drawn = true;
    }

    public void setRotatation(final double _angle) {
        angle = _angle;
    }

    public void setReference(final NodeReference _reference) {
        reference = _reference;
    }

    public void setName(final String name) {
        id = new UUID(name);
    }

    public void setSize(final int _width, final int _height) {
        width = _width;
        height = _height;
    }

    public void setXScale(final double _xScale) {
        xScale = _xScale;
        width = (int) (oWidth * xScale);
    }

    public void setYScale(final double _yScale) {
        yScale = _yScale;
        height = (int) (oHeight * yScale);
    }

    public void rotate(final double _angle) {
        angle += _angle;
    }

    public void setPosition(final int x, final int y) {
        setX(x);
        setY(y);
    }

    public void resetDrawnState() {
        drawn = false;
    }

    public void update() {
        for (Node node : children) {
            node.update();
        }
    }

    public void addChild(final Node node) {
        node.parent = this;
        node.scene = scene;
        children.add(node);
        Collections.sort(children);
        if (node.physicsData.getEnablePhysics() && scene != null && scene instanceof DynamicScene) {
            final DynamicScene dynamicScene = (DynamicScene) scene;
            dynamicScene.addPhysicsNode(node);
        }
        node.updateNode();
    }

    public void removeChild(final Node node) {
        final int index = Collections.binarySearch(children, node);
        if (index >= 0) {
            children.remove(index);
            deleteNode(node);
        } else {
            System.out.println("ERROR: TRIED TO REMOVE NODE THAT WAS NOT PRESENT");
        }
    }

    public void enablePhysics() {
        if (scene != null && scene instanceof DynamicScene) {
            final DynamicScene dynamicScene = (DynamicScene) scene;
            dynamicScene.addPhysicsNode(this);
        }
        physicsData.setEnablePhysics(true);
    }

    public void disablePhysics() {
        if (scene != null && scene instanceof DynamicScene) {
            final DynamicScene dynamicScene = (DynamicScene) scene;
            dynamicScene.removePhysicsNode(this);
        }
        physicsData.setEnablePhysics(false);
    }

    public void accelerate(final double xacc, final double yacc) {
        physicsData.accelerate(xacc, yacc);
    }

    public void accelerateX(double acceleration) {
        physicsData.accelerateX(acceleration);
    }

    public void accelerateY(double acceleration) {
        physicsData.accelerateY(acceleration);
    }

    public void moveX(final int deltaX) {
        setX(x + deltaX);
    }

    public void moveX() {
        setX(x + (int) physicsData.getXv());
    }

    public void moveY(int deltaY) {
        setY(y + deltaY);
    }

    public void moveY() {
        setY(y + (int) physicsData.getYv());
    }

    public void move(final int deltaX, final int deltaY) {
        moveX(deltaX);
        moveY(deltaY);
    }

    public void oMoveTo(final int x, final int y, final int speed) {
        movePos = new Point(x, y);
        moveSpeed = speed;
    }

    public void addCX(final int delta) {
        cx += delta;
    }

    public void addCY(final int delta) {
        cy += delta;
    }

    public void addCWidth(final int delta) {
        cwidth += delta;
    }

    public void addCHeight(final int delta) {
        cheight += delta;
    }

    public double distanceTo(final Point p) {
        return Math.sqrt(Math.pow(p.x - (x + width / 2), 2) + Math.pow(p.y - (y + height / 2), 2));
    }

    public double distanceToW(final Point p) {
        return Math.sqrt(Math.pow(p.x - (getWX() + width / 2), 2) + Math.pow(p.y - (getWY() + height / 2), 2));
    }

    public double distanceToW(final Node node) {
        return Math.sqrt(Math.pow(node.getWX() - (getWX() + width / 2), 2) + Math.pow(node.getWY() - (getWY() + height / 2), 2));
    }

    public void updateNode() {
        updateNode(this);
    }

    public void updateNode(final Node node) {
        if (parent == null) {
            if (partitionManager != null) {
                partitionManager.updateNode(node);
            }
        } else {
            parent.updateNode(node);
        }
    }

    public void wideUpdate() {
        updateNode();
        for (Node child : children) {
            child.wideUpdate();
        }
    }

    public void deleteNode(final Node node) {
        if (parent == null) {
            if (partitionManager != null) {
                partitionManager.removeNode(node);
            }
        } else {
            parent.deleteNode(node);
        }
    }

    public void parcelDraw(final Canvas canvas) {
        //To be overridden
    }

    public void clickedOn() {
        //To be overridden
    }

    public void removeFromParent() {
        parent.removeChild(this);
    }

    public void causerCollision(final Collision collision) {
        //To be overridden
    }

    public void victimCollision(final Collision collision) {
        //To be overridden
    }

    public int compareTo(final Node node) {
        return id.compareTo(node.getId());
    }

    public Node copy() {
        try {
            final Class<?> nodeClass = getClass();
            final Node node = (Node) nodeClass.newInstance();
            node.integrate(this);
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void integrate(final Node node) {
        id = new UUID(node.getName());
        width = node.width;
        height = node.height;
        reference = node.reference;
        physicsData = node.physicsData.copy();
        data = node.data.copy();
    }

    /**
     * Crush Diagram:
     * root {
     *     i - id
     *     f - nodeData
     *     f - details {
     *         i - x
     *         i - y
     *         d - angle
     *         f - physicsData
     *         i - nodeState
     *     }
     *     f - children
     * }
     */
    public DataTree crush() {
        final DataTree result = new DataTree();
        result.addData(reference != null ? reference.getId() : 0);
        result.addData(data);
        result.addFolder();
        result.addData(x, 2);
        result.addData(y, 2);
        result.addData(angle, 2);
        result.addData(physicsData, 2);
        result.addData(isUniverse ? 2: isWorld ? 1: 0, 2);
        result.addData(children);
        return result;
    }

    public Node uncrush(final DataTree data, final NodeHandler scene) {
        final int id = data.getI(0);
        final DataTree nodeData = new DataTree(data.getF(1));
        final int x = data.getI(2, 0);
        final int y = data.getI(2, 1);
        final double angle = data.getD(2, 2);
        final PhysicsData physicsData = PhysicsData.uncrush(new DataTree(data.getF(2, 3)));
        final int state = data.getI(2, 4);
        final List<List> children = data.getF(3);
        final List<Node> decompressedChildren = new ArrayList<>();
        final NodeReference ref = state == 0 ? Ref.getNodeRef(id) : null;
        final Node node;
        PhysicsHandler physicsHandler;
        for (List child : children) {
            final Node kid = uncrush(new DataTree(child), scene);
            decompressedChildren.add(kid);
        }
        if (ref == null) {
            node = new Node();
        } else {
            final Node sample = ref.getSample();
            node = sample.copy();
        }
        node.data = nodeData;
        node.reference = Ref.getNodeRef(id);
        node.scene = scene;
        node.physicsData = physicsData;
        node.x = x;
        node.y = y;
        node.angle = angle;
        for (Node kid : decompressedChildren) {
            node.addChild(kid);
        }
        switch (state) {
            case 1: node.markWorld(); node.setName("world"); break;
            case 2: node.markUniverse(); node.setName("universe"); break;
        }
        physicsHandler = null;
        if (scene != null && scene instanceof DynamicScene) {
            final DynamicScene dynamicScene = (DynamicScene) scene;
            physicsHandler = dynamicScene.getPhysicsHandler();
        }
        if (physicsHandler != null && node.physicsData.getEnablePhysics()) {
            physicsHandler.addPhysicsNode(node);
        }
        return node;
    }

}
