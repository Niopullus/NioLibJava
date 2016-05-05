package com.niopullus.NioLib.scene.dynscene;

/**
 * Created by Owen on 3/23/2016.
 */
public class HalfCollision implements Comparable {

    private CollideData collideData;
    private int dist;

    public HalfCollision(int dist, CollideData collideData) {
        this.dist = dist;
        this.collideData = collideData;
    }

    public int getDist() {
        return this.dist;
    }

    public double getFriction() {
        return this.collideData.getFriction();
    }

    public double getElasticity() {
        return this.collideData.getElasticity();
    }

    public int compareTo(Object object) {
        Integer dist1 = this.dist;
        Integer dist2 = ((HalfCollision) (object)).getDist();
        return dist1.compareTo(dist2);
    }

}
