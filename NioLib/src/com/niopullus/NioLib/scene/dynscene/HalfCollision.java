package com.niopullus.NioLib.scene.dynscene;

/**
 * Created by Owen on 3/23/2016.
 */
public class HalfCollision implements Comparable<HalfCollision> {

    private CollideData collideData;
    private int dist;

    public HalfCollision(final int dist, final CollideData collideData) {
        this.dist = dist;
        this.collideData = collideData;
    }

    public int getDist() {
        return dist;
    }

    public double getFriction() {
        return collideData.getFriction();
    }

    public double getElasticity() {
        return collideData.getElasticity();
    }

    public int compareTo(final HalfCollision halfCollision) {
        final Integer dist1 = dist;
        final Integer dist2 = halfCollision.getDist();
        return dist1.compareTo(dist2);
    }

}
