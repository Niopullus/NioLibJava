package com.niopullus.NioLib.scene.dynscene;

import com.niopullus.NioLib.Crushable;
import com.niopullus.NioLib.DataTree;


/**Stores information regarding the physics of a node
 * Created by Owen on 3/10/2016.
 */
public class PhysicsData implements Crushable {

    public static final double DEFAULT_ELASTICITY = 0.0;
    public static final double DEFAULT_FRICTION = 0.2;
    public static final double DEFAULT_MASS = 10;
    public static final double DEFAULT_XSTRENGTH = 10;
    public static final double DEFAULT_YSTRENGTH = 10;
    public static final double DEFAULT_XSPEEDLIMIT = 30;
    public static final double DEFAULT_YSPEEDLIMIT = 30;
    private double xv;
    private double yv;
    private double elasticity;
    private double friction;
    private double mass;
    private double xStrength;
    private double yStrength;
    private double xSpeedLim;
    private double ySpeedLim;
    private double gravityCoefficient;
    private boolean doGravity;
    private HalfCollision colEast;
    private HalfCollision colWest;
    private HalfCollision colNorth;
    private HalfCollision colSouth;
    private boolean collidablein;
    private boolean collidableout;
    private boolean enablePhysics;

    public PhysicsData() {
        this.xv = 0;
        this.yv = 0;
        this.elasticity = DEFAULT_ELASTICITY;
        this.friction = DEFAULT_FRICTION;
        this.mass = DEFAULT_MASS;
        this.xStrength = DEFAULT_XSTRENGTH;
        this.yStrength = DEFAULT_YSTRENGTH;
        this.xSpeedLim = DEFAULT_XSPEEDLIMIT;
        this.ySpeedLim = DEFAULT_YSPEEDLIMIT;
        this.gravityCoefficient = 1.0;
        this.doGravity = true;
        this.collidablein = true;
        this.collidableout = false;
    }

    public double getXv() {
        return xv;
    }

    public double getYv() {
        return yv;
    }

    public double getElasticity() {
        return elasticity;
    }

    public double getFriction() {
        return friction;
    }

    public double getMass() {
        return mass;
    }

    public double getxStrength() {
        return xStrength;
    }

    public double getyStrength() {
        return yStrength;
    }

    public double getxSpeedLim() {
        return xSpeedLim;
    }

    public double getySpeedLim() {
        return ySpeedLim;
    }

    public boolean getDoGravity() {
        return doGravity;
    }

    public double getGravityCoefficient() {
        return gravityCoefficient;
    }

    public HalfCollision getColEast() {
        return colEast;
    }

    public HalfCollision getColWest() {
        return colWest;
    }

    public HalfCollision getColNorth() {
        return colNorth;
    }

    public HalfCollision getColSouth() {
        return colSouth;
    }

    public boolean getCollidableIn() {
        return collidablein;
    }

    public boolean getEnablePhysics() {
        return enablePhysics;
    }

    public void setXv(final double xv) {
        this.xv = xv;
    }

    public void setYv(final double yv) {
        this.yv = yv;
    }

    public void setElasticity(final double elasticity) {
        this.elasticity = elasticity;
    }

    public void setFriction(final double friction) {
        this.friction = friction;
    }

    public void setMass(final double mass) {
        this.mass = mass;
    }

    public void setxStrength(final double xStrength) {
        this.xStrength = xStrength;
    }

    public void setyStrength(final double yStrength) {
        this.yStrength = yStrength;
    }

    public void setxSpeedLim(final double xSpeedLim) {
        this.xSpeedLim = xSpeedLim;
    }

    public void setySpeedLim(final double ySpeedLim) {
        this.ySpeedLim = ySpeedLim;
    }

    public void enableGravity() {
        doGravity = true;
    }

    public void disableGravity() {
        doGravity = false;
    }

    public void setGravityCoefficient(final double coefficient) {
        gravityCoefficient = coefficient;
    }

    public void setColEast(final HalfCollision halfCollision) {
        this.colEast = halfCollision;
    }

    public void setColWest(final HalfCollision halfCollision) {
        this.colWest = halfCollision;
    }

