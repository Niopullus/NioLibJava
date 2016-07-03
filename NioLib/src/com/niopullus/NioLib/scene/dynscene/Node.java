package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.*;
import com.niopullus.NioLib.draw.Parcel;
import com.niopullus.NioLib.scene.NodeHandler;
import com.niopullus.NioLib.scene.dynscene.reference.NodeReference;
import com.niopullus.NioLib.scene.dynscene.reference.Ref;
import com.niopullus.NioLib.utilities.Utilities;
import com.niopullus.NioLib.draw.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**Polymorphic sprite designed for a Dynamic Scene
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

    public Node() {
        this("unnamedNode");
    }

    public Node(final String name) {
        this(name, 0, 0);
    }

    public Node(final String name, final int width, final int height) {
        this.children = new ArrayList<>();
        this.id = new UUID(name);
        this.physicsData = new PhysicsData();
        this.width = width;
        this.height = height;
        this.partRangeX = null;
        this.partRangeY = null;
        this.cx = 0;
        this.cy = 0;
        this.cwidth = 0;
        this.cheight = 0;
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
        if (isWorld) {
            return 0;
        } else {
            return parent.getWY() + y;
        }
    }

    public void setCX(final int cx) {
        this.cx = cx;
    }

    public void setCY(final int cy) {
        this.cy = cy;
    }

    public void setCWidth(final int cwidth) {
        this.cwidth = cwidth;
    }

    public void setCHeight(final int cheight) {
        this.cheight = cheight;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setZ(final int z) {
        this.z = z;
    }

    public void setXv(final double xv) {
        this.physicsData.setXv(xv);
    }

    public void setYv(final double yv) {
        this.physicsData.setYv(yv);
    }

    public void setScene(final NodeHandler scene) {
        this.scene = scene;
    }

    public void setGravityCoefficient(final double coefficient) {
        this.physicsData.setGravityCoefficient(coefficient);
    }

    public void setElasticity(final double elasticity) {
        this.physicsData.setElasticity(elasticity);
    }

    public void setFriction(final double friction) {
        this.physicsData.setFriction(friction);
    }

    public void setMass(final double mass) {
        this.physicsData.setMass(mass);
    }

    public void setxStrength(final double xStrength) {
        this.physicsData.setxStrength(xStrength);
    }

    public void setyStrength(final double yStrength) {
        this.physicsData.setyStrength(yStrength);
    }

    public void setxSpeedLim(final double xSpeedLim) {
        this.physicsData.setxSpeedLim(xSpeedLim);
    }

    public void setySpeedLim(final double ySpeedLim) {
        this.physicsData.setySpeedLim(ySpeedLim);
    }

    public void disableGravity() {
        physicsData.disableGravity();
    }

    public void enableGravity() {
        physicsData.enableGravity();
    }

    public void setColEast(final HalfCollision halfCollision) {
        this.physicsData.setColEast(halfCollision);
    }

    public void setColWest(final HalfCollision halfCollision) {
        this.physicsData.setColWest(halfCollision);
    }

    public void setColNorth(final HalfCollision halfCollision) {
        this.physicsData.setColNorth(halfCollision);
    }

    public void setColSouth(final HalfCollision halfCollision) {
        this.physicsData.setColSouth(halfCollision);
    }

    public void setPartRangeX(final int[] rangeX) {
        this.partRangeX = rangeX;
    }

    public void setPartRangeY(final int[] rangeY) {
        this.partRangeY = rangeY;
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

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void csetXScale(final double xScale) {
        this.xScale = xScale;
    }

    public void csetYScale(final double yScale) {
        this.yScale = yScale;
    }

    public void osetWidth(final int width) {
        this.oWidth = width;
    }

    public void osetHeight(final int height) {
        this.oHeight = height;
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

    public void setRotatation(final double angle) {
        this.angle = angle;
    }

    public void setReference(final NodeReference reference) {
        this.reference = reference;
    }

    public void setName(final String name) {
        id = new UUID(name);
    }

    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public void setXScale(final double xScale) {
        this.xScale = xScale;
        this.width = (int) (this.oWidth * xScale);
    }

    public void setYScale(final double yScale) {
        this.yScale = yScale;
        this.height = (int) (this.oHeight * yScale);
    }

    public void setPosition(final int x, final int y) {
        this.setX(x);
        this.setY(y);
    }

    public void update() {
        for (Node node : children) {
            node.update();
        }
        translate();
    }

    private void translate() {
        if (movePos != null) {
            final double angle = Utilities.invtrig(movePos.getY() - y, movePos.getX() - x);
            final int newX = (int) (x + moveSpeed * Math.cos(angle));
            final int newY = (int) (y + moveSpeed * Math.sin(angle));
            if (newX < x && newX < this.movePos.getX() || newX > x && newX > movePos.getX()) {
                x = (int) movePos.getX();
            } else {
                x = newX;
            }
            if (newY < y && newY < movePos.getY() || newY > y && newY > movePos.getY()) {
                y = (int) movePos.getY();
            } else {
                y = newY;
            }
        }
        if (getPosition().equals(movePos)) {
            movePos = null;
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
    }

    public void removeChild(final Node node) {
        final int index = Collections.binarySearch(children, node);
        if (index != -1) {
            children.remove(index);
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

    public void parcelDraw(final Canvas canvas) {
        for (Node node : children) {
            canvas.o.parcel(node, node.getX(), node.getY(), node.getZ(), node.getAngle());
        }
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
            final List<Node> newChildren = new ArrayList();
            for (Node child : children) {
                newChildren.add(child.copy());
            }
            node.id = new UUID(id.getName());
            node.children = newChildren;
            node.physicsData = physicsData.copy();
            node.data = data.copy();
            return node;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * root {
     *     i - id
     *     f - nodeData
     *     f - details {
     *         i - x
     *         i - y
     *         f - physicsData
     *         i - nodeState
     *     }
     *     f - children
     * }
     */

    public DataTree crush() {
        final DataTree result = new DataTree();
        result.addData(reference.getId());
        result.addData(data);
        result.addFolder();
        result.addData(x, 2);
        result.addData(y, 2);
        result.addData(physicsData, 2);
        result.addData(isUniverse ? 2: isWorld ? 1: 0, 2);
        result.addData(children);
        return result;
    }

    public static Node uncrush(final DataTree data, final NodeHandler scene) {
        final int id = data.getI(0);
        final DataTree nodeData = new DataTree(data.getF(1));
        final int x = data.getI(2, 0);
        final int y = data.getI(2, 1);
        final PhysicsData physicsData = PhysicsData.uncrush(new DataTree(data.getF(2, 2)));
        final int state = data.getI(2, 3);
        final List<List> children = data.getF(3);
        final List<Node> decompressedChildren = new ArrayList<>();
        final NodeReference ref = Ref.getNodeRef(id);
        final Node node;
        PhysicsHandler physicsHandler;
        for (List child : children) {
            final Node kid = Node.uncrush(new DataTree(child), scene);
            decompressedChildren.add(kid);
        }
        if (ref == null) {
            node = new Node();
        } else {
            final Node sample = ref.getSample();
            node = sample.copy();
            node.setXScale(ref.getDefaultXScale());
            node.setYScale(ref.getDefaultYScale());
        }
        node.data = nodeData;
        node.reference = Ref.getNodeRef(id);
        node.scene = scene;
        node.physicsData = physicsData;
        node.x = x;
        node.y = y;
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
