package com.niopullus.NioLib.scene.dynscene;

/**
 * Created by Owen on 3/23/2016.
 */
public interface CollideData {

    double getElasticity();
    double getFriction();
    String getName();
    void victimCollision(Collision collision);

}