    public void setColNorth(final HalfCollision halfCollision) {
        this.colNorth = halfCollision;
    }

    public void setColSouth(final HalfCollision halfCollision) {
        this.colSouth = halfCollision;
    }

    public void enableCollisionIn() {
        collidablein = true;
    }

    public void disableCollisionIn() {
        collidablein = false;
    }

    public boolean getCollidableOut() {
        return collidableout;
    }

    public void enableCollisionOut() {
        collidableout = true;
    }

    public void disableCollisionOut() {
        collidableout = false;
    }

    public void setEnablePhysics(boolean enablePhysics) {
        this.enablePhysics = enablePhysics;
    }

    public void setDoGravity(final boolean doGravity) {
        this.doGravity = doGravity;
    }

    public void setCollidablein(final boolean collidablein) {
        this.collidablein = collidablein;
    }

    public void setCollidableout(final boolean collidableout) {
        this.collidableout = collidableout;
    }

    public void accelerate(final double xacc, final double yacc) {
        accelerateX(xacc);
        accelerateY(yacc);
    }

    public void accelerateX(final double acceleration) {
        if (Math.abs(xv + acceleration) <= xSpeedLim) {
            xv += acceleration;
        } else {
            if (xv + acceleration > 0) {
                xv = xSpeedLim;
            } else {
                xv = -xSpeedLim;
            }
        }
    }

    public void accelerateY(final double acceleration) {
        if (Math.abs(yv + acceleration) <= ySpeedLim) {
            yv += acceleration;
        } else {
            if (yv + acceleration > 0) {
                yv = ySpeedLim;
            } else {
                yv = -ySpeedLim;
            }
        }
    }

    public PhysicsData copy() {
        final PhysicsData result = new PhysicsData();
        result.xv = xv;
        result.yv = yv;
        result.elasticity = elasticity;
        result.friction = friction;
        result.mass = mass;
        result.xStrength = xStrength;
        result.yStrength = yStrength;
        result.xSpeedLim = xSpeedLim;
        result.ySpeedLim = ySpeedLim;
        result.gravityCoefficient = gravityCoefficient;
        result.doGravity = doGravity;
        result.colEast = colEast;
        result.colWest = colWest;
        result.colNorth = colNorth;
        result.colSouth = colSouth;
        result.collidablein = collidablein;
        result.collidableout = collidableout;
        result.enablePhysics = enablePhysics;
        return result;
    }

    /**Crush Diagram:
     * root {
     *      b - enablephysics
     *      d - x velocity
     *      d - y velocity
     *      d - elasticity
     *      d - friction
     *      d - mass
     *      d - xStrength
     *      d - yStrength
     *      d - xSpeedLimit
     *      d - ySpeedLimit
     *      d - gravityCoefficient
     *      b - doGravity
     *      b - collidablein
     *      b - collidableout
     * }
     * @see Crushable
     */

    public DataTree crush() {
        final DataTree result = new DataTree();
        result.addData(enablePhysics);
        result.addData(xv);
        result.addData(yv);
        result.addData(elasticity);
        result.addData(friction);
        result.addData(mass);
        result.addData(xStrength);
        result.addData(yStrength);
        result.addData(xSpeedLim);
        result.addData(ySpeedLim);
        result.addData(gravityCoefficient);
        result.addData(doGravity);
        result.addData(collidablein);
        result.addData(collidableout);
        return result;
    }

    public static PhysicsData uncrush(final DataTree data) {
        final PhysicsData result = new PhysicsData();
        result.setEnablePhysics(data.getB(0));
        result.setXv(data.getD(1));
        result.setYv(data.getD(2));
        result.setElasticity(data.getD(3));
        result.setFriction(data.getD(4));
        result.setMass(data.getD(5));
        result.setxStrength(data.getD(6));
        result.setyStrength(data.getD(7));
        result.setxSpeedLim(data.getD(8));
        result.setySpeedLim(data.getD(9));
        result.setGravityCoefficient(data.getD(10));
        result.setDoGravity(data.getB(11));
        result.setCollidablein(data.getB(12));
        result.setCollidableout(data.getB(13));
        return result;
    }


}
