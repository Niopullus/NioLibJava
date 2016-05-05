package com.niopullus.NioLib.scene.dynscene;

import java.io.Serializable;

/**
 * Created by Owen on 3/10/2016.
 */
public class PhysicsData implements Serializable {

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
        return this.doGravity;
    }

    public double getGravityCoefficient() {
        return this.gravityCoefficient;
    }

    public void setXv(double xv) {
        this.xv = xv;
    }

    public void setYv(double yv) {
        this.yv = yv;
    }

    public void setElasticity(double elasticity) {
        this.elasticity = elasticity;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setxStrength(double xStrength) {
        this.xStrength = xStrength;
    }

    public void setyStrength(double yStrength) {
        this.yStrength = yStrength;
    }

    public void setxSpeedLim(double xSpeedLim) {
        this.xSpeedLim = xSpeedLim;
    }

    public void setySpeedLim(double ySpeedLim) {
        this.ySpeedLim = ySpeedLim;
    }

    public void enableGravity() {
        this.doGravity = true;
    }

    public void disableGravity() {
        this.doGravity = false;
    }

    public void setGravityCoefficient(double coefficient) {
        this.gravityCoefficient = coefficient;
    }

    public void accelerateX(double acceleration) {
        if (Math.abs(this.xv + acceleration) <= this.xSpeedLim) {
            this.xv += acceleration;
        } else {
            if (this.xv + acceleration > 0) {
                this.xv = this.xSpeedLim;
            } else {
                this.xv = -this.xSpeedLim;
            }
        }
    }

    public void accelerateY(double acceleration) {
        if (Math.abs(this.yv + acceleration) <= this.ySpeedLim) {
            this.yv += acceleration;
        } else {
            if (this.yv + acceleration > 0) {
                this.yv = this.ySpeedLim;
            } else {
                this.yv = -this.ySpeedLim;
            }
        }
    }

    public void accelerate(double xacc, double yacc) {
        this.accelerateX(xacc);
        this.accelerateY(yacc);
    }

    public void setColEast(HalfCollision halfCollision) {
        this.colEast = halfCollision;
    }

    public void setColWest(HalfCollision halfCollision) {
        this.colWest = halfCollision;
    }

    public void setColNorth(HalfCollision halfCollision) {
        this.colNorth = halfCollision;
    }

    public void setColSouth(HalfCollision halfCollision) {
        this.colSouth = halfCollision;
    }

    public HalfCollision getColEast() {
        return this.colEast;
    }

    public HalfCollision getColWest() {
        return this.colWest;
    }

    public HalfCollision getColNorth() {
        return this.colNorth;
    }

    public HalfCollision getColSouth() {
        return this.colSouth;
    }

    public boolean getCollidableIn() {
        return this.collidablein;
    }

    public void enableCollisionIn() {
        this.collidablein = true;
    }

    public void disableCollisionIn() {
        this.collidablein = false;
    }

    public boolean getCollidableOut() {
        return this.collidableout;
    }

    public void enableCollisionOut() {
        this.collidableout = true;
    }

    public void disableCollisionOut() {
        this.collidableout = false;
    }

}
